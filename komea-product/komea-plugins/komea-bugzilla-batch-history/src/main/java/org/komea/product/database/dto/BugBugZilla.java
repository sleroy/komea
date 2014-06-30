/**
 *
 */

package org.komea.product.database.dto;



import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.wicket.util.string.Strings;
import org.joda.time.DateTime;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.dao.BugzillaDao;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IssueResolution;
import org.komea.product.plugins.bugtracking.model.IssueStatus;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.plugins.datasource.DataCustomFields;
import org.komea.product.plugins.model.IDataCustomFields;

import com.google.common.collect.Lists;



/**
 * @author sleroy
 */
public class BugBugZilla implements IIssue
{
    
    
    private BugzillaDao            bugzillaDao;
    
    private BZServerConfiguration  bzServerConfiguration;
    
    private final DataCustomFields customFields = new DataCustomFields();
    
    
    private List<BugHistory>       history      = Lists.newArrayList();
    
    
    private IPersonService         personService;
    
    
    private Project                project;


    String                         alias;
    Integer                        assigned_to;
    String                         bug_file_loc;
    Integer                        bug_id;
    String                         bug_severity;
    String                         bug_status;
    
    
    Integer                        cclist_accessible;
    
    
    Integer                        component_id;
    
    
    Date                           creation_ts;
    
    
    Date                           delta_ts;
    
    
    Double                         estimated_time;
    
    
    Integer                        everconfirmed;
    
    
    Date                           lastdiffed;
    
    
    String                         op_sys;
    
    
    String                         priority;
    
    
    Integer                        product_id;
    
    
    Integer                        qa_contact;
    
    
    Double                         remaining_time;
    
    
    String                         rep_platform;
    
    
    Integer                        reporter_accessible;
    
    
    Integer                        reporterId;
    
    
    String                         resolution;
    
    
    String                         short_desc;
    
    
    String                         status_whiteboard;
    
    
    String                         target_milestone;
    
    String                         version;
    
    
    
    public BugBugZilla() {
    
    
        super();
    }
    
    
    /**
     * Returns the value of the field alias.
     *
     * @return the alias
     */
    public String getAlias() {
    
    
        return alias;
    }
    
    
    /**
     * Returns the value of the field assigned_to.
     *
     * @return the assigned_to
     */
    public Integer getAssigned_to() {
    
    
        return assigned_to;
    }
    
    
    /**
     * Returns the value of the field bug_file_loc.
     *
     * @return the bug_file_loc
     */
    public String getBug_file_loc() {
    
    
        return bug_file_loc;
    }
    
    
    /**
     * Returns the value of the field bug_id.
     *
     * @return the bug_id
     */
    public Integer getBug_id() {
    
    
        return bug_id;
    }
    
    
    /**
     * Returns the value of the field bug_severity.
     *
     * @return the bug_severity
     */
    public String getBug_severity() {
    
    
        return bug_severity;
    }
    
    
    /**
     * Returns the value of the field bug_status.
     *
     * @return the bug_status
     */
    public String getBug_status() {
    
    
        return bug_status;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getBugTrackerURL()
     */
    @Override
    public String getBugTrackerURL() {
    
    
        return "";
    }
    
    
    /**
     * Returns the value of the field bugzillaDao.
     *
     * @return the bugzillaDao
     */
    public BugzillaDao getBugzillaDao() {


        return bugzillaDao;
    }
    
    
    public BZServerConfiguration getBzServerConfiguration() {
    
    
        return bzServerConfiguration;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getCategory()
     */
    @Override
    public String getCategory() {
    
    
        return "component_id";
    }
    
    
    /**
     * Returns the value of the field cclist_accessible.
     *
     * @return the cclist_accessible
     */
    public Integer getCclist_accessible() {
    
    
        return cclist_accessible;
    }
    
    
    /**
     * Returns the value of the field component_id.
     *
     * @return the component_id
     */
    public Integer getComponent_id() {
    
    
        return component_id;
    }
    
    
    /**
     * Returns the value of the field creation_ts.
     *
     * @return the creation_ts
     */
    public Date getCreation_ts() {
    
    
        return creation_ts;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicData#getCustomFields()
     */
    @Override
    public IDataCustomFields getCustomFields() {
    
    
        return customFields;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getDateSubmitted()
     */
    @Override
    public DateTime getDateSubmitted() {
    
    
        return new DateTime(creation_ts);
    }
    
    
    /**
     * Returns the value of the field delta_ts.
     *
     * @return the delta_ts
     */
    public Date getDelta_ts() {
    
    
        return delta_ts;
    }
    
    
    /**
     * Returns the value of the field estimated_time.
     *
     * @return the estimated_time
     */
    public Double getEstimated_time() {
    
    
        return estimated_time;
    }
    
    
    /**
     * Returns the value of the field everconfirmed.
     *
     * @return the everconfirmed
     */
    public Integer getEverconfirmed() {
    
    
        return everconfirmed;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getHandler()
     */
    @Override
    public Person getHandler() {
    
    
        if (assigned_to == null) {
            return null;
        }
        return personService.findOrCreatePersonByEmail(CollectionUtil.singleOrNull(
                bugzillaDao.getUser(assigned_to)).getLogin_name());
        
    }
    
    
    public List<BugHistory> getHistory() {


        return history;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getId()
     */
    @Override
    public String getId() {
    
    
        return bug_id.toString();
    }
    
    
    /**
     * Returns the value of the field lastdiffed.
     *
     * @return the lastdiffed
     */
    public Date getLastdiffed() {
    
    
        return lastdiffed;
    }
    
    
    /**
     * Returns the value of the field op_sys.
     *
     * @return the op_sys
     */
    public String getOp_sys() {
    
    
        return op_sys;
    }
    
    
    public IPersonService getPersonService() {
    
    
        return personService;
    }
    
    
    /**
     * Returns the value of the field priority.
     *
     * @return the priority
     */
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
     * Returns the value of the field product_id.
     *
     * @return the product_id
     */
    public Integer getProduct_id() {
    
    
        return product_id;
    }
    
    
    public Project getProject() {
    
    
        return project;
    }


    /**
     * Returns the value of the field qa_contact.
     *
     * @return the qa_contact
     */
    public Integer getQa_contact() {
    
    
        return qa_contact;
    }
    
    
    /**
     * Returns the value of the field remaining_time.
     *
     * @return the remaining_time
     */
    public Double getRemaining_time() {
    
    
        return remaining_time;
    }
    
    
    /**
     * Returns the value of the field rep_platform.
     *
     * @return the rep_platform
     */
    public String getRep_platform() {
    
    
        return rep_platform;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getReporter()
     */
    @Override
    public Person getReporter() {
    
    
        final String login_name =
                CollectionUtil.singleOrNull(bugzillaDao.getUser(reporterId)).getLogin_name();
        Validate.isTrue(!Strings.isEmpty(login_name));
        return personService.findOrCreatePersonByEmail(login_name);
        
    }
    
    
    /**
     * Returns the value of the field reporter_accessible.
     *
     * @return the reporter_accessible
     */
    public Integer getReporter_accessible() {
    
    
        return reporter_accessible;
    }
    
    
    /**
     * Returns the value of the field reporter.
     *
     * @return the reporter
     */
    
    public Integer getReporterId() {
    
    
        return reporterId;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getResolution()
     */
    @Override
    public IssueResolution getResolution() {
    
    
        return bzServerConfiguration.isResolutionFixed(resolution)
                ? IssueResolution.FIXED
                    : IssueResolution.NOT_FIXED;
    }
    
    
    /**
     * Returns the value of the field resolution.
     *
     * @return the resolution
     */
    public String getResolutionName() {
    
    
        return resolution;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getSeverity()
     */
    @Override
    public Severity getSeverity() {
    
    
        return bzServerConfiguration.getSeverityMap().get(bug_severity);
    }
    
    
    /**
     * Returns the value of the field short_desc.
     *
     * @return the short_desc
     */
    public String getShort_desc() {
    
    
        return short_desc;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getStatus()
     */
    @Override
    public IssueStatus getStatus() {
    
    
        return bzServerConfiguration.isStatusOpened(bug_status)
                ? IssueStatus.OPENED
                    : IssueStatus.CLOSED;
    }
    
    
    /**
     * Returns the value of the field status_whiteboard.
     *
     * @return the status_whiteboard
     */
    public String getStatus_whiteboard() {
    
    
        return status_whiteboard;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getSummary()
     */
    @Override
    public String getSummary() {
    
    
        return short_desc;
    }
    
    
    /**
     * Returns the value of the field target_milestone.
     *
     * @return the target_milestone
     */
    public String getTarget_milestone() {
    
    
        return target_milestone;
    }
    
    
    /**
     * Returns the value of the field version.
     *
     * @return the version
     */
    public String getVersion() {
    
    
        return version;
    }
    
    
    /**
     * Sets the field alias with the value of _alias.
     *
     * @param _alias
     *            the alias to set
     */
    public void setAlias(final String _alias) {
    
    
        alias = _alias;
    }
    
    
    /**
     * Sets the field assigned_to with the value of _assigned_to.
     *
     * @param _assigned_to
     *            the assigned_to to set
     */
    public void setAssigned_to(final Integer _assigned_to) {
    
    
        assigned_to = _assigned_to;
    }
    
    
    /**
     * Sets the field bug_file_loc with the value of _bug_file_loc.
     *
     * @param _bug_file_loc
     *            the bug_file_loc to set
     */
    public void setBug_file_loc(final String _bug_file_loc) {
    
    
        bug_file_loc = _bug_file_loc;
    }
    
    
    /**
     * Sets the field bug_id with the value of _bug_id.
     *
     * @param _bug_id
     *            the bug_id to set
     */
    public void setBug_id(final Integer _bug_id) {
    
    
        bug_id = _bug_id;
    }
    
    
    /**
     * Sets the field bug_severity with the value of _bug_severity.
     *
     * @param _bug_severity
     *            the bug_severity to set
     */
    public void setBug_severity(final String _bug_severity) {
    
    
        bug_severity = _bug_severity;
    }
    
    
    /**
     * Sets the field bug_status with the value of _bug_status.
     *
     * @param _bug_status
     *            the bug_status to set
     */
    public void setBug_status(final String _bug_status) {
    
    
        bug_status = _bug_status;
    }
    
    
    /**
     * Sets the field bugzillaDao with the value of _bugzillaDao.
     *
     * @param _bugzillaDao
     *            the bugzillaDao to set
     */
    public void setBugzillaDao(final BugzillaDao _bugzillaDao) {


        bugzillaDao = _bugzillaDao;
    }
    
    
    public void setBzServerConfiguration(final BZServerConfiguration _bzServerConfiguration) {
    
    
        bzServerConfiguration = _bzServerConfiguration;
    }
    
    
    /**
     * Sets the field cclist_accessible with the value of _cclist_accessible.
     *
     * @param _cclist_accessible
     *            the cclist_accessible to set
     */
    public void setCclist_accessible(final Integer _cclist_accessible) {
    
    
        cclist_accessible = _cclist_accessible;
    }
    
    
    /**
     * Sets the field component_id with the value of _component_id.
     *
     * @param _component_id
     *            the component_id to set
     */
    public void setComponent_id(final Integer _component_id) {
    
    
        component_id = _component_id;
    }
    
    
    /**
     * Sets the field creation_ts with the value of _creation_ts.
     *
     * @param _creation_ts
     *            the creation_ts to set
     */
    public void setCreation_ts(final Date _creation_ts) {
    
    
        creation_ts = _creation_ts;
    }
    
    
    /**
     * Sets the field delta_ts with the value of _delta_ts.
     *
     * @param _delta_ts
     *            the delta_ts to set
     */
    public void setDelta_ts(final Date _delta_ts) {
    
    
        delta_ts = _delta_ts;
    }
    
    
    /**
     * Sets the field estimated_time with the value of _estimated_time.
     *
     * @param _estimated_time
     *            the estimated_time to set
     */
    public void setEstimated_time(final Double _estimated_time) {
    
    
        estimated_time = _estimated_time;
    }
    
    
    /**
     * Sets the field everconfirmed with the value of _everconfirmed.
     *
     * @param _everconfirmed
     *            the everconfirmed to set
     */
    public void setEverconfirmed(final Integer _everconfirmed) {
    
    
        everconfirmed = _everconfirmed;
    }
    
    
    public void setHistory(final List<BugHistory> _history) {


        history = _history;
    }
    
    
    /**
     * Sets the field lastdiffed with the value of _lastdiffed.
     *
     * @param _lastdiffed
     *            the lastdiffed to set
     */
    public void setLastdiffed(final Date _lastdiffed) {
    
    
        lastdiffed = _lastdiffed;
    }
    
    
    /**
     * Sets the field op_sys with the value of _op_sys.
     *
     * @param _op_sys
     *            the op_sys to set
     */
    public void setOp_sys(final String _op_sys) {
    
    
        op_sys = _op_sys;
    }
    
    
    public void setPersonService(final IPersonService _personService) {
    
    
        personService = _personService;
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
     * Sets the field product_id with the value of _product_id.
     *
     * @param _product_id
     *            the product_id to set
     */
    public void setProduct_id(final Integer _product_id) {
    
    
        product_id = _product_id;
    }
    
    
    public void setProject(final Project _project) {
    
    
        project = _project;
    }
    
    
    /**
     * Sets the field qa_contact with the value of _qa_contact.
     *
     * @param _qa_contact
     *            the qa_contact to set
     */
    public void setQa_contact(final Integer _qa_contact) {
    
    
        qa_contact = _qa_contact;
    }
    
    
    /**
     * Sets the field remaining_time with the value of _remaining_time.
     *
     * @param _remaining_time
     *            the remaining_time to set
     */
    public void setRemaining_time(final Double _remaining_time) {
    
    
        remaining_time = _remaining_time;
    }
    
    
    /**
     * Sets the field rep_platform with the value of _rep_platform.
     *
     * @param _rep_platform
     *            the rep_platform to set
     */
    public void setRep_platform(final String _rep_platform) {
    
    
        rep_platform = _rep_platform;
    }
    
    
    /**
     * Sets the field reporter_accessible with the value of _reporter_accessible.
     *
     * @param _reporter_accessible
     *            the reporter_accessible to set
     */
    public void setReporter_accessible(final Integer _reporter_accessible) {
    
    
        reporter_accessible = _reporter_accessible;
    }
    
    
    /**
     * Sets the field reporter with the value of _reporter.
     *
     * @param _reporter
     *            the reporter to set
     */
    public void setReporterId(final Integer _reporter) {
    
    
        reporterId = _reporter;
    }
    
    
    /**
     * Sets the field resolution with the value of _resolution.
     *
     * @param _resolution
     *            the resolution to set
     */
    public void setResolutionName(final String _resolution) {
    
    
        resolution = _resolution;
    }
    
    
    /**
     * Sets the field short_desc with the value of _short_desc.
     *
     * @param _short_desc
     *            the short_desc to set
     */
    public void setShort_desc(final String _short_desc) {
    
    
        short_desc = _short_desc;
    }
    
    
    /**
     * Sets the field status_whiteboard with the value of _status_whiteboard.
     *
     * @param _status_whiteboard
     *            the status_whiteboard to set
     */
    public void setStatus_whiteboard(final String _status_whiteboard) {
    
    
        status_whiteboard = _status_whiteboard;
    }
    
    
    /**
     * Sets the field target_milestone with the value of _target_milestone.
     *
     * @param _target_milestone
     *            the target_milestone to set
     */
    public void setTarget_milestone(final String _target_milestone) {
    
    
        target_milestone = _target_milestone;
    }
    
    
    /**
     * Sets the field version with the value of _version.
     *
     * @param _version
     *            the version to set
     */
    public void setVersion(final String _version) {
    
    
        version = _version;
    }
}
