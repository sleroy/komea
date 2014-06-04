/**
 * 
 */

package org.komea.product.plugins.projectmanagement.model;



/**
 * Defines a filter to matches tasks.
 * 
 * @author sleroy
 */
public interface ITaskFilter
{
    
    
    /**
     * Defines the matcher.
     * 
     * @param _task
     */
    boolean matches(ITask _task);
}
