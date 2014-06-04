/**
 * 
 */

package org.komea.product.plugins.projectmanagement.model;



import java.util.List;



/**
 * @author sleroy
 */
public interface ITaskManagement
{
    
    
    /**
     * Returns the list of task.
     * 
     * @return the list of tasks.
     */
    List<ITask> getTasks();
    
    
    /**
     * Returns the list of task.
     * 
     * @return the list of tasks.
     */
    List<ITask> searchTasks(ITaskFilter _taskFilter);
    
}
