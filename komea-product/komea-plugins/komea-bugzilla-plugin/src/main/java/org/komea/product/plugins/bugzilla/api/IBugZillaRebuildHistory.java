/**
 *
 */

package org.komea.product.plugins.bugzilla.api;



import java.util.concurrent.ExecutorService;



/**
 * @author sleroy
 */
public interface IBugZillaRebuildHistory
{


    /**
     * Returns the importation thread.
     *
     * @return the importation thread.
     */
    ExecutorService getThread();


    /**
     * @return
     */
    boolean isRunning();
    
    
    /**
     * Launch a thread to build the history of the bugzilla servers.
     */
    void rebuildHistory();

}
