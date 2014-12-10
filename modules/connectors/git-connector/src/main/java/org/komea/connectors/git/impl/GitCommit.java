/**
 *
 */

package org.komea.connectors.git.impl;


import java.io.Serializable;
import java.util.Date;

import org.eclipse.jgit.lib.PersonIdent;
import org.komea.connectors.git.IGitCommit;

/**
 * This class implements a scm commit;
 *
 * @author sleroy
 */
public class GitCommit implements IGitCommit, Serializable
{
    
    /**
     * 
     */
    private static final long  serialVersionUID      = -1022783438679860198L;
    
    public static final String GIT                   = "git";
    
    public static final String COMMIT_EVENT_TYPE     = "commit";
    
    private int                commitMessageSize;
    private final String             author;
    private final String             committer;
    
    private final Date               commitTime;
    
    private final String             id;
    
    private String             commitMessage         = "";
    
    private int                numberOfAddedLines    = 0;
    
    private int                numberOfChangedLines  = 0;
    
    private int                numberOfDeletedLines  = 0;
    
    private int                numberOfModifiedFiles = 0;
    
    private String             repositoryUrl;
    
    private String             shProject;
    

    
    public GitCommit(final String _id, final PersonIdent author, final PersonIdent committer, final String fullMessage) {
    
        this.commitTime = author.getWhen();
        this.id = _id;
        this.commitMessage = fullMessage;
        this.author = author.getEmailAddress();
        this.committer = committer.getEmailAddress();
        
    }
    
    public void setShProject(final String shProject) {
    
        this.shProject = shProject;
    }
    
    public String getShProject() {
    
        return this.shProject;
    }
    
    /**
     * @return the author
     */
    @Override
    public String getAuthor() {
    
        return this.author;
    }
    
    @Override
    public String getCommitter() {
    
        return this.committer;
    }
    
    /**
     * @return the commitMessage
     */
    @Override
    public String getCommitMessage() {
    
        return this.commitMessage;
    }
    
    /**
     * @return the commitTime
     */
    @Override
    public Date getCommitTime() {
    
        return this.commitTime;
    }
    
    /**
     * @return the id
     */
    @Override
    public String getId() {
    
        return this.id;
    }
    
    @Override
    public int getNumberOfAddedLines() {
    
        return this.numberOfAddedLines;
    }
    
    @Override
    public int getNumberOfChangedLines() {
    
        return this.numberOfChangedLines;
    }
    
    @Override
    public int getNumberOfDeletedLines() {
    
        return this.numberOfDeletedLines;
    }
    
    @Override
    public int getNumberOfModifiedFiles() {
    
        return this.numberOfModifiedFiles;
    }
    
    /**
     * @param _numberOfAddedLines
     *            the numberOfAddedLines to set
     */
    public void setNumberOfAddedLines(final int _numberOfAddedLines) {
    
        this.numberOfAddedLines = _numberOfAddedLines;
    }
    
    public void setNumberOfChangedLines(final int _numberOfChangedLines) {
    
        this.numberOfChangedLines = _numberOfChangedLines;
    }
    
    /**
     * @param _numberOfDeletedLines
     *            the numberOfDeletedLines to set
     */
    public void setNumberOfDeletedLines(final int _numberOfDeletedLines) {
    
        this.numberOfDeletedLines = _numberOfDeletedLines;
    }
    
    /**
     * @param _numberOfModifiedFiles
     *            the numberOfModifiedFiles to set
     */
    public void setNumberOfModifiedFiles(final int _numberOfModifiedFiles) {
    
        this.numberOfModifiedFiles = _numberOfModifiedFiles;
    }
    @Override
    public int getTotalNumberOfModifiedLines() {
    
        return this.numberOfAddedLines + this.numberOfChangedLines + this.numberOfModifiedFiles;
    }
    
    public String getRepositoryUrl() {
    
        return this.repositoryUrl;
    }
    
    public void setRepositoryUrl(final String repositoryUrl) {
    
        this.repositoryUrl = repositoryUrl;
    }
    
    @Override
    public String toString() {
    
        return "ScmCommit{" + "author=" + this.getAuthor() + ", commitTime=" + this.commitTime + ", id=" + this.id + ", commitMessage="
                + this.commitMessage + ", numberOfAddedLines=" + this.numberOfAddedLines + ", numberOfChangedLines="
                + this.numberOfChangedLines + ", numberOfDeletedLines=" + this.numberOfDeletedLines + ", numberOfModifiedFiles="
                + this.numberOfModifiedFiles + '}';
    }
    
    @Override
    public int getCommitMessageSize() {
    
        return this.commitMessageSize;
    }
    
    public void setCommitMessageSize(final int commitMessageSize) {
    
        this.commitMessageSize = commitMessageSize;
    }
    
}
