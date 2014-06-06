/**
 * 
 */

package org.komea.product.backend.groovy;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.PersonGroup;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public abstract class AbstractDepartmentDynamicQuery extends AbstractDynamicQuery
{
    
    
    @Autowired
    private IPersonGroupService personGroupService;
    
    
    
    public AbstractDepartmentDynamicQuery(final BackupDelay _delay) {
    
    
        super(EntityType.DEPARTMENT, _delay);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.groovy.AbstractDynamicQuery#getResult()
     */
    @Override
    public KpiResult evaluateResult() {
    
    
        final KpiResult kpiResult = new KpiResult();
        for (final PersonGroup person : personGroupService.getAllDepartmentsPG()) {
            kpiResult.put(person, evaluateDepartment(person));
        }
        
        return kpiResult;
    }
    
    
    protected abstract Number evaluateDepartment(PersonGroup _person);
    
}
