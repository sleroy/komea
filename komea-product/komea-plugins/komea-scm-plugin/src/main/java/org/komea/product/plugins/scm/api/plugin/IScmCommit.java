/**
 * 
 */

package org.komea.product.plugins.scm.api.plugin;



import org.joda.time.DateTime;
import org.komea.product.database.model.Person;



/**
 * This interface defines a commit.
 * 
 * @author sleroy
 */
public interface IScmCommit
{
    
    
    /**
     * Returns the author of the commit
     * 
     * @return the author
     */
    Person getAuthor();
    
    
    /**
     * Returns the commit time.
     * 
     * @return the commit time.
     */
    DateTime getCommitTime();
    
    
    /**
     * Returns the id of the commit.
     * 
     * @return the id
     */
    String getId();
    
    
    /**
     * Returns the message of the commit
     * 
     * @return the message of the commit.
     */
    String getMessage();
}
