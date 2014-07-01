/**
 *
 */

package org.komea.product.backend.batch;



import org.komea.product.backend.utils.IFilter;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.bugtracker.kpis.IssueFilterKPI;



/**
 * @author sleroy
 */
public class KpiAndQueryObject
{
    
    
    private Kpi            kpi;


    private IFilter        originalFilter;


    private IssueFilterKPI query;
    
    
    
    /**
     * @param _kpi
     * @param _originalFilter
     * @param _query
     */
    public KpiAndQueryObject(
            final Kpi _kpi,
            final IFilter _originalFilter,
            final IssueFilterKPI _query) {


        super();
        kpi = _kpi;
        originalFilter = _originalFilter;
        query = _query;
    }


    /**
     * Returns the value of the field kpi.
     * 
     * @return the kpi
     */
    public Kpi getKpi() {


        return kpi;
    }


    /**
     * Returns the value of the field originalFilter.
     * 
     * @return the originalFilter
     */
    public IFilter getOriginalFilter() {


        return originalFilter;
    }
    
    
    /**
     * Returns the value of the field query.
     * 
     * @return the query
     */
    public IssueFilterKPI getQuery() {


        return query;
    }
    
    
    /**
     * Sets the field kpi with the value of _kpi.
     * 
     * @param _kpi
     *            the kpi to set
     */
    public void setKpi(final Kpi _kpi) {


        kpi = _kpi;
    }
    
    
    /**
     * Sets the field originalFilter with the value of _originalFilter.
     * 
     * @param _originalFilter
     *            the originalFilter to set
     */
    public void setOriginalFilter(final IFilter _originalFilter) {


        originalFilter = _originalFilter;
    }
    
    
    /**
     * Sets the field query with the value of _query.
     * 
     * @param _query
     *            the query to set
     */
    public void setQuery(final IssueFilterKPI _query) {


        query = _query;
    }
}
