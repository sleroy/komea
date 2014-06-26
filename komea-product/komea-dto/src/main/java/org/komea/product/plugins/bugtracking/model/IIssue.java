/**
 *
 */

package org.komea.product.plugins.bugtracking.model;



import org.joda.time.DateTime;
import org.komea.product.database.enums.Severity;
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
    
    
    String getPriority();
    
    
    Project getProduct();
    
    
    Person getReporter();
    
    
    IssueResolution getResolution();
    
    
    Severity getSeverity();
    
    
    IssueStatus getStatus();
    
    
    String getSummary();
}
