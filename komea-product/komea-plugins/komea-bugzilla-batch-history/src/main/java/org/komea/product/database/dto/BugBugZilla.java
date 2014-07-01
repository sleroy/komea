/**
 *
 */

package org.komea.product.database.dto;



import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.google.common.collect.Maps;



/**
 * @author sleroy
 */
public class BugBugZilla implements IIssue
{


    private Map<String, Object>   attributes = Maps.newHashMap();

    private BugzillaDao           bugzillaDao;

    private BZServerConfiguration bzServerConfiguration;


    private List<BugHistory>      history    = Lists.newArrayList();


    private IPersonService        personService;

    private Project               project;



    public BugBugZilla() {


        super();
    }


    /**
     * Returns the value of the field assigned_to.
     *
     * @return the assigned_to
     */
    public Integer getAssigned_to() {


        return castToInt(attributes.get("assigned_to"));
    }
    
    
    public Map<String, Object> getAttributes() {


        return attributes;
    }


    /**
     * Returns the value of the field bug_id.
     *
     * @return the bug_id
     */
    public Integer getBug_id() {


        return castToInt(attributes.get("bug_id"));
    }


    /**
     * Returns the value of the field bug_severity.
     *
     * @return the bug_severity
     */
    public String getBug_severity() {


        return attributes.get("bug_severity").toString();
    }


    /**
     * Returns the value of the field bug_status.
     *
     * @return the bug_status
     */
    public String getBug_status() {


        return attributes.get("bug_status").toString();
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
     * Returns the value of the field component_id.
     *
     * @return the component_id
     */
    public Integer getComponent_id() {


        return castToInt(attributes.get("component_id"));
    }


    /**
     * Returns the value of the field creation_ts.
     *
     * @return the creation_ts
     */
    public Date getCreation_ts() {


        return (Date) attributes.get("creation_ts");
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicData#getCustomFields()
     */
    @Override
    public IDataCustomFields getCustomFields() {
    
    
        return new DataCustomFields(attributes);
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getDateSubmitted()
     */
    @Override
    public DateTime getDateSubmitted() {


        return new DateTime(attributes.get("creation_ts"));
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getHandler()
     */
    @Override
    public Person getHandler() {


        if (getAssigned_to() == null) {
            return null;
        }
        return personService.findOrCreatePersonByEmail(CollectionUtil.singleOrNull(
                bugzillaDao.getUser(getAssigned_to())).getLogin_name());

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


        return getBug_id().toString();
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


        return (String) attributes.get("priority");
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


        return castToInt(attributes.get("product_id"));
    }


    public Project getProject() {


        return project;
    }


    /**
     * Returns the value of the field remaining_time.
     *
     * @return the remaining_time
     */
    public Double getRemaining_time() {


        return castToDouble(attributes.get("remaining_time"));
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getReporter()
     */
    @Override
    public Person getReporter() {


        final String login_name =
                CollectionUtil.singleOrNull(
                        bugzillaDao.getUser((Integer) attributes.get("reporterId")))
                        .getLogin_name();
        Validate.isTrue(!Strings.isEmpty(login_name));
        return personService.findOrCreatePersonByEmail(login_name);

    }


    /**
     * Returns the value of the field reporter.
     *
     * @return the reporter
     */

    public Integer getReporterId() {


        return castToInt(attributes.get("reporterId"));
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getResolution()
     */
    @Override
    public IssueResolution getResolution() {


        return bzServerConfiguration.isResolutionFixed((String) attributes.get("resolution"))
                ? IssueResolution.FIXED
                        : IssueResolution.NOT_FIXED;
    }


    /**
     * Returns the value of the field resolution.
     *
     * @return the resolution
     */
    public String getResolutionName() {


        return (String) attributes.get("resolution");
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getSeverity()
     */
    @Override
    public Severity getSeverity() {


        return bzServerConfiguration.getSeverityMap().get(attributes.get("bug_severity"));
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getStatus()
     */
    @Override
    public IssueStatus getStatus() {


        return bzServerConfiguration.isStatusOpened((String) attributes.get("bug_status"))
                ? IssueStatus.OPENED
                        : IssueStatus.CLOSED;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getSummary()
     */
    @Override
    public String getSummary() {


        return (String) attributes.get("summary");
    }


    /**
     * Sets the field assigned_to with the value of _assigned_to.
     *
     * @param _assigned_to
     *            the assigned_to to set
     */
    public void setAssigned_to(final Integer _assigned_to) {


        attributes.put("assigned_to", _assigned_to);
    }


    public void setAttributes(final Map<String, Object> _attributes) {


        attributes = _attributes;
    }


    /**
     * Sets the field bug_id with the value of _bug_id.
     *
     * @param _bug_id
     *            the bug_id to set
     */
    public void setBug_id(final Integer _bug_id) {


        attributes.put("bug_id", _bug_id);
    }


    /**
     * Sets the field bug_severity with the value of _bug_severity.
     *
     * @param _bug_severity
     *            the bug_severity to set
     */
    public void setBug_severity(final String _bug_severity) {


        attributes.put("bug_severity", _bug_severity);
    }


    /**
     * Sets the field bug_status with the value of _bug_status.
     *
     * @param _bug_status
     *            the bug_status to set
     */
    public void setBug_status(final String _bug_status) {


        attributes.put("bug_status", _bug_status);
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
     * Sets the field component_id with the value of _component_id.
     *
     * @param _component_id
     *            the component_id to set
     */
    public void setComponent_id(final Integer _component_id) {


        attributes.put("component_id", _component_id);
    }


    /**
     * Sets the field creation_ts with the value of _creation_ts.
     *
     * @param _creation_ts
     *            the creation_ts to set
     */
    public void setCreation_ts(final Date _creation_ts) {


        attributes.put("creation_ts", _creation_ts);
    }


    public void setHistory(final List<BugHistory> _history) {


        history = _history;
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


        attributes.put("priority", _priority);
    }


    /**
     * Sets the field product_id with the value of _product_id.
     *
     * @param _product_id
     *            the product_id to set
     */
    public void setProduct_id(final Integer _product_id) {


        attributes.put("product_id", _product_id);
    }


    public void setProject(final Project _project) {


        project = _project;
    }


    /**
     * Sets the field reporter with the value of _reporter.
     *
     * @param _reporter
     *            the reporter to set
     */
    public void setReporterId(final Integer _reporter) {


        attributes.put("reporterId", _reporter);
    }


    /**
     * Sets the field resolution with the value of _resolution.
     *
     * @param _resolution
     *            the resolution to set
     */
    public void setResolution(final String _resolution) {


        attributes.put("resolution", _resolution);
    }


    private Double castToDouble(final Object obj) {
    
    
        if (obj instanceof Double) {
            return (Double) obj;
        }
        return Double.parseDouble(obj.toString());
    }
    
    
    private Integer castToInt(final Object obj) {


        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        return Integer.parseInt(obj.toString());
    }


}
