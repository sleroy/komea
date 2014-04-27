/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import java.io.Serializable;

import org.joda.time.DateTime;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.scm.api.plugin.ScmCommit;



/**
 * @author sleroy
 */
public class ScmKpiTestingData
{
    
    
    /**
     * 
     */
    public static final Person  AUTHOR  = new Person(1, null, null, "firstName", "lastName",
                                                "email", "login", "password", UserBdd.KOMEA);
    /**
     * 
     */
    public static final Project PROJECT = new Project(1, "projectKey", "name", "description", 0,
                                                "icon");
    
    
    
    /**
     * @return
     */
    public static Serializable fakeCommit() {
    
    
        final ScmCommit scmCommit = new ScmCommit();
        scmCommit.setAuthor(AUTHOR);
        scmCommit.setCommitTime(new DateTime());
        scmCommit.setId("1");
        scmCommit.setMessage("Test commit");
        scmCommit.setProject(PROJECT);
        return scmCommit;
    }
    
}
