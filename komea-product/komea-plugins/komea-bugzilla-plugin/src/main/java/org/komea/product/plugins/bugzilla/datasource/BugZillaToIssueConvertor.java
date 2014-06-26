/**
 *
 */

package org.komea.product.plugins.bugzilla.datasource;



import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugzilla.api.IBugZillaToIssueConvertor;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.j2bugzilla.base.Bug;



/**
 * @author sleroy
 */
@Service
@Transactional
public class BugZillaToIssueConvertor implements IBugZillaToIssueConvertor
{


    private static final Logger LOGGER = LoggerFactory.getLogger(BugZillaToIssueConvertor.class);
    @Autowired
    private IPersonService      personService;



    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.datasource.IBugZillaToIssueConvertor#convert(com.j2bugzilla.base.Bug,
     * org.komea.product.database.model.Project, org.komea.product.plugins.bugzilla.model.BZServerConfiguration)
     */
    @Override
    public IIssue convert(
            final Bug _bug,
            final Project _project,
            final BZServerConfiguration _bzServerConfiguration) {


        LOGGER.trace("Received bug {}", _bug);
        final Person handler =
                createPersonOrNull((String) _bug.getParameterMap().get(
                        _bzServerConfiguration.getHandler_field()));
        final Person creator =
                createPersonOrNull((String) _bug.getParameterMap().get(
                        _bzServerConfiguration.getReporter_field()));
        return new BZIssueWrapper(_bug, handler, creator, _project, _bzServerConfiguration);


    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.datasource.IBugZillaToIssueConvertor#convertAll(java.util.List,
     * org.komea.product.database.model.Project, org.komea.product.plugins.bugzilla.model.BZServerConfiguration)
     */
    @Override
    public Collection<? extends IIssue> convertAll(
            final List<Bug> _bugs,
            final Project _project,
            final BZServerConfiguration _serverConfiguration) {


        Validate.notNull(_project);

        final List<IIssue> issues = Lists.newArrayList();
        for (final Bug bug : _bugs) {
            final IIssue issue = convert(bug, _project, _serverConfiguration);
            if (issue != null) {
                issues.add(issue);
            } else {
                LOGGER.error("Could not convert an issue {}", bug);
            }
        }

        return issues;
    }


    /**
     * @param _object
     * @return
     */
    private Person createPersonOrNull(final String _object) {


        if (_object == null) {
            return null;
        }

        return personService.findOrCreatePersonByEmail(_object);
    }

}
