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
public abstract class AbstractTeamDynamicQuery extends AbstractDynamicQuery
{
    
    
    @Autowired
    private IPersonGroupService personGroupService;
    
    
    
    public AbstractTeamDynamicQuery(final BackupDelay _delay) {
    
    
        super(EntityType.TEAM, _delay);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.groovy.AbstractDynamicQuery#getResult()
     */
    @Override
    public KpiResult evaluateResult() {
    
    
        final KpiResult kpiResult = new KpiResult();
        for (final PersonGroup person : personGroupService.getAllTeamsPG()) {
            kpiResult.put(person, evaluateTeam(person));
        }
        
        return kpiResult;
    }
    
    
    protected abstract Number evaluateTeam(PersonGroup _person);
    
}
