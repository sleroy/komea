
package org.komea.ecore;


import java.io.IOException;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;

/**
 * A Komea schema builder from an ecore metamodel.
 * 
 * @author afloch
 */
public class Ecore2KomeaSchemaBuilder
{
    
    /**
     * Load an ecore file.
     * 
     * @param uri
     * @return
     * @throws IOException
     */
    public static EPackage load(final URI uri) throws IOException {
    
        ResourceSet rs = new ResourceSetImpl();
        Map<String, Object> options = rs.getResourceFactoryRegistry().getExtensionToFactoryMap();
        options.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
        
        Resource resource = rs.getResource(uri, true);
        EList<EObject> contents = resource.getContents();
        
        return (EPackage) contents.get(0);
    }
    
    private final IKomeaSchemaFactory factory;
    
    public Ecore2KomeaSchemaBuilder(final IKomeaSchemaFactory factory) {
    
        super();
        this.factory = factory;
    }
    
    /**
     * Build a schema from an ecore metamodel package.
     * 
     * @param name
     * @param epackage
     * @return
     */
    public IKomeaSchema buildFrom(final String name, final EPackage epackage) {
    
        IKomeaSchema schema = this.factory.newSchema(name);
        TypeMapper mapper = new TypeMapper(this.factory);
        fillSchema(schema, epackage, mapper);
        return schema;
    }
    private void fillSchema(final IKomeaSchema schema, final EPackage epackage, final TypeMapper mapper) {
    
        TypeBuilder builder = new TypeBuilder(mapper);
        for (EClassifier eClassifier : epackage.getEClassifiers()) {
            IEntityType type = builder.doSwitch(eClassifier);
            if (type != null) {
                schema.addType(type);
            }
        }
        for (EPackage subPackage : epackage.getESubpackages()) {
            fillSchema(schema, subPackage, mapper);
        }
    }
}
