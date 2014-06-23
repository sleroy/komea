/**
 * 
 */

package org.komea.product.plugins.bugtracking.model;



import org.joda.time.DateTime;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.model.IDataCustomFields;



/**
 * @author sleroy
 */
public class Issue implements IIssue
{
    
    
    private String                  bugTrackerURL;
    private String                  category;
    private IDataCustomFields customFields;
    private DateTime                dateSubmitted;
    private Person                  handler;
    private String                  id;
    
    
    private String                  priority;
    private Project                 product;
    private Person                  reporter;
    private IssueResolution         resolution;
    private Severity                severity;
    private IssueStatus             status;
    private String                  summary;
    
    
    
    /**
     * 
     */
    public Issue() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getBugTrackerURL()
     */
    @Override
    public String getBugTrackerURL() {
    
    
        return bugTrackerURL;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getCategory()
     */
    @Override
    public String getCategory() {
    
    
        return category;
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
    
    
        return dateSubmitted;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getHandler()
     */
    @Override
    public Person getHandler() {
    
    
        return handler;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getId()
     */
    @Override
    public String getId() {
    
    
        return id;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getPriority()
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
    
    
        return product;
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
    
    
        return resolution;
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
    
    
        return status;
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
     * Sets the field bugTrackerURL with the value of _bugTrackerURL.
     * 
     * @param _bugTrackerURL
     *            the bugTrackerURL to set
     */
    public void setBugTrackerURL(final String _bugTrackerURL) {
    
    
        bugTrackerURL = _bugTrackerURL;
    }
    
    
    /**
     * Sets the field category with the value of _category.
     * 
     * @param _category
     *            the category to set
     */
    public void setCategory(final String _category) {
    
    
        category = _category;
    }
    
    
    /**
     * Sets the field customFields with the value of _customFields.
     * 
     * @param _customFields
     *            the customFields to set
     */
    public void setCustomFields(final IDataCustomFields _customFields) {
    
    
        customFields = _customFields;
    }
    
    
    /**
     * Sets the field dateSubmitted with the value of _dateSubmitted.
     * 
     * @param _dateSubmitted
     *            the dateSubmitted to set
     */
    public void setDateSubmitted(final DateTime _dateSubmitted) {
    
    
        dateSubmitted = _dateSubmitted;
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
     * Sets the field priority with the value of _priority.
     * 
     * @param _priority
     *            the priority to set
     */
    public void setPriority(final String _priority) {
    
    
        priority = _priority;
    }
    
    
    /**
     * Sets the field product with the value of _product.
     * 
     * @param _product
     *            the product to set
     */
    public void setProduct(final Project _product) {
    
    
        product = _product;
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
     * Sets the field resolution with the value of _resolution.
     * 
     * @param _resolution
     *            the resolution to set
     */
    public void setResolution(final IssueResolution _resolution) {
    
    
        resolution = _resolution;
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
     * Sets the field status with the value of _status.
     * 
     * @param _status
     *            the status to set
     */
    public void setStatus(final IssueStatus _status) {
    
    
        status = _status;
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
    
    
        return "Issue [\\n\\tbugTrackerURL="
                + bugTrackerURL + ", \\n\\tcategory=" + category + ", \\n\\tcustomFields="
                + customFields + ", \\n\\tdateSubmitted=" + dateSubmitted + ", \\n\\thandler="
                + handler + ", \\n\\tid=" + id + ", \\n\\tpriority=" + priority
                + ", \\n\\tproduct=" + product + ", \\n\\treporter=" + reporter
                + ", \\n\\tseverity=" + severity + ", \\n\\tstatus=" + status + ", \\n\\tsummary="
                + summary + ", \\n\\tresolution=" + resolution + "]";
    }
    
}
