/**
 * 
 */

package org.komea.product.backend.service;



import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.komea.product.backend.service.sprint.ISprintPlugin;
import org.komea.product.backend.service.sprint.ISprintService;
import org.komea.product.service.dto.Sprint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * This class defines the service to handle sprints. It relies on a sprint plugin that is optional. When it is not defined, empty values are
 * returned.
 * 
 * @author sleroy
 */
@Service
public class SprintService implements ISprintService
{
    
    
    @Autowired(required = false)
    private ISprintPlugin sprintPlugin;
    
    
    
    /**
     * Sprint service.
     */
    public SprintService() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.sprint.ISprintService#getSprintOnPeriod(java.lang.String, org.joda.time.DateTime,
     * org.joda.time.DateTime)
     */
    @Override
    public Sprint getSprintOnPeriod(
            final String _projectName,
            final DateTime _begin,
            final DateTime _endDate) {
    
    
        Validate.notNull(_projectName);
        Validate.notNull(_begin);
        Validate.notNull(_endDate);
        Validate.isTrue(!_begin.isAfter(_endDate));
        if (sprintPlugin == null) {
            return null;
        }
        return sprintPlugin.getSprintOnPeriod(_projectName, _begin, _endDate);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.sprint.ISprintService#getSprintsAssociatedToAProject(java.lang.String)
     */
    @Override
    public List<Sprint> getSprintsAssociatedToAProject(final String _projectName) {
    
    
        Validate.notNull(_projectName);
        if (sprintPlugin == null) {
            return Collections.emptyList();
        }
        return sprintPlugin.getSprintsAssociatedToAProject(_projectName);
    }
    
}
