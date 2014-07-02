/**
 *
 */

package org.komea.product.backend.batch;



import org.komea.product.database.dao.BugzillaDao;



/**
 * @author sleroy
 */
public interface IRebuildHistoryService
{


    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public abstract void run();
    
    
    /**
     * @param _mapper
     */
    void setMapper(BugzillaDao _mapper);


}
