
package org.komea.experimental;


import java.io.File;

import org.junit.Test;
import org.komea.event.query.impl.EventQueryManager;
import org.komea.experimental.kpis.CodeChunkPerRelease;
import org.komea.experimental.prediction.ReleaseCodeChunk;
import org.springframework.orientdb.session.impl.RemoteDatabaseConfiguration;

public class CodeChunkPerReleaseTests
{
    
    @Test
    public void test() {
    
        KomeaConfiguration komea = new KomeaConfiguration("localhost:2424", "localhost:2424");
        ApplicationConfiguration configuration = new ApplicationConfiguration(new File("/Users/afloch/Documents/git/mongo"),
                "https://jira.mongodb.org/", "mongodb");
        
        EventQueryManager queries = new EventQueryManager(new RemoteDatabaseConfiguration(komea.getEventsDbUrl(), "events"));
        CodeChunkPerRelease kpi = new CodeChunkPerRelease(queries);
        ReleaseCodeChunk chunk = kpi.chunk("r2.7.7");
        System.out.println(chunk);
    }
    
}
