/**
 * 
 */

package org.komea.product.plugins.datasource;



import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.IFilter;
import org.komea.product.plugins.model.IDynamicDataTable;



/**
 * @author sleroy
 */
public class DynamicDataTable<T extends Serializable> implements IDynamicDataTable<T>
{
    
    
    /**
     * 
     */
    private static final long serialVersionUID = -828432018703334405L;
    private final List<T>     issues;
    
    
    
    public DynamicDataTable(final List<T> _issues) {
    
    
        issues = _issues == null ? Collections.EMPTY_LIST : _issues;
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataTable#getData()
     */
    @Override
    public List<T> getData() {
    
    
        return issues;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataTable#isEmpty()
     */
    @Override
    public boolean isEmpty() {
    
    
        return issues.isEmpty();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataTable#searchData(org.komea.product.plugins.model.IFilter)
     */
    @Override
    public List<T> searchData(final IFilter<T> _dataFilter) {
    
    
        return CollectionUtil.filter(issues, _dataFilter);
    }
    
}
