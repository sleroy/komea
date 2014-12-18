
package org.komea.experimental.model;


import java.util.List;

import com.google.common.collect.Lists;

public class AnalyzedApplication
{
    
    private final String                       name;
    private final String                       branch;
    
    private final List<String>                 sourcePaths;
    
    private final SoftwareFactoryConfiguration configuration;
    
    public AnalyzedApplication(final SoftwareFactoryConfiguration config, final String branch, final String path) {
    
        super();
        this.name = config.getName();
        this.branch = branch;
        this.sourcePaths = Lists.newArrayList();
        this.configuration = config;
        addSourcePath(path);
    }
    
    public SoftwareFactoryConfiguration getConfiguration() {
    
        return this.configuration;
    }
    
    public String getBranch() {
    
        return this.branch;
    }
    
    public String getName() {
    
        return this.name;
    }
    
    public List<String> getSourcePaths() {
    
        return this.sourcePaths;
    }
    
    public void addSourcePath(final String path) {
    
        this.sourcePaths.add(path);
    }
    
}
