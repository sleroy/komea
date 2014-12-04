
package org.komea.core.schema;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.core.schema.impl.SchemaWeaver;
import org.komea.core.schema.utils.YUMLExport;

import com.google.common.collect.Lists;

public class SchemaWeaverTests
{
    
    private final IKomeaSchemaFactory factory = new KomeaSchemaFactory();
    
    @Test
    public void weaverTest() throws FileNotFoundException, IOException {
    
        IKomeaSchema schema1 = buildSchema1();
        IKomeaSchema schema2 = buildSchema2();
        
        SchemaWeaver weaver = new SchemaWeaver();
        IKomeaSchema weaved = weaver.weave("weaved", Lists.newArrayList(schema1,schema2));
        YUMLExport.exportToHtml(new FileOutputStream("weaved.html"), weaved);
    }
    
    private IKomeaSchema buildSchema2() {
    
        IKomeaSchema schema = this.factory.newSchema("S2");
        
        IEntityType person = this.factory.newEntity("Person");
        person.addProperty(this.factory.newAttribute("name", Primitive.STRING));
        person.addProperty(this.factory.newAttribute("email", Primitive.STRING));
        
        schema.addType(person);
        return schema;
    }
    
    private IKomeaSchema buildSchema1() {
    
        IKomeaSchema schema = this.factory.newSchema("S1");
        
        IEntityType person = this.factory.newEntity("Person");
        person.addProperty(this.factory.newAttribute("name", Primitive.STRING).enableIndexation());
        
        IEntityType developer = this.factory.newEntity("Developper");
        developer.setSuperType(person);
        
        schema.addType(person);
        schema.addType(developer);
        return schema;
    }
}
