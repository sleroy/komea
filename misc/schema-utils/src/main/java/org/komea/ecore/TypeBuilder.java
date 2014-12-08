package org.komea.ecore;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreSwitch;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IReference;

class TypeBuilder extends EcoreSwitch<IEntityType>
{
    
    private final TypeMapper typesMap;
    
    public TypeBuilder(final TypeMapper mapper) {
    
        this.typesMap = mapper;
        
    }
    
    @Override
    public IEntityType caseEClass(final EClass object) {
    
        IEntityType type = this.typesMap.getOrCreate(object.getName());
        EList<EClass> superTypes = object.getESuperTypes();
        if (superTypes.size() > 1) {
            throw new Ecore2KomeaException("Unable to build an entity type for " + object.getName()
                    + " since it inherits from more than one EClassifier : " + object.getESuperTypes());
        } else {
            if (superTypes.size() == 1) {
                type.setSuperType(this.typesMap.getOrCreate(superTypes.get(0).getName()));
            }
        }
        ReferenceBuilder refBuilder = new ReferenceBuilder(this.typesMap);
        for (EStructuralFeature feature : object.getEStructuralFeatures()) {
            IReference reference = refBuilder.doSwitch(feature);
            if (reference != null) {
                type.addProperty(reference);
            }
        }
        return type;
    }
}