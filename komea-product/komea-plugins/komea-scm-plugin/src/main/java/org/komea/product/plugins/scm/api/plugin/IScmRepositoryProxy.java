/**
 * 
 */

package org.komea.product.plugins.scm.api.plugin;



import java.io.Closeable;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;



/**
 * This interface defines a proxy above a scm repository (whatever it is git, svn , perforce) and provide main functionalities to obtain the
 * requested informations.
 * 
 * @author sleroy
 */
public interface IScmRepositoryProxy extends Closeable
{
    
    
    /**
     * Returns the list of commit of a branch past the last date (optional, may be null)
     * 
     * @param _lastDate
     *            Joda time date
     * @return the list of commits of a branch
     */
    List<IScmCommit> getAllCommitsFromABranch(String _branchName, DateTime _lastDate);
    
    
    /**
     * Returns the list of commit of a branch
     * 
     * @return the list of commits of a branch
     */
    Set<String> getAllTagsFromABranch(String _branchName);
    
    
    /**
     * Returns the list of branches of the repository.
     * 
     * @return the list of branches.
     */
    List<String> getBranches();
    
    
    /**
     * Returns the event factory.
     * 
     * @return the scm event factory.
     */
    IScmEventFactory getEventFactory();
    
    
    /**
     * Returns the repository definition.
     * 
     * @return the repository definition.
     */
    ScmRepositoryDefinition getRepositoryDefinition();
    
    
    /**
     * Returns the component to clone the repository on the local disk. The folder is stored in the SCM definition.
     * 
     * @return the repository cloner
     */
    IScmCloner getScmCloner();
    
    
    /**
     * Tests the connection to the scm repository.
     */
    void testConnection();
}
