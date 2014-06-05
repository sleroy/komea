/**
 * 
 */

package org.komea.product.backend.groovy;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Person;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public abstract class AbstractPersonDynamicQuery extends AbstractDynamicQuery
{
    
    
    @Autowired
    private IPersonService personService;
    
    
    
    public AbstractPersonDynamicQuery(final BackupDelay _delay) {
    
    
        super(EntityType.PERSON, _delay);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.groovy.AbstractDynamicQuery#getResult()
     */
    @Override
    public final KpiResult getResult() {
    
    
        initializationToEvaluateResults();
        final KpiResult kpiResult = new KpiResult();
        for (final Person person : personService.selectAll()) {
            kpiResult.put(person, evaluatePerson(person));
        }
        
        return kpiResult;
    }
    
    
    protected abstract Number evaluatePerson(Person _person);
    
    
    protected void initializationToEvaluateResults() {
    
    
    }
    
}
