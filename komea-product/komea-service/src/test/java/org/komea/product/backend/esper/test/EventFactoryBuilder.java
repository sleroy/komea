
package org.komea.product.backend.esper.test;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;



/**
 */
public class EventFactoryBuilder
{
    
    
    private final List<EventMethodGenerator> builders = new ArrayList<EventMethodGenerator>();
    
    
    private String                           className;
    
    
    private File                             sourceDirectory;
    
    
    private String                           packageName;
    
    
    
    public EventFactoryBuilder() {
    
    
        super();
        
    }
    
    
    /**
     * Constructor for EventFactoryBuilder.
     * @param _className String
     * @param _packageName String
     * @param _sourceDirectory File
     */
    public EventFactoryBuilder(
            final String _className,
            final String _packageName,
            final File _sourceDirectory) {
    
    
        super();
        className = _className;
        packageName = _packageName;
        sourceDirectory = _sourceDirectory;
        
        
    }
    
    
    /**
     * Generation of the event class.
     * 
    
     * @throws IOException */
    public void generate() throws IOException {
    
    
        FileWriter fileOutputStream = null;
        try {
            final String sourceFolder =
                    sourceDirectory + File.separator + packageName.replace('.', File.separatorChar);
            final File file = new File(sourceFolder);
            file.mkdirs();
            System.out.println("Generation : " + file);
            fileOutputStream = new FileWriter(new File(file, className + ".java"));
            fileOutputStream.write("package org.komea.event.factory;\n");
            fileOutputStream.write("import org.komea.product.database.dto.EventSimpleDto;\n");
            fileOutputStream.write("public class " + className + " {\n");
            for (final EventMethodGenerator builder : builders) {
                fileOutputStream.write(builder.build());
                fileOutputStream.write("\n");
            }
            fileOutputStream.write("\n}\n");
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
        }
    }
    
    
    /**
     * Method register.
     * @param _builder EventMethodGenerator
     */
    public void register(final EventMethodGenerator _builder) {
    
    
        builders.add(_builder);
        
    }
    
}
