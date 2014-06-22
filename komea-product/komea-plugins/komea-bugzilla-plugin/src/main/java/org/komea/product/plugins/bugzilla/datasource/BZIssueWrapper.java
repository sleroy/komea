/**
 *
 */

package org.komea.product.plugins.bugzilla.datasource;



import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IssueResolution;
import org.komea.product.plugins.bugtracking.model.IssueStatus;
import org.komea.product.plugins.bugzilla.core.BugHistory;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.plugins.datasource.DataCustomFields;
import org.komea.product.plugins.model.IDataCustomFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.Flag;



/**
 * @author sleroy
 */
public class BZIssueWrapper implements IIssue
{


    private static final Logger         LOGGER  = LoggerFactory.getLogger(BZIssueWrapper.class);


    private final Bug                   bug;
    private final Person                handler;

    private List<BugHistory>            history = Lists.newArrayList();
    private final IssueResolution       issueResolution;


    private IssueStatus                 issueStatus;
    private final Project               project;
    private final Person                reporter;


    private final BZServerConfiguration serverConfiguration;
    
    
    private final Severity              severity;



    public BZIssueWrapper(
            final Bug _bug,
            final Person _handler,
            final Person _reporter,
            final Project _project,
            final BZServerConfiguration _serverConfiguration) {


        serverConfiguration = _serverConfiguration;

        Validate.notNull(_bug);
        Validate.notNull(_project);
        bug = _bug;
        handler = _handler;
        reporter = _reporter;
        project = _project;
        issueResolution =
                _serverConfiguration.isResolutionFixed(bug.getResolution())
                ? IssueResolution.FIXED
                        : IssueResolution.NOT_FIXED;
        severity = _serverConfiguration.getSeverityMap().get(bug.getSeverity());

        if (_serverConfiguration.isStatusOpened(bug.getStatus())) {
            issueStatus = IssueStatus.OPENED;
        } else {
            issueStatus = IssueStatus.CLOSED;
        }


    }


    public Bug getBug() {


        return bug;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getBugTrackerURL()
     */
    @Override
    public String getBugTrackerURL() {


        return "http://not supported://" + bug.getAlias();
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getCategory()
     */
    @Override
    public String getCategory() {


        return (String) getCustomFields().getField("component");
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicData#getCustomFields()
     */
    @Override
    public IDataCustomFields getCustomFields() {


        final DataCustomFields dataCustomFields = new DataCustomFields();
        dataCustomFields.put("component", bug.getComponent());
        dataCustomFields.put("os", bug.getOperatingSystem());
        dataCustomFields.put("platform", bug.getPlatform());
        dataCustomFields.put("version", bug.getVersion());

        if (bug.getParameterMap().containsKey("flags")) {
            for (final Flag flag : bug.getFlags()) {
                dataCustomFields.put(flag.getName(), flag.getStatus().name());

            }
        }
        for (final java.util.Map.Entry<Object, Object> entry : bug.getParameterMap().entrySet()) {
            dataCustomFields.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return dataCustomFields;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getDateSubmitted()
     */
    @Override
    public DateTime getDateSubmitted() {


        throw new UnsupportedOperationException("Date is not provided by bugzilla");
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getHandler()
     */
    @Override
    public Person getHandler() {


        return handler;
    }


    public List<BugHistory> getHistory() {
    
    
        return Collections.unmodifiableList(history);
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getId()
     */
    @Override
    public String getId() {


        return Integer.toString(bug.getID());
    }


    @Override
    public String getPriority() {


        return bug.getPriority();
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getProduct()
     */
    @Override
    public Project getProduct() {


        return project;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getReporter()
     */
    @Override
    public Person getReporter() {


        return reporter;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getResolution()
     */
    @Override
    public IssueResolution getResolution() {


        return issueResolution;


    }


    public BZServerConfiguration getServerConfiguration() {
    
    
        return serverConfiguration;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getSeverity()
     */
    @Override
    public Severity getSeverity() {


        return severity;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getStatus()
     */
    @Override
    public IssueStatus getStatus() {


        return issueStatus;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getSummary()
     */
    @Override
    public String getSummary() {


        return bug.getSummary();
    }


    public void setHistory(final List<BugHistory> _history) {


        history = _history;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {


        return "BZIssueWrapper [\\n\\tbug="
                + bug + ", \\n\\thandler=" + handler + ", \\n\\tissueResolution=" + issueResolution
                + ", \\n\\tissueStatus=" + issueStatus + ", \\n\\tproject=" + project
                + ", \\n\\treporter=" + reporter + ", \\n\\tseverity=" + severity + "]";
    }

}
