
package org.komea.experimental;


import java.io.File;

import org.komea.event.query.impl.EventQueryManager;
import org.komea.experimental.metrics.CodeChunkPerRelease;
import org.komea.experimental.metrics.NbIssuesPerRelease;
import org.komea.experimental.model.AnalyzedApplication;
import org.komea.experimental.model.KomeaConfiguration;
import org.komea.experimental.model.SoftwareFactoryConfiguration;
import org.komea.experimental.prediction.Release;
import org.komea.experimental.prediction.ReleaseCodeChunk;
import org.springframework.orientdb.session.impl.RemoteDatabaseConfiguration;

public class CodeChunkPerReleaseBench
{
    
    public static void main(final String[] args) {
    
        KomeaConfiguration komea = new KomeaConfiguration("localhost:2424", "localhost:2424");
        SoftwareFactoryConfiguration configuration = new SoftwareFactoryConfiguration(new File("/Users/afloch/Documents/git/mongo"),
                "https://jira.mongodb.org/", "mongodb");
        
        AnalyzedApplication mongo = new AnalyzedApplication(configuration, "refs/heads/v2.6", "%third_party/", "jstests/", "docs/",
                "%dbtests/");
        
        EventQueryManager queries = new EventQueryManager(new RemoteDatabaseConfiguration(komea.getEventsDbUrl(), "events"));
        IReleaseTagConvertor convertor = new MongodbTagConvertor();
        
        ApplicationDAO dao = new ApplicationDAO(convertor, queries, mongo);
        
        CommitsDao commitsDao = new CommitsDao(convertor, queries, mongo);
        IssuesDao issuesDao = new IssuesDao(convertor, queries, mongo);
        
        // Release findRelease = dao.findRelease("2.6.5");
        // analyze(commitsDao, issuesDao, findRelease);
        
        for (Release release : dao.findAllReleases()) {
            
            analyze(commitsDao, issuesDao, release);
            
        }
        
    }
    
    private static void analyze(final CommitsDao commitsDao, final IssuesDao issuesDao, final Release release) {
    
        CodeChunkPerRelease kpi = new CodeChunkPerRelease(commitsDao);
        
        ReleaseCodeChunk chunk = kpi.chunk(release);
        if (chunk != null) {
            
            if (release != null) {
                
                NbIssuesPerRelease nbIssuesPerRelease = new NbIssuesPerRelease(issuesDao);
                nbIssuesPerRelease.setStrict(true);
                int countNbIssues = nbIssuesPerRelease.countNbIssues(release);
                System.out.println(release.geReleaseName() + "; " + chunk.getChunk() + ";" + countNbIssues);
            }
        }
    }
    
}
