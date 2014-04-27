/**
 * 
 */

package org.komea.product.plugins.scm.api.plugin;



import java.io.Serializable;

import org.joda.time.DateTime;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;



/**
 * This interface defines a commit.
 * 
 * @author sleroy
 */
public interface IScmCommit extends Serializable
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
    
    
    /**
     * Returns the number of added lines in this commit.
     * 
     * @return the number of added lines
     */
    int getNumberOfAddedLines();
    
    
    /**
     * Returns the number of changed lines in this commit.
     * 
     * @return the number of changed lines
     */
    int getNumberOfChangedLines();
    
    
    /**
     * Returns the number of deleted lines in this commit.
     * 
     * @return the number of deleted lines
     */
    int getNumberOfDeletedLines();
    
    
    /**
     * Returns the number of modified lines
     * 
     * @return the number of modified lines.
     */
    int getNumberOfModifiedFiles();
    
    
    /**
     * Returns the project associated to the commit.
     */
    Project getProject();
    
    
    /**
     * Returns the number of deleted lines + the number of added lines + number of modified lines into this commit.
     * 
     * @return the total number of modified lines.
     */
    int getTotalNumberOfModifiedLines();
    
    
    /**
     * Returns true if an author is associated to this commit.
     */
    boolean hasAuthor();
    
    
    /**
     * Returns true if a project is associated to this commit.
     */
    boolean hasProject();
}
