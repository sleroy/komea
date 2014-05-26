/**
 *
 */

package org.komea.product.plugins.kpi.standard.management;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectPersonService;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Person;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * This class defines the nubmer of users involved into a project;
 * 
 * @author sleroy
 */
public class NumberOfProjectsPerPerson implements IDynamicDataQuery<KpiResult>
{
    
    
    @Autowired
    private IPersonService        personService;
    
    @Autowired
    private IProjectPersonService projectPersonService;
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.IQuery#getBackupDelay()
     */
    @Override
    public BackupDelay getBackupDelay() {
    
    
        return BackupDelay.DAY;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.IQuery#getResult()
     */
    @Override
    public KpiResult getResult() {
    
    
        final KpiResult kpiResult = new KpiResult();
        for (final Person person : personService.selectAll()) {
            kpiResult.put(person.getEntityKey(),
                    projectPersonService.getProjectIdsOfPerson(person.getId()).size());
        }
        return kpiResult;
    }
    
    
    public void setProjectPersonService(final IProjectPersonService _projectPersonService) {
    
    
        projectPersonService = _projectPersonService;
    }
    
}
