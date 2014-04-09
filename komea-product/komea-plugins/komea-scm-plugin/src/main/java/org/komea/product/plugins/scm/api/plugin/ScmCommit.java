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
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "ScmCommit [author="
                + author + ", commitTime=" + commitTime + ", id=" + id + ", message=" + message
                + "]";
    }
    
    
}
