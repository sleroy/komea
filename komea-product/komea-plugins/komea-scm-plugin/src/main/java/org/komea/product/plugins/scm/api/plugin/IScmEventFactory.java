/**
 * 
 */

package org.komea.product.plugins.scm.api.plugin;



import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.Person;



/**
 * @author sleroy
 */
public interface IScmEventFactory
{
    
    
    EventSimpleDto sendBranchAssociated(String _branchName);
    
    
    EventSimpleDto sendBranchNumbers(int _number);
    
    
    EventSimpleDto sendCustomerBranches(int _number);
    
    
    EventSimpleDto sendCustomerTags(int _number);
    
    
    EventSimpleDto sendFetchRepository();
    
    
    EventSimpleDto sendFetchFailed();
    
    
    EventSimpleDto sendNewCommit(String _message, Person _person, String _revision);
    
    
    EventSimpleDto sendNumberTagPerBranch(int _number, String _branchName);
    
}
