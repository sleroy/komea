/**
 * 
 */

package org.komea.product.backend.criterias;



import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.KpiKey;



/**
 * This class defines a criteria to find a kpi It returns an exception if not found
 * 
 * @author sleroy
 */
public class FindKpiOrFail extends FindKpi
{
    
    
    /**
     * Find a kpi or fail
     * 
     * @param _kpiKey
     *            the kpi key
     */
    public FindKpiOrFail(final KpiKey _kpiKey, final KpiDao _kpiDao) {
    
    
        super(_kpiKey, _kpiDao);
        
    }
    
    
    /**
     * Find a kpi or returns an exception.
     */
    @Override
    public Kpi find() {
    
    
        final Kpi findKPI = super.find();
        if (findKPI == null) { throw new KPINotFoundRuntimeException(getKpiKey().getKpiName()); }
        return findKPI;
    }
    
}
