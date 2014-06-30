/**
 *
 */

package org.komea.product.backend.batch;



import java.util.List;

import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.IFilter;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;



/**
 * @author sleroy
 */
public class MockIssuePlugin implements IIssuePlugin
{
    
    
    private final List<IIssue> issueList;



    /**
     * @param _existedInPastIssues
     */
    public MockIssuePlugin(final List<IIssue> _existedInPastIssues) {


        issueList = _existedInPastIssues;


    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssuePlugin#cleanCache()
     */
    @Override
    public void cleanCache() {
    
    
        //

    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataTable#getData()
     */
    @Override
    public List<IIssue> getData() {
    
    
        return issueList;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataTable#isEmpty()
     */
    @Override
    public boolean isEmpty() {
    
    
        return getData().isEmpty();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataTable#searchData(org.komea.product.backend.utils.IFilter)
     */
    @Override
    public List<IIssue> searchData(final IFilter<IIssue> _dataFilter) {
    
    
        return CollectionUtil.filter(getData(), _dataFilter);
    }
    
}
