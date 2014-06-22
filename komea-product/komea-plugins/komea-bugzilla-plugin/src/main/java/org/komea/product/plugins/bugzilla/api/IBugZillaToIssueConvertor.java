/**
 * 
 */

package org.komea.product.plugins.bugzilla.api;



import java.util.Collection;
import java.util.List;

import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;

import com.j2bugzilla.base.Bug;



/**
 * @author sleroy
 *
 */
public interface IBugZillaToIssueConvertor
{
    
    
    /**
     * Convert a single bug into an issue;
     *
     * @param _bug
     * @return
     */
    public abstract IIssue convert(
            Bug _bug,
            Project _project,
            BZServerConfiguration _bzServerConfiguration);
    
    
    /**
     * Convert all the issues.
     *
     * @param _bugs
     * @param _serverConfiguration
     * @return
     */
    public abstract Collection<? extends IIssue> convertAll(
            List<Bug> _bugs,
            Project _project,
            BZServerConfiguration _serverConfiguration);
    
}
