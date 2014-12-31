
package org.komea.experimental.model;


import java.util.List;

import com.google.common.collect.Lists;

public class AnalyzedApplication
{
    
    private final String                       name;
    private final String                       branch;
    
    private final List<String>                 excludedPaths;
    
    private final SoftwareFactoryConfiguration configuration;
    
    public AnalyzedApplication(final SoftwareFactoryConfiguration config, final String branch, final String ...paths) {
    
        super();
        this.name = config.getName();
        this.branch = branch;
        this.excludedPaths = Lists.newArrayList();
        this.configuration = config;
        for (int i = 0; i < paths.length; i++) {
            addExcludedPath(paths[i]);
        }
       
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
    
    public List<String> getExcludedPaths() {
    
        return this.excludedPaths;
    }
    
    public void addExcludedPath(final String path) {
    
        this.excludedPaths.add(path);
    }
    
}
