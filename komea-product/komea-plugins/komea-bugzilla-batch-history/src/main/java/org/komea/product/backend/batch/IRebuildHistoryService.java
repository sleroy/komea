/**
 * 
 */

package org.komea.product.backend.batch;



import org.apache.ibatis.session.SqlSession;



/**
 * @author sleroy
 *
 */
public interface IRebuildHistoryService
{
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public abstract void run();
    
    
    /**
     * @param _openSession
     */
    public abstract void setMyBatis(SqlSession _openSession);
    
}
