
package org.komea.core.schema.impl;


import static org.komea.core.schema.impl.SchemaNamingConvention.ARITY;
import static org.komea.core.schema.impl.SchemaNamingConvention.MANDATORY;
import static org.komea.core.schema.impl.SchemaNamingConvention.ONE;
import static org.komea.core.schema.impl.SchemaNamingConvention.TRUE;
import static org.komea.core.schema.impl.SchemaNamingConvention.TYPE;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.komea.events.exceptions.KomeaRuntimeException;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IReference;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;
import org.komea.core.schema.impl.SchemaNamingConvention.EdgeDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.orientechnologies.orient.core.index.OIndex;
import com.orientechnologies.orient.core.index.OIndexUnique;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OProperty;
import com.orientechnologies.orient.core.metadata.schema.OSchema;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientVertexType;

public class OrientSchemaLoader
{
    
    private final static Logger       LOGGER  = LoggerFactory.getLogger(OrientSchemaLoader.class);
    
    private final IKomeaSchemaFactory factory = new KomeaSchemaFactory();
    
    public OrientSchemaLoader() {
    
    }
    public IKomeaSchema load(final String name, final OrientGraph database) {
    
        IKomeaSchema schema = this.factory.newSchema(name);
        SchemaMapping mapping = new SchemaMapping(database);
        OSchema oschema = database.getRawGraph().getMetadata().getSchema();
        for (OClass oclass : oschema.getClasses()) {
            if (isEntityType(oclass)) {
                IEntityType etype = mapping.getOrCreateEntityType(oclass.getName());
                schema.addType(etype);
            }
        }
        return schema;
    }
    
    private boolean isEntityType(final OClass etype) {
    
        return !etype.getName().equals("V") && etype.isSubClassOf("V");
    }
    
    private static class SchemaInferenceException extends KomeaRuntimeException
    {
        
        public SchemaInferenceException(final String message, final Throwable cause) {
        
            super(message, cause);
            
        }
        
        public SchemaInferenceException(final String message) {
        
            super(message);
            
        }
        
        public SchemaInferenceException(final Throwable t) {
        
            super(t);
            
        }
        
    }
    
    private static class SchemaMapping
    {
        
        private static final String            OUT_EDGE = "out_";
        private static final String            IN_EDGE  = "in_";
        private final IKomeaSchemaFactory      factory  = new KomeaSchemaFactory();
        private final OrientGraph              graph;
        private final Map<String, IEntityType> typesMap = Maps.newHashMap();
        
        public SchemaMapping(final OrientGraph graph) {
        
            super();
            this.graph = graph;
        }
        
        public IEntityType getOrCreateEntityType(final String name) {
        
            IEntityType etype = this.typesMap.get(name);
            if (etype == null) {
                etype = createEntityType(name);
            }
            return etype;
        }
        
        private IEntityType createEntityType(final String name) {
        
            OrientVertexType oclass = this.graph.getVertexType(name);
            if (oclass == null) {
                throw new SchemaInferenceException("Unable to find the vertex type for " + name);
            }
            IEntityType etype = this.factory.newEntity(name);
            this.typesMap.put(name, etype);
            Collection<OProperty> properties = oclass.declaredProperties();
            for (OProperty oProperty : properties) {
                if (isAttribute(oProperty)) {
                    etype.addProperty(createAttribute(oProperty));
                } else {
                    IReference ref = createReference(oProperty);
                    if (ref != null) {
                        etype.addProperty(ref);
                    }
                }
            }
            OrientVertexType superClass = oclass.getSuperClass();
            if (superClass != null && !superClass.getName().equals("V")) {
                etype.setSuperType(getOrCreateEntityType(superClass.getName()));
            }
            return etype;
        }
        private boolean isAttribute(final OProperty oProperty) {
        
            String name = oProperty.getName();
            return !name.startsWith(IN_EDGE) && !name.startsWith(OUT_EDGE);
        }
        
        private IReference createAttribute(final OProperty oProperty) {
        
            OType otype = oProperty.getType();
            OType otypeLinked = oProperty.getLinkedType();
            
            Primitive ptype;
            if (otype.isMultiValue()) {
                ptype = Primitive.valueOf(otypeLinked.name());
            } else {
                ptype = Primitive.valueOf(otype.name());
            }
            IReference reference = this.factory.newReference(oProperty.getName(), ptype).setKind(ReferenceKind.CONTAINMENT);
            if (otype.isMultiValue()) {
                reference.setArity(ReferenceArity.MANY);
            }
            if (isUnique(oProperty)) {
                reference.enableUnique();
            } else if (isIndexed(oProperty)) {
                reference.enableIndexation();
            }
            if (isMandatory(oProperty)) {
                reference.enableMandatory();
            }
            return reference;
        }
        
        /**
         * Try to extract a Komea reference from a {@link OProperty}.
         * 
         * @param oProperty
         * @return null if no Komea reference can be extracted from the property
         */
        private IReference createReference(final OProperty oProperty) {
        
            String name = oProperty.getName();
            try {
                EdgeDefinition definition = SchemaNamingConvention.extractEdgeDefinition(name);
                
                String pname = definition.getName();
                String etype = SchemaNamingConvention.formatEdgeType(definition);
                
                OrientEdgeType oetype = this.graph.getEdgeType(etype);
                String opposite = oetype.getCustom(TYPE);
                Validate.isTrue(opposite != null, "Type has'nt been set on property " + oProperty.getName());
                IEntityType oppositeType = getOrCreateEntityType(opposite);
                
                ReferenceArity arity = extractArity(oetype);
                ReferenceKind kind = extractKind(oetype);
                IReference reference = this.factory.newReference(pname, oppositeType).setArity(arity).setKind(kind);
                
                if (isMandatory(oetype)) {
                    reference.enableMandatory();
                }
                
                return reference;
                
            } catch (NamingConventionException e) {
                LOGGER.warn("Unable to build a Komea schema reference from property " + name
                        + ": it does'nt respect naming convention (out_[entity]_[name]).");
                return null;
            }
            
        }
        private boolean isMandatory(final OrientEdgeType oetype) {
        
            String custom = oetype.getCustom(MANDATORY);
            return TRUE.equals(custom);
        }
        
        private boolean isMandatory(final OProperty oetype) {
        
            String custom = oetype.getCustom(MANDATORY);
            return TRUE.equals(custom);
        }
        
        private boolean isUnique(final OProperty oetype) {
        
            Collection<OIndex<?>> indexes = oetype.getAllIndexes();
            if(indexes.isEmpty()){
                return false;
            }
            return hasUnique(indexes);
        }
        
        private boolean hasUnique( final Collection<OIndex<?>>  indexes){
            for (OIndex<?> oIndex : indexes) {
                String type = oIndex.getType();
                if("UNIQUE".equals(type)){
                    return true;
                }
            }
            return false;
        }
        
        private boolean isIndexed(final OProperty oetype) {
        
            Collection<OIndex<?>> indexes = oetype.getAllIndexes();
            return !indexes.isEmpty();
        }
        
        private ReferenceKind extractKind(final OrientEdgeType oetype) {
        
            return ReferenceKind.valueOf(oetype.getSuperClass().getName());
        }
        
        private ReferenceArity extractArity(final OrientEdgeType oetype) {
        
            String arity = oetype.getCustom(ARITY);
            
            if (ONE.equals(arity)) {
                return ReferenceArity.ONE;
            } else {
                return ReferenceArity.MANY;
            }
        }
        
    }
}
