
package org.komea.core.schema.impl;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.komea.core.exceptions.KomeaRuntimeException;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IReference;
import org.komea.core.schema.IType;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class SchemaWeaver
{
    
    private final IKomeaSchemaFactory factory;
    
    public SchemaWeaver(final IKomeaSchemaFactory factory) {
    
        super();
        this.factory = factory;
    }
    
    public SchemaWeaver() {
    
        this(new KomeaSchemaFactory());
    }
    
    /**
     * Weave all input schemas into a single schema.
     * 
     * @param schemas
     * @return
     */
    public IKomeaSchema weave(final String name, final Iterable<IKomeaSchema> schemas) throws SchemaWeavingException {
    
        IKomeaSchema schema = this.factory.newSchema(name);
        
        Map<String, TypePointcut> pointcutsMap = Maps.newHashMap();
        
        // first create all weaved types from input schema
        for (IKomeaSchema toWeave : schemas) {
            for (IEntityType type : toWeave.getTypes()) {
                String typeName = type.getName();
                TypePointcut weavedType = pointcutsMap.get(typeName);
                if (weavedType == null) {
                    weavedType = new TypePointcut(typeName, this.factory);
                    pointcutsMap.put(typeName, weavedType);
                }
                weavedType.addType(type);
            }
        }
        
        Collection<TypePointcut> pointcuts = pointcutsMap.values();
        for (TypePointcut pointcut : pointcuts) {
            schema.addType(pointcut.weave(pointcutsMap));
        }
        return schema;
    }
    
    private static class TypePointcut
    {
        
        private final String              name;
        private final List<IEntityType>   types;
        private final IEntityType         weaved;
        private final IKomeaSchemaFactory factory;
        
        public TypePointcut(final String name, final IKomeaSchemaFactory factory) {
        
            super();
            this.name = name;
            this.types = Lists.newArrayList();
            this.factory = factory;
            this.weaved = this.factory.newEntity(this.name);
        }
        
        public void addType(final IEntityType type) {
        
            this.types.add(type);
        }
        
        public IEntityType weave(final Map<String, TypePointcut> map) {
        
            manageInheritancy(map);
            manageReferences(map);
            return this.weaved;
        }
        
        private void manageInheritancy(final Map<String, TypePointcut> map) {
        
            Set<TypePointcut> superTypes = Sets.newHashSet();
            for (IEntityType type : this.types) {
                IEntityType superType = type.getSuperType();
                if (superType != null) {
                    superTypes.add(map.get(superType.getName()));
                }
            }
            
            if (superTypes.size() > 0) {
                if (superTypes.size() > 1) {
                    throw new SchemaWeavingException("More than one super type for " + this.name + ": " + superTypes);
                }
                TypePointcut superTypePointcut = superTypes.iterator().next();
                this.weaved.setSuperType(superTypePointcut.getWeaved());
            }
        }
        
        private void manageReferences(final Map<String, TypePointcut> typesMap) {
        
            Map<String, ReferencePointcut> pointcuts = findAllReferencesPointcuts();
            
            Collection<ReferencePointcut> values = pointcuts.values();
            for (ReferencePointcut pointcut : values) {
                this.weaved.addProperty(pointcut.weave(typesMap));
            }
        }
        
        private Map<String, ReferencePointcut> findAllReferencesPointcuts() {
        
            Map<String, ReferencePointcut> pointcuts = Maps.newHashMap();
            
            for (IEntityType type : this.types) {
                for (IReference property : type.getProperties()) {
                    String pname = property.getName();
                    ReferencePointcut pointcut = pointcuts.get(pname);
                    if (pointcut == null) {
                        pointcut = new ReferencePointcut(pname, this.factory);
                        pointcuts.put(pname, pointcut);
                    }
                    pointcut.addReference(property);
                }
            }
            return pointcuts;
        }
        
        public IEntityType getWeaved() {
        
            return this.weaved;
        }
        
        @Override
        public int hashCode() {
        
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
            return result;
        }
        
        @Override
        public boolean equals(final Object obj) {
        
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            TypePointcut other = (TypePointcut) obj;
            if (this.name == null) {
                if (other.name != null) {
                    return false;
                }
            } else if (!this.name.equals(other.name)) {
                return false;
            }
            return true;
        }
        
        @Override
        public String toString() {
        
            return this.name;
        }
    }
    
    private static class ReferencePointcut
    {
        
        private final String           name;
        private final List<IReference> references;
        private final IKomeaSchemaFactory    factory;
        
        public ReferencePointcut(final String name, final IKomeaSchemaFactory factory) {
        
            super();
            this.name = name;
            this.references = Lists.newArrayList();
            this.factory = factory;
            
        }
        
        public void addReference(final IReference reference) {
        
            this.references.add(reference);
        }
        
        public IReference weave(final Map<String, TypePointcut> typesMap) {
        
            IType type = findType(typesMap);
            IReference ref = this.factory.newReference(this.name, type);
            ref.setKind(findKind());
            ref.setArity(findArity());
            for(IReference input: this.references){
                if(input.isIndexed()){
                    ref.enableIndexation();
                }
                if(input.isUnique()){
                    ref.enableUnique();
                }
                if(input.isMandatory()){
                    ref.enableMandatory();
                }
            }
            return ref;
        }
        
        private ReferenceArity findArity() {
            Set<ReferenceArity> arities = Sets.newHashSet();
            for(IReference ref : this.references){
                arities.add(ref.getArity());
            }
            if(arities.size()>1){
                throw new SchemaWeavingException("More than one arity for reference " + this.name + ": " + arities);
            }
            return arities.iterator().next();
        }

        private ReferenceKind findKind() {
            Set<ReferenceKind> kinds = Sets.newHashSet();
            for(IReference ref : this.references){
                kinds.add(ref.getKind());
            }
            if(kinds.size()>1){
                throw new SchemaWeavingException("More than one reference kind for " + this.name + ": " + kinds);
            }
            return kinds.iterator().next();
        }

        private IType findType(final Map<String, TypePointcut> typesMap) {
        
            Set<String> types = Sets.newHashSet();
            for (IReference ref : this.references) {
                types.add(ref.getType().getName());
            }
            if (types.size() > 1) {
                throw new SchemaWeavingException("More than one type for reference " + this.name + ": " + types);
            }
            
            IReference firstRef = this.references.get(0);
            if(firstRef.getType().isPrimitive()){
                return firstRef.getType();
            }else{
                return typesMap.get(firstRef.getType().getName()).getWeaved();
            }
         
        }
        
        @Override
        public int hashCode() {
        
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
            return result;
        }
        
        @Override
        public boolean equals(final Object obj) {
        
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ReferencePointcut other = (ReferencePointcut) obj;
            if (this.name == null) {
                if (other.name != null) {
                    return false;
                }
            } else if (!this.name.equals(other.name)) {
                return false;
            }
            return true;
        }
        
    }
    
    private static class SchemaWeavingException extends KomeaRuntimeException
    {
        
        public SchemaWeavingException(final String message) {
        
            super(message);
            
        }
        
    }
}
