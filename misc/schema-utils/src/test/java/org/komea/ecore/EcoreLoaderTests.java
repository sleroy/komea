
package org.komea.ecore;


import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.junit.Assert;
import org.junit.Test;
import org.komea.core.schema.IKomeaSchema;
import org.komea.core.schema.impl.KomeaSchemaFactory;
import org.komea.ecore.Ecore2KomeaSchemaBuilder;

public class EcoreLoaderTests
{
    
    @Test
    public void loadTest() throws IOException {
    
        URI uri = URI.createFileURI("src/test/resources/process.ecore");
        EPackage ecore = Ecore2KomeaSchemaBuilder.load(uri);
        Ecore2KomeaSchemaBuilder builder  = new Ecore2KomeaSchemaBuilder(new KomeaSchemaFactory());
        IKomeaSchema schema = builder.buildFrom("test", ecore);
        Assert.assertNotNull(schema.findType("Member"));
    }
    
}
