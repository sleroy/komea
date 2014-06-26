/**
 * 
 */

package org.komea.product.plugins.projectmanagement.model;



import java.net.URL;
import java.util.List;

import org.joda.time.DateTime;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.model.IDynamicData;
import org.komea.product.plugins.model.IDataCustomFields;



/**
 * This class defines a task in project management (may be a story, feature, requirements whatever).
 * 
 * @author sleroy
 */
public interface ITask extends IDynamicData
{
    
    
    /**
     * Returns the list of persons associated to a task
     * 
     * @return
     */
    List<Person> getAssociatedMembers();
    
    
    /**
     * Returns the project associated to the task
     */
    Project getAssociatedProject();
    
    
    /**
     * Returns the business value of this task.
     * 
     Double getBusinessValue();
     * /**
     * 
     * @return Returns the consumed charge
     */
    Double getConsumedCharge();
    
    
    /**
     * Returns the creation date
     * 
     * @return the creation date.
     */
    DateTime getCreationDate();
    
    
    /**
     * Returns additional fields describing the tasks. These fields may depend from implementation.
     * 
     * @return the extra fields.
     */
    @Override
    IDataCustomFields getCustomFields();
    
    
    /**
     * Returns the expected charge of a story.
     * 
     * @return
     */
    Double getExpectedCharge();
    
    
    /**
     * Get the expected date of delivery.
     * 
     * @return the expected date of delivery.
     */
    DateTime getExpectedDate();
    
    
    /**
     * Returns the associated feature.
     * 
     * @return
     */
    String getFeature();
    
    
    /**
     * Returns the innovation value associated to this task.
     * 
     * @return
     */
    Double getInnovation();
    
    
    /**
     * Returns the story name
     * 
     * @return the story name.
     */
    String getName();
    
    
    /**
     * Returns the risk value.
     * 
     * @return
     */
    Double getRisk();
    
    
    /**
     * Returns the task status.
     * 
     * @return
     */
    String getStatus();
    
    
    /**
     * Returns the type of task(story ,task etc).
     * 
     * @return
     */
    String getTaskType();
    
    
    /**
     * Returns the url associated to a task
     * 
     * @return the url associated to ask.
     */
    URL getTaskURL();
}
