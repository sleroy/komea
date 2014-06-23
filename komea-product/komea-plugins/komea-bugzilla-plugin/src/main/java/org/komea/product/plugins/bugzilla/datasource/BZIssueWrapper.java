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
    
    
    private static final Logger   LOGGER  = LoggerFactory.getLogger(BZIssueWrapper.class);
    
    
    private DataCustomFields      dataCustomFields;
    
    
    private Person                handler;
    
    private List<BugHistory>      history = Lists.newArrayList();
    private String                id;
    
    
    private IssueResolution       issueResolution;
    private IssueStatus           issueStatus;
    private String                priority;
    
    
    private Project               project;
    
    
    private Person                reporter;
    
    
    private BZServerConfiguration serverConfiguration;
    
    
    private Severity              severity;
    
    
    private DateTime              submitTime;
    
    
    private String                summary;
    
    
    
    /**
     *
     */
    public BZIssueWrapper() {
    
    
        super();
    }
    
    
    public BZIssueWrapper(
            final Bug _bug,
            final Person _handler,
            final Person _reporter,
            final Project _project,
            final BZServerConfiguration _serverConfiguration) {
    
    
        Validate.notNull(_bug);
        Validate.notNull(_project);
        id = Integer.toString(_bug.getID());
        priority = _bug.getPriority();
        summary = _bug.getSummary();
        
        handler = _handler;
        reporter = _reporter;
        project = _project;
        submitTime = new DateTime(_bug.getParameterMap().get("creation_time"));
        issueResolution =
                _serverConfiguration.isResolutionFixed(_bug.getResolution())
                        ? IssueResolution.FIXED
                            : IssueResolution.NOT_FIXED;
        severity = _serverConfiguration.getSeverityMap().get(_bug.getSeverity());
        
        if (_serverConfiguration.isStatusOpened(_bug.getStatus())) {
            issueStatus = IssueStatus.OPENED;
        } else {
            issueStatus = IssueStatus.CLOSED;
        }
        dataCustomFields = new DataCustomFields();
        dataCustomFields.put("component", _bug.getComponent());
        dataCustomFields.put("os", _bug.getOperatingSystem());
        dataCustomFields.put("platform", _bug.getPlatform());
        dataCustomFields.put("version", _bug.getVersion());
        
        if (_bug.getParameterMap().containsKey("flags")) {
            for (final Flag flag : _bug.getFlags()) {
                dataCustomFields.put(flag.getName(), flag.getStatus().name());
                
            }
        }
        for (final java.util.Map.Entry<Object, Object> entry : _bug.getParameterMap().entrySet()) {
            dataCustomFields.put(entry.getKey().toString(), entry.getValue().toString());
        }
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getBugTrackerURL()
     */
    @Override
    public String getBugTrackerURL() {
    
    
        return "http://not supported://";
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
    
    
        return dataCustomFields;
    }
    
    
    /**
     * Returns the value of the field dataCustomFields.
     *
     * @return the dataCustomFields
     */
    public DataCustomFields getDataCustomFields() {
    
    
        return dataCustomFields;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getDateSubmitted()
     */
    @Override
    public DateTime getDateSubmitted() {
    
    
        return submitTime;
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
    
    
        return id;
    }
    
    
    /**
     * Returns the id as an int
     *
     * @return the id as an int.
     */
    public int getIdInt() {
    
    
        return Integer.parseInt(id);
    }
    
    
    /**
     * Returns the value of the field issueResolution.
     *
     * @return the issueResolution
     */
    public IssueResolution getIssueResolution() {
    
    
        return issueResolution;
    }
    
    
    /**
     * Returns the value of the field issueStatus.
     *
     * @return the issueStatus
     */
    public IssueStatus getIssueStatus() {
    
    
        return issueStatus;
    }
    
    
    @Override
    public String getPriority() {
    
    
        return priority;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getProduct()
     */
    @Override
    public Project getProduct() {
    
    
        return project;
    }
    
    
    /**
     * Returns the value of the field project.
     *
     * @return the project
     */
    public Project getProject() {
    
    
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
    
    
    /**
     * Returns the value of the field submitTime.
     *
     * @return the submitTime
     */
    public DateTime getSubmitTime() {
    
    
        return submitTime;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getSummary()
     */
    @Override
    public String getSummary() {
    
    
        return summary;
    }
    
    
    /**
     * Sets the field dataCustomFields with the value of _dataCustomFields.
     *
     * @param _dataCustomFields
     *            the dataCustomFields to set
     */
    public void setDataCustomFields(final DataCustomFields _dataCustomFields) {
    
    
        dataCustomFields = _dataCustomFields;
    }
    
    
    /**
     * Sets the field handler with the value of _handler.
     *
     * @param _handler
     *            the handler to set
     */
    public void setHandler(final Person _handler) {
    
    
        handler = _handler;
    }
    
    
    public void setHistory(final List<BugHistory> _history) {
    
    
        history = _history;
    }
    
    
    /**
     * Sets the field id with the value of _id.
     *
     * @param _id
     *            the id to set
     */
    public void setId(final String _id) {
    
    
        id = _id;
    }
    
    
    /**
     * Sets the field issueResolution with the value of _issueResolution.
     *
     * @param _issueResolution
     *            the issueResolution to set
     */
    public void setIssueResolution(final IssueResolution _issueResolution) {
    
    
        issueResolution = _issueResolution;
    }
    
    
    /**
     * Sets the field issueStatus with the value of _issueStatus.
     *
     * @param _issueStatus
     *            the issueStatus to set
     */
    public void setIssueStatus(final IssueStatus _issueStatus) {
    
    
        issueStatus = _issueStatus;
    }
    
    
    /**
     * Sets the field priority with the value of _priority.
     *
     * @param _priority
     *            the priority to set
     */
    public void setPriority(final String _priority) {
    
    
        priority = _priority;
    }
    
    
    /**
     * Sets the field project with the value of _project.
     *
     * @param _project
     *            the project to set
     */
    public void setProject(final Project _project) {
    
    
        project = _project;
    }
    
    
    /**
     * Sets the field reporter with the value of _reporter.
     *
     * @param _reporter
     *            the reporter to set
     */
    public void setReporter(final Person _reporter) {
    
    
        reporter = _reporter;
    }
    
    
    /**
     * Sets the field serverConfiguration with the value of _serverConfiguration.
     *
     * @param _serverConfiguration
     *            the serverConfiguration to set
     */
    public void setServerConfiguration(final BZServerConfiguration _serverConfiguration) {
    
    
        serverConfiguration = _serverConfiguration;
    }
    
    
    /**
     * Sets the field severity with the value of _severity.
     *
     * @param _severity
     *            the severity to set
     */
    public void setSeverity(final Severity _severity) {
    
    
        severity = _severity;
    }
    
    
    /**
     * Sets the field submitTime with the value of _submitTime.
     *
     * @param _submitTime
     *            the submitTime to set
     */
    public void setSubmitTime(final DateTime _submitTime) {
    
    
        submitTime = _submitTime;
    }
    
    
    /**
     * Sets the field summary with the value of _summary.
     *
     * @param _summary
     *            the summary to set
     */
    public void setSummary(final String _summary) {
    
    
        summary = _summary;
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "BZIssueWrapper [\\n\\tdataCustomFields="
                + dataCustomFields + ", \\n\\thandler=" + handler + ", \\n\\thistory=" + history
                + ", \\n\\tid=" + id + ", \\n\\tissueResolution=" + issueResolution
                + ", \\n\\tissueStatus=" + issueStatus + ", \\n\\tpriority=" + priority
                + ", \\n\\tproject=" + project + ", \\n\\treporter=" + reporter
                + ", \\n\\tserverConfiguration=" + serverConfiguration + ", \\n\\tseverity="
                + severity + ", \\n\\tsubmitTime=" + submitTime + ", \\n\\tsummary=" + summary
                + "]";
    }
    
}
