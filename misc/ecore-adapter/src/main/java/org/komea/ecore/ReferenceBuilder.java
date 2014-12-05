package org.komea.ecore;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreSwitch;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.IReference;
import org.komea.core.schema.IType;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;

class ReferenceBuilder extends EcoreSwitch<IReference>
{
    
    private final IKomeaSchemaFactory factory;
    private final TypeMapper          typesMap;

    
    public ReferenceBuilder(final TypeMapper typesMap) {
    
        super();
        this.factory = typesMap.getFactory();
        this.typesMap = typesMap;
    }
    
    @Override
    public IReference caseEAttribute(final EAttribute object) {
    
        IReference reference = this.factory.newReference(object.getName(), findPrimitiveType(object.getEType())).setKind(
                ReferenceKind.CONTAINMENT);
        if (object.isMany()) {
            reference.setArity(ReferenceArity.MANY);
        }
        if (object.isRequired()) {
            reference.enableMandatory();
        }
        
        return reference;
    }
    
    @Override
    public IReference caseEReference(final EReference object) {
    
        EReference opposite = object.getEOpposite();
        if (opposite == null || !opposite.isContainment()) {
            IReference reference = this.factory.newReference(object.getName(), findReferenceType(object.getEType()));
            if (object.isContainment()) {
                reference.setKind(ReferenceKind.CONTAINMENT);
            } else {
                reference.setKind(ReferenceKind.REFERENCE);
            }
            if (object.isMany()) {
                reference.setArity(ReferenceArity.MANY);
            }
            if (object.isRequired()) {
                reference.enableMandatory();
            }
            return reference;
        } else {
            return super.caseEReference(object);
        }
        
    }
    
    private IType findPrimitiveType(final EClassifier eClassifier) {
    
        String name = eClassifier.getName();
        String tname = name.substring(1);
        
        return Primitive.valueOf(tname.toUpperCase());
    }
    private IType findReferenceType(final EClassifier eClassifier) {
    
        return this.typesMap.getOrCreate(eClassifier.getName());
    }
    
}