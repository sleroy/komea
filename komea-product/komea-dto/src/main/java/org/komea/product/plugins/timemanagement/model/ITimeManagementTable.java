/**
 * 
 */

package org.komea.product.plugins.timemanagement.model;



import java.util.List;



/**
 * This interface defines a time management table.
 * 
 * @author sleroy
 */
public interface ITimeManagementTable
{
    
    
    /**
     * Returns the sum of the time spent for an activity.
     * 
     * @param _activityName
     * @return
     */
    int getNumberOfTimeLines(ITimeManagementFilter _filter);
    
    
    /**
     * Returns the sum of the time spent for an activity.
     * 
     * @param _activityName
     * @return
     */
    Double getSumTime(ITimeManagementFilter _filter);
    
    
    /**
     * Returns the time lines
     * 
     * @return the time entries
     */
    List<ITimeManagementDataLine> getTimeLine();
}
