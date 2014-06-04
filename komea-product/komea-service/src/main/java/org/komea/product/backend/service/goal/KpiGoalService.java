/**
 * 
 */

package org.komea.product.backend.service.goal;



import java.util.List;

import org.apache.commons.lang3.Validate;
import org.komea.product.backend.genericservice.AbstractService;
import org.komea.product.backend.service.IKpiGoalService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dao.IGenericDAO;
import org.komea.product.database.dao.KpiGoalDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.KpiGoal;
import org.komea.product.database.model.KpiGoalCriteria;
import org.komea.product.database.model.KpiGoalCriteria.Criteria;
import org.komea.product.service.dto.KpiKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * This class provides all required methods to handle kpi goals.
 * 
 * @author sleroy
 */
@Service
@Transactional
public class KpiGoalService extends AbstractService<KpiGoal, Integer, KpiGoalCriteria> implements
        IKpiGoalService
{
    
    
    @Autowired
    private IEntityService entityService;
    
    @Autowired
    private KpiGoalDao     kpiGoalDao;
    
    @Autowired
    private IKPIService    kpiService;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.IKpiGoalService#findKpiGoals(org.komea.product.service.dto.KpiKey,
     * org.komea.product.service.dto.EntityKey)
     */
    @Override
    public List<KpiGoal> findKpiGoals(final KpiKey _kpiKey) {
    
    
        final Kpi kpiOrFail = kpiService.findKPIOrFail(_kpiKey.getKpiName());
        final KpiGoalCriteria kpiGoalCriteria = new KpiGoalCriteria();
        final Criteria criteria = kpiGoalCriteria.createCriteria();
        criteria.andIdKpiEqualTo(kpiOrFail.getId());
        
        if (_kpiKey.isAssociatedToEntity()) {
            final IEntity findEntityByEntityKey =
                    entityService.findEntityByEntityKey(_kpiKey.getEntityKey());
            Validate.notNull(findEntityByEntityKey);
            criteria.andEntityIDEqualTo(findEntityByEntityKey.getId());
        }
        
        return kpiGoalDao.selectByCriteria(kpiGoalCriteria);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.genericservice.AbstractService#getRequiredDAO()
     */
    @Override
    public IGenericDAO<KpiGoal, Integer, KpiGoalCriteria> getRequiredDAO() {
    
    
        return kpiGoalDao;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.genericservice.AbstractService#createKeyCriteria(java.lang.String)
     */
    @Override
    protected KpiGoalCriteria createKeyCriteria(final String _key) {
    
    
        throw new UnsupportedOperationException("Kpi Goal does not define any key");
    }
    
    
}
