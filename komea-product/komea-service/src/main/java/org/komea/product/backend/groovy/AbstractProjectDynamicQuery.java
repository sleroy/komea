/**
 * 
 */

package org.komea.product.backend.groovy;



import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Project;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public abstract class AbstractProjectDynamicQuery extends AbstractDynamicQuery
{
    
    
    @Autowired
    private IProjectService projectService;
    
    
    
    public AbstractProjectDynamicQuery(final BackupDelay _delay) {
    
    
        super(EntityType.PROJECT, _delay);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.groovy.AbstractDynamicQuery#getResult()
     */
    @Override
    public KpiResult evaluateResult() {
    
    
        final KpiResult kpiResult = new KpiResult();
        for (final Project project : projectService.selectAll()) {
            kpiResult.put(project, evaluateProject(project));
        }
        
        return kpiResult;
    }
    
    
    protected abstract Number evaluateProject(Project _project);
    
}
