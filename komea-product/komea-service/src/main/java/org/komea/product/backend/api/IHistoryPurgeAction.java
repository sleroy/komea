
package org.komea.product.backend.api;



/**
 */
public interface IHistoryPurgeAction
{
    
    
    /**
     * Purges the history and returns the number of measures removed.
     * 
    
     * @return the number of measures removed. */
    int purgeHistory();
}
