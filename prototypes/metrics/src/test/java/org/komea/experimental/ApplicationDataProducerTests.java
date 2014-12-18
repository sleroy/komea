
package org.komea.experimental;


import java.io.File;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.komea.experimental.model.SoftwareFactoryConfiguration;
import org.komea.experimental.model.KomeaConfiguration;

public class ApplicationDataProducerTests
{
    
    @Test
    @Ignore
    public void test() throws IOException {
    
        KomeaConfiguration komea = new KomeaConfiguration("localhost:2424", "localhost:2424");
        SoftwareFactoryConfiguration configuration = new SoftwareFactoryConfiguration(new File("/Users/afloch/Documents/git/mongo"),
                "https://jira.mongodb.org/", "mongodb");
        
        ApplicationEventsProducer analyzer = new ApplicationEventsProducer(configuration, komea);
        analyzer.connect("root", "root");
        analyzer.pushJiraEvents();
        
        analyzer.close();
    }
}
