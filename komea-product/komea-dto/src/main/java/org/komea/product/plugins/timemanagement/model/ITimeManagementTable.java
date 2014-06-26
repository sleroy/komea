/**
 * 
 */

package org.komea.product.plugins.timemanagement.model;



import org.komea.product.backend.utils.IFilter;
import org.komea.product.plugins.model.IDynamicDataTable;



/**
 * This interface defines a time management table.
 * 
 * @author sleroy
 */
public interface ITimeManagementTable extends IDynamicDataTable<ITimeManagementDataLine>
{
    
    
    /**
     * Returns the sum of the time spent for an activity.
     * 
     * @param _filter
     *            the timeline filter
     * @return
     */
    int getNumberOfTimeLines(IFilter<ITimeManagementDataLine> _filter);
    
    
    /**
     * Returns the sum of the time spent for an activity.
     * 
     * @param _activityName
     * @return
     */
    Double getSumTime(IFilter<ITimeManagementDataLine> _filter);
    
    
}
