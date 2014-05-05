/**
 * 
 */

package org.komea.product.backend.criterias;



import org.apache.commons.lang.Validate;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiCriteria;
import org.komea.product.service.dto.KpiKey;



/**
 * This class defines a criteria to find a kpi It returns an exception if not found
 * 
 * @author sleroy
 */
public class FindKpi
{
    
    
    private final KpiDao kpiDao;
    private final KpiKey kpiKey;
    
    
    
    /**
     * Find a kpi or fail
     * 
     * @param _kpiKey
     *            the kpi key
     */
    public FindKpi(final KpiKey _kpiKey, final KpiDao _kpiDao) {
    
    
        super();
        kpiKey = _kpiKey;
        kpiDao = _kpiDao;
    }
    
    
    public Kpi find() {
    
    	Validate.isTrue(!kpiKey.isAssociatedToEntity());
        final KpiCriteria kpiCriteria = new KpiCriteria();
        kpiCriteria.createCriteria().andKpiKeyEqualTo(kpiKey.getKpiName());
        return CollectionUtil.singleOrNull(kpiDao.selectByCriteriaWithBLOBs(kpiCriteria));
    }
    
    
    public KpiDao getKpiDao() {
    
    
        return kpiDao;
    }
    
    
    public KpiKey getKpiKey() {
    
    
        return kpiKey;
    }
    
}
