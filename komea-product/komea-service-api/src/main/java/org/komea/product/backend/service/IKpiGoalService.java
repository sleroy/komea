/**
 * 
 */

package org.komea.product.backend.service;



import java.util.List;

import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.model.KpiGoal;
import org.komea.product.database.model.KpiGoalCriteria;
import org.komea.product.service.dto.KpiKey;



/**
 * @author sleroy
 */
public interface IKpiGoalService extends IGenericService<KpiGoal, Integer, KpiGoalCriteria>
{
    
    
    /**
     * Returns a list of kpi goals for a given entity and a kpi.
     * 
     * @return the list of kpi goals.
     */
    List<KpiGoal> findKpiGoals(KpiKey _kpiKey);
}
