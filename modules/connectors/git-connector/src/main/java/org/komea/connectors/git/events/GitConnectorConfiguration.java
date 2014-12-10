
package org.komea.connectors.git.events;


import java.io.File;

import org.joda.time.DateTime;

public class GitConnectorConfiguration
{
    
    private final DateTime since;
    private final String   project;
    private final DateTime until;
    private final File     repositoryFolder;
    
    public GitConnectorConfiguration(final String project, final File repositoryFolder, final DateTime since, final DateTime until) {
    
        super();
        this.since = since;
        this.until = until;
        this.repositoryFolder = repositoryFolder;
        this.project = project;
    }
    
    public DateTime getSince() {
    
        return this.since;
    }
    
    public DateTime getUntil() {
    
        return this.until;
    }
    
    public String getProject() {
    
        return this.project;
    }
    
    public File getRepositoryFolder() {
    
        return this.repositoryFolder;
    }
    
}
