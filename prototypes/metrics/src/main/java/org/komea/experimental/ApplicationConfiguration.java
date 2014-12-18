
package org.komea.experimental;


import java.io.File;

public class ApplicationConfiguration
{
    
    private final File   repositoryFolder;
    private final String jiraUrl;
    private final String name;
    
    public ApplicationConfiguration(final File repositoryFolder, final String jiraUrl, final String name) {
    
        super();
        this.repositoryFolder = repositoryFolder;
        this.jiraUrl = jiraUrl;
        this.name = name;
        
    }
    
    public File getRepositoryFolder() {
    
        return this.repositoryFolder;
    }
    
    public String getJiraUrl() {
    
        return this.jiraUrl;
    }
    
    public String getName() {
    
        return this.name;
    }
    
    @Override
    public String toString() {
    
        return this.name;
    }
}
