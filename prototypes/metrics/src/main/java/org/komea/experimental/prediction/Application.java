
package org.komea.experimental.prediction;


import java.util.List;

import com.google.common.collect.Lists;

public class Application
{
    
    private final String       name;
    private final String       branch;
    
    private final List<String> sourcePaths;
    
    public Application(final String name, final String branch, final String path) {
    
        super();
        this.name = name;
        this.branch = branch;
        this.sourcePaths = Lists.newArrayList();
        addSourcePath(path);
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
