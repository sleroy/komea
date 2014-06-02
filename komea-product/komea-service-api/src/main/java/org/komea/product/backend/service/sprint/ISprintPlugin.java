/**
 * 
 */

package org.komea.product.backend.service.sprint;



import java.util.List;

import org.joda.time.DateTime;
import org.komea.product.service.dto.Sprint;



/**
 * @author sleroy
 */
public interface ISprintPlugin
{
    
    
    /**
     * Returns the sprint associated to the given period or null.
     * 
     * @param _begin
     *            the begin date
     * @param _endDate
     *            the end date
     * @return the sprint or null.
     */
    Sprint getSprintOnPeriod(String _projectName, DateTime _begin, DateTime _endDate);
    
    
    /**
     * Returns the list of sprint associated to a project or an empty list.
     * 
     * @param _projectName
     *            the project name
     * @return the list of sprints.
     */
    List<Sprint> getSprintsAssociatedToAProject(String _projectName);
}
