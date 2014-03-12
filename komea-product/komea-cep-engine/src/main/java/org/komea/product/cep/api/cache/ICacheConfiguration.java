/**
 * 
 */

package org.komea.product.cep.api.cache;



import java.io.Serializable;
import java.util.concurrent.TimeUnit;



/**
 * @author sleroy
 */
public interface ICacheConfiguration extends Serializable
{
    
    
    /**
     * Returns the maximum size allowed in the cache.
     * 
     * @return the maximumSize
     */
    public Integer getMaximumSize();
    
    
    /**
     * Returns the time fragment of the time expiration policy
     */
    public Integer getTime();
    
    
    /**
     * Retruns the time unit used by expiration time policy
     */
    public TimeUnit getTimeUnit();
    
    
    /**
     * Tests if this cache configuration defines maximum size policy.
     */
    public boolean hasMaximumSizePolicy();
    
    
    /**
     * Tests if this cache does not define eviction policy.
     */
    public boolean hasNoPolicy();
    
    
    /**
     * Tests if this cache defines a time policy.
     */
    public boolean hasTimePolicy();
    
    
    /**
     * Tests if the stats are enabled.
     */
    boolean isEnableStats();
    
}
