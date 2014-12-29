
package org.komea.experimental.prediction;


public class ReleaseCodeChunk
{
    
    private final Release release;
    private final Release previousRelease;
    private final int     chunk;
    
    public ReleaseCodeChunk(final Release release, final Release previousRelease, final int chunk) {
    
        super();
        this.release = release;
        this.previousRelease = previousRelease;
        this.chunk = chunk;
    }
    
    
    public Release getRelease() {
    
        return this.release;
    }
    
    
    
    public int getChunk() {
    
        return this.chunk;
    }
    
    public Release getPreviousRelease() {
    
        return this.previousRelease;
    }
    
    @Override
    public String toString() {
    
        StringBuilder sb = new StringBuilder();
        sb.append(this.previousRelease).append("=>").append(this.release).append(" : ").append(this.chunk).append(" modified lines");
        return sb.toString();
    }
    
}
