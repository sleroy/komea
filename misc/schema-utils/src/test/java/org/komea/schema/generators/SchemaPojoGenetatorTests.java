
package org.komea.schema.generators;


import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Test;
import org.komea.core.schema.IEntityType;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.IKomeaSchemaFactory;
import org.komea.core.schema.Primitive;
import org.komea.core.schema.ReferenceArity;
import org.komea.core.schema.ReferenceKind;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.ecore.Ecore2KomeaSchemaBuilder;

public class SchemaPojoGenetatorTests
{
    
    @Test
    public void generateTest() throws IOException {
    
        IKomeaSchemaFactory factory = new KomeaSchemaFactory();
        IKomeaSchema schema = factory.newSchema("schema");
        
        IEntityType person = factory.newEntity("Person");
        person.addProperty(factory.newReference("name", Primitive.STRING));
        person.addProperty(factory.newReference("values", Primitive.INTEGER).setArity(ReferenceArity.MANY));
        person.addProperty(factory.newReference("children", person).setArity(ReferenceArity.MANY).setKind(ReferenceKind.AGGREGATION));
        schema.addType(person);
        
        File output = new File("build/gen");
        
        KomeaSchemaPojoGenerator generator = new KomeaSchemaPojoGenerator(output, "test");
        generator.generate(schema);
        Assert.assertTrue(FileUtils.getFile(output, "test", "Person.java").exists());
    }
    
    @Test
    public void generateProcessSchemaTest() throws IOException {
    
        URI uri = URI.createFileURI("src/test/resources/process.ecore");
        EPackage ecore = Ecore2KomeaSchemaBuilder.load(uri);
        Ecore2KomeaSchemaBuilder builder = new Ecore2KomeaSchemaBuilder(new KomeaSchemaFactory());
        String opackage = "process";
        IKomeaSchema schema = builder.buildFrom(opackage, ecore);
        
        File output = new File("build/gen");
        KomeaSchemaPojoGenerator generator = new KomeaSchemaPojoGenerator(output, opackage);
        generator.generate(schema);
        Assert.assertTrue(FileUtils.getFile(output, opackage, "Member.java").exists());
    }
    
}
