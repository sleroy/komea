/**
 * 
 */

package org.komea.product.plugins.scm.api.plugin;



import org.joda.time.DateTime;
import org.komea.product.database.model.Person;



/**
 * This class implements a scm commit;
 * 
 * @author sleroy
 */
public class ScmCommit implements IScmCommit
{
    
    
    private Person   author;
    
    
    private DateTime commitTime;
    
    
    private String   id;
    
    
    private String   message;
    
    
    private int      numberOfAddedlines    = 0;
    
    
    private int      numberOfChangedLines  = 0;
    
    
    private int      numberofDeletedLines  = 0;
    
    private int      numberOfModifiedFiles = 0;
    
    
    
    /**
     * 
     */
    public ScmCommit() {
    
    
        super();
    }
    
    
    public ScmCommit(
            final Person _author,
            final DateTime _commitTime,
            final String _id,
            final String _message) {
    
    
        super();
        author = _author;
        commitTime = _commitTime;
        id = _id;
        message = _message;
    }
    
    
    /**
     * @return the author
     */
    @Override
    public Person getAuthor() {
    
    
        return author;
    }
    
    
    /**
     * @return the commitTime
     */
    @Override
    public DateTime getCommitTime() {
    
    
        return commitTime;
    }
    
    
    /**
     * @return the id
     */
    @Override
    public String getId() {
    
    
        return id;
    }
    
    
    /**
     * @return the message
     */
    @Override
    public String getMessage() {
    
    
        return message;
    }
    
    
    public int getNumberOfAddedlines() {
    
    
        return numberOfAddedlines;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmCommit#getNumberOfAddedLines()
     */
    @Override
    public int getNumberOfAddedLines() {
    
    
        return numberOfAddedlines;
    }
    
    
    @Override
    public int getNumberOfChangedLines() {
    
    
        return numberOfChangedLines;
    }
    
    
    public int getNumberofDeletedLines() {
    
    
        return numberofDeletedLines;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmCommit#getNumberOfDeletedLines()
     */
    @Override
    public int getNumberOfDeletedLines() {
    
    
        return numberofDeletedLines;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmCommit#getNumberOfModifiedFiles()
     */
    @Override
    public int getNumberOfModifiedFiles() {
    
    
        return numberOfModifiedFiles;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.scm.api.plugin.IScmCommit#getTotalNumberOfModifiedLines()
     */
    @Override
    public int getTotalNumberOfModifiedLines() {
    
    
        return getNumberOfAddedLines() + getNumberOfDeletedLines() + getNumberOfChangedLines();
    }
    
    
    /**
     * @param _author
     *            the author to set
     */
    public void setAuthor(final Person _author) {
    
    
        author = _author;
    }
    
    
    /**
     * @param _commitTime
     *            the commitTime to set
     */
    public void setCommitTime(final DateTime _commitTime) {
    
    
        commitTime = _commitTime;
    }
    
    
    /**
     * @param _id
     *            the id to set
     */
    public void setId(final String _id) {
    
    
        id = _id;
    }
    
    
    /**
     * @param _message
     *            the message to set
     */
    public void setMessage(final String _message) {
    
    
        message = _message;
    }
    
    
    /**
     * @param _numberOfAddedlines
     *            the numberOfAddedlines to set
     */
    public void setNumberOfAddedlines(final int _numberOfAddedlines) {
    
    
        numberOfAddedlines = _numberOfAddedlines;
    }
    
    
    public void setNumberOfChangedLines(final int _numberOfChangedLines) {
    
    
        numberOfChangedLines = _numberOfChangedLines;
    }
    
    
    /**
     * @param _numberofDeletedLines
     *            the numberofDeletedLines to set
     */
    public void setNumberofDeletedLines(final int _numberofDeletedLines) {
    
    
        numberofDeletedLines = _numberofDeletedLines;
    }
    
    
    /**
     * @param _numberOfModifiedFiles
     *            the numberOfModifiedFiles to set
     */
    public void setNumberOfModifiedFiles(final int _numberOfModifiedFiles) {
    
    
        numberOfModifiedFiles = _numberOfModifiedFiles;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "ScmCommit [author="
                + author + ", commitTime=" + commitTime + ", id=" + id + ", message=" + message
                + ", numberOfAddedlines=" + numberOfAddedlines + ", numberofDeletedLines="
                + numberofDeletedLines + ", numberOfModifiedFiles=" + numberOfModifiedFiles + "]";
    }
    
    
}
