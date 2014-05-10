
package org.komea.product.backend.olap;



public class Interval
{
    
    
    private final Clock clock;
    private long        start;
    
    
    
    /**
     * Interval with clock measurement
     * 
     * @param _clock
     */
    public Interval(final Clock _clock) {
    
    
        clock = _clock;
        
        
    }
    
    
    public void start() {
    
    
        start = clock.time();
    }
    
    
    public long stop() {
    
    
        return clock.time() - start;
    }
}
