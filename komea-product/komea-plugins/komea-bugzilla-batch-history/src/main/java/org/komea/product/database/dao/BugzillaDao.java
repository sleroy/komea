
package org.komea.product.database.dao;



import java.util.List;

import org.komea.product.database.dto.BZUser;
import org.komea.product.database.dto.BugBugZilla;
import org.komea.product.database.dto.BugHistory;



public interface BugzillaDao
{


    public List<BugHistory> getHistory(int bugID);


    /**
     * Returns the information about an user;
     *
     * @param userID
     *            the user ID
     * @return the user.
     */
    public List<BZUser> getUser(Integer userID);
    
    
    /**
     * Lists bugs for the project name
     *
     * @param _projectName
     *            the project name
     */
    public List<BugBugZilla> listBugs(String productID);
}
