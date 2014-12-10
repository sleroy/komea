/**
 * 
 */

package org.komea.connectors.scm;



import java.util.Date;



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
    String getAuthor();
    
    
    /**
     * Returns the commit time.
     * 
     * @return the commit time.
     */
    Date getCommitTime();
    
    
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
    String getCommitMessage();
    
    
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
    String getProject();
    
    
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
     * Returns true if the commit has complete informations (author, project, message)
     * 
     * @return
     */
    boolean hasCompleteInformations();
    
    
    /**
     * Returns true if a project is associated to this commit.
     */
    boolean hasProject();
}
