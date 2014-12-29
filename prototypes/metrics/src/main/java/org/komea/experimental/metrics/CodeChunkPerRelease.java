
package org.komea.experimental.metrics;


import org.komea.experimental.CommitsDao;
import org.komea.experimental.prediction.Release;
import org.komea.experimental.prediction.ReleaseCodeChunk;

public class CodeChunkPerRelease
{
    
    private final CommitsDao dao;
    
    public CodeChunkPerRelease(final CommitsDao dao) {
    
        super();
        this.dao = dao;
    }
    
    public ReleaseCodeChunk chunk(final Release release) {
    
        Release previous = this.dao.findPreviousRelease(release);
        if (previous != null) {
            int chunk = this.dao.countNumberOfModifiedLines(previous.getDate(), release.getDate());
            return new ReleaseCodeChunk(release, previous, chunk);
        }else{
            return null;
        }
    }
    
    public ReleaseCodeChunk chunk(final String name) {
    
        Release release = this.dao.findRelease(name);
        
        if (release != null) {
            return chunk(release);
        }
        return null;
    }
    
}
