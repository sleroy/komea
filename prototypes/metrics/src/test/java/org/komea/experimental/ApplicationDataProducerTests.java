
package org.komea.experimental;


import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class ApplicationDataProducerTests
{
    
    @Test
    public void test() throws IOException {
    
        KomeaConfiguration komea = new KomeaConfiguration("localhost:2424", "localhost:2424");
        ApplicationConfiguration configuration = new ApplicationConfiguration(new File("/Users/afloch/Documents/git/mongo"),
                "https://jira.mongodb.org/", "mongodb");
        
        ApplicationAnalyzer analyzer = new ApplicationAnalyzer(configuration, komea);
        analyzer.connect("root", "root");
        analyzer.pushGitEvents();
        
        analyzer.close();
    }
}
