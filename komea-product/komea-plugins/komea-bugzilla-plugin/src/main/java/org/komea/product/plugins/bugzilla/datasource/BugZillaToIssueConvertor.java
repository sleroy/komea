/**
 * 
 */

package org.komea.product.plugins.bugzilla.datasource;



import java.util.Collection;
import java.util.List;

import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.j2bugzilla.base.Bug;



/**
 * @author sleroy
 */
@Service
@Transactional
public class BugZillaToIssueConvertor
{
    
    
    private IPersonService  personService;
    private IProjectService projectService;
    
    
    
    /**
     * @param _bug
     * @return
     */
    public IIssue convert(final Bug _bug, final Project _project) {
    
    
        return new BZIssueWrapper(_bug, null, null, _project);
        
        
    }
    
    
    /**
     * @param _bugs
     * @return
     */
    public Collection<? extends IIssue> convertAll(final List<Bug> _bugs, final Project _project) {
    
    
        final List<IIssue> issues = Lists.newArrayList();
        for (final Bug bug : _bugs) {
            final IIssue issue = convert(bug, _project);
            if (issue != null) {
                issues.add(issue);
            }
        }
        
        return issues;
    }
    
}
