
package org.komea.connectors.git;


import java.util.Date;

public interface IGitCommit
{
    
    /**
     * Returns the repository url of the commit
     * 
     * @return the commit repository
     */
    String getRepositoryUrl();
    
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
     * Returns the number of deleted lines + the number of added lines + number of modified lines into this commit.
     * 
     * @return the total number of modified lines.
     */
    int getTotalNumberOfModifiedLines();
    
    
    int getCommitMessageSize();
    
    void setNumberOfAddedLines(int addedLines);
    
    void setNumberOfDeletedLines(int deletedLines);
    
    void setNumberOfChangedLines(int changedLines);
    
    void setNumberOfModifiedFiles(int size);

    void setShProject(String project);

    public abstract String getCommitter();
    
}
