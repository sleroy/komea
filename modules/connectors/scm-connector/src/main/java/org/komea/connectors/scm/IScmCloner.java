/**
 * 
 */

package org.komea.connectors.scm;



/**
 * This interface must be implemented to read a repository from the cron job.
 * 
 * @author sleroy
 */
public interface IScmCloner
{
    
    
    /**
     * Clone a repository on the local disk
     * 
     * @throws an
     *             exception if the repository is already cloned.
     */
    void cloneRepository();
}
