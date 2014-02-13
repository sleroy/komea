
package org.komea.product.backend.esper.test.factories;



import java.io.File;
import java.io.IOException;

import org.komea.product.backend.esper.test.DSLEventBuilder;
import org.komea.product.backend.esper.test.EventFactoryBuilder;



public class ScmEventFactoryBuilder
{
    
    
    public static void main(final String[] args) throws IOException {
    
    
        final EventFactoryBuilder eventFactoryBuilder =
                new EventFactoryBuilder("ScmEventFactory", "org.komea.event.factory", new File(
                        "src/test/java"));
        eventFactoryBuilder.register(DSLEventBuilder.newEvent("sendCommit", "scm", "scm_commit")
                .linkToProject().withParameter("author", String.class)
                .withParameter("num_lines", Integer.class).withParameter("author", String.class)
                .withParameter("branch", String.class).withParameter("num_files", String.class)
                .withParameter("type", String.class).withParameter("test_files", String.class)
                .withParameter("code_files", String.class)
                .withParameter("message_length", String.class)
                .withParameter("misc_files", String.class)
                .predefinedMessage("New commit for the projet $project.").build());
        eventFactoryBuilder.generate();
    }
    
    
    public ScmEventFactoryBuilder() {
    
    
        super();
        
    }
}
