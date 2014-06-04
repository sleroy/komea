/**
 * 
 */

package org.komea.product.plugins.timemanagement.model;



import org.joda.time.DateTime;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;



/**
 * @author sleroy
 */
public interface ITimeManagementDataLine
{
    
    
    /**
     * Returns the type of activity associated to this time entry
     * 
     * @return
     */
    String getActivityName();
    
    
    /**
     * Returns the date associated to this time entry.
     */
    DateTime getDate();
    
    
    /**
     * Returns the member associated to this time entry.
     * 
     * @return
     */
    Person getPerson();
    
    
    /**
     * Returns the project associated to this activity
     * 
     * @return the project associated to this activity.
     */
    Project getProject();
    
    
    /**
     * Returns the time spent
     */
    
    double getTimeSpent();
    
    
    /**
     * Returns if this activity may be considered as research.
     * 
     * @return true if this activity may be considered as research.
     */
    boolean isResearch();
}
