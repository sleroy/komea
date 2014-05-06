/**
 * 
 */

package org.komea.product.backend.criterias;



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
    private final String kpiKey;
    
    
    
    public FindKpi(final KpiKey _ofKpiName, final KpiDao _kpiDAO) {
    
    
        this(_ofKpiName.getKpiName(), _kpiDAO);
    }
    
    
    /**
     * Find a kpi or fail
     * 
     * @param _kpiKey
     *            the kpi key
     */
    public FindKpi(final String _kpiKey, final KpiDao _kpiDao) {
    
    
        super();
        kpiKey = _kpiKey;
        kpiDao = _kpiDao;
    }
    
    
    public Kpi find() {
    
    
        final KpiCriteria kpiCriteria = new KpiCriteria();
        kpiCriteria.createCriteria().andKpiKeyEqualTo(kpiKey);
        return CollectionUtil.singleOrNull(kpiDao.selectByCriteriaWithBLOBs(kpiCriteria));
    }
    
    
    public KpiDao getKpiDao() {
    
    
        return kpiDao;
    }
    
    
    public String getKpiKey() {
    
    
        return kpiKey;
    }
    
    
}
