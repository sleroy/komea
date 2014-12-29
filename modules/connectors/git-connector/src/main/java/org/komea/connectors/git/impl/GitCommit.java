/**
 *
 */

package org.komea.connectors.git.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.eclipse.jgit.lib.PersonIdent;
import org.komea.connectors.git.IFileUpdate;
import org.komea.connectors.git.IGitCommit;

import com.google.common.collect.Lists;

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
    private static final long           serialVersionUID      = -1022783438679860198L;
    
    private int                         commitMessageSize;
    private final String                author;
    private final String                committer;
    
    private final Date                  commitTime;
    
    private final String                id;
    
    private String                      commitMessage         = "";
    
    private int                         numberOfAddedLines    = 0;
    
    private int                         numberOfDeletedLines  = 0;
    
    private int                         numberOfModifiedFiles = 0;
    
    private String                      repositoryUrl;
    
    private String                      shProject;
    
    private List<String>                branches;
    private transient List<IFileUpdate> modifications;
    
    public GitCommit(final String _id, final PersonIdent author, final PersonIdent committer, final String fullMessage) {
    
        this.commitTime = author.getWhen();
        this.id = _id;
        this.commitMessage = fullMessage;
        this.author = author.getEmailAddress();
        this.committer = committer.getEmailAddress();
        this.modifications = Lists.newArrayList();
        this.branches = Lists.newArrayList();
        
    }
    
    @Override
    public void addFileUpdate(final IFileUpdate modification) {
    
        this.modifications.add(modification);
        
    }
    
    /**
     * @return the author
     */
    @Override
    public String getAuthor() {
    
        return this.author;
    }
    
    @Override
    public List<String> getBranches() {
    
        return this.branches;
    }
    
    /**
     * @return the commitMessage
     */
    @Override
    public String getCommitMessage() {
    
        return this.commitMessage;
    }
    
    @Override
    public int getCommitMessageSize() {
    
        return this.commitMessageSize;
    }
    
    @Override
    public String getCommitter() {
    
        return this.committer;
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
    public List<IFileUpdate> getModifications() {
    
        return this.modifications;
    }
    
    @Override
    public int getNumberOfAddedLines() {
    
        return this.numberOfAddedLines;
    }
    
    @Override
    public int getNumberOfDeletedLines() {
    
        return this.numberOfDeletedLines;
    }
    
    @Override
    public int getNumberOfModifiedFiles() {
    
        return this.numberOfModifiedFiles;
    }
    
    @Override
    public String getRepositoryUrl() {
    
        return this.repositoryUrl;
    }
    @Override
    public String getShProject() {
    
        return this.shProject;
    }
    
    @Override
    public int getTotalNumberOfModifiedLines() {
    
        return this.numberOfAddedLines + this.numberOfDeletedLines;
    }
    
    @Override
    public void setBranches(final List<String> branches) {
    
        this.branches = branches;
    }
    
    public void setCommitMessageSize(final int commitMessageSize) {
    
        this.commitMessageSize = commitMessageSize;
    }
    
    /**
     * @param _numberOfAddedLines
     *            the numberOfAddedLines to set
     */
    @Override
    public void setNumberOfAddedLines(final int _numberOfAddedLines) {
    
        this.numberOfAddedLines = _numberOfAddedLines;
    }
    
    /**
     * @param _numberOfDeletedLines
     *            the numberOfDeletedLines to set
     */
    @Override
    public void setNumberOfDeletedLines(final int _numberOfDeletedLines) {
    
        this.numberOfDeletedLines = _numberOfDeletedLines;
    }
    
    /**
     * @param _numberOfModifiedFiles
     *            the numberOfModifiedFiles to set
     */
    @Override
    public void setNumberOfModifiedFiles(final int _numberOfModifiedFiles) {
    
        this.numberOfModifiedFiles = _numberOfModifiedFiles;
    }
    
    public void setRepositoryUrl(final String repositoryUrl) {
    
        this.repositoryUrl = repositoryUrl;
    }
    
    @Override
    public void setShProject(final String shProject) {
    
        this.shProject = shProject;
    }
    
    @Override
    public String toString() {
    
        return "ScmCommit{" + "author=" + getAuthor() + ", commitTime=" + this.commitTime + ", id=" + this.id + ", commitMessage="
                + this.commitMessage + ", numberOfAddedLines=" + this.numberOfAddedLines + ", numberOfDeletedLines="
                + this.numberOfDeletedLines + ", numberOfModifiedFiles=" + this.numberOfModifiedFiles + '}';
    }
    
}
