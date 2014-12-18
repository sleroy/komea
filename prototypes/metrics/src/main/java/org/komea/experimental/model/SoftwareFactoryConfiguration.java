
package org.komea.experimental.model;


import java.io.File;

public class SoftwareFactoryConfiguration
{
    
    private final File   repositoryFolder;
    private final String jiraUrl;
    private final String name;
    
    public SoftwareFactoryConfiguration(final File repositoryFolder, final String jiraUrl, final String name) {
    
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
