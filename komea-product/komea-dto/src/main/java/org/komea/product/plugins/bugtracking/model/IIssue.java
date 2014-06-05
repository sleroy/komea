/**
 * 
 */

package org.komea.product.plugins.bugtracking.model;



import org.joda.time.DateTime;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.model.IDynamicData;



/**
 * @author sleroy
 */
public interface IIssue extends IDynamicData
{
    
    
    String getBugTrackerURL();
    
    
    String getCategory();
    
    
    DateTime getDateSubmitted();
    
    
    Person getHandler();
    
    
    String getId();
    
    
    /**
     * @return
     */
    String getPriority();
    
    
    Project getProduct();
    
    
    Person getReporter();
    
    
    String getResolution();
    
    
    String getSeverity();
    
    
    String getStatus();
    
    
    String getSummary();
}
