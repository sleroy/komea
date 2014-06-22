/**
 *
 */

package org.komea.product.plugins.bugzilla.api;



/**
 * @author sleroy
 */
public interface IBugZillaRebuildHistory
{


    /**
     * Launch a thread to build the history of the bugzilla servers.
     */
    void rebuildHistory();

}
