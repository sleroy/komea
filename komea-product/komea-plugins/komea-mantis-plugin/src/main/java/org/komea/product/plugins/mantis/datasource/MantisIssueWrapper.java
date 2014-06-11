/**
 *
 */

package org.komea.product.plugins.mantis.datasource;



import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IssueResolution;
import org.komea.product.plugins.bugtracking.model.IssueStatus;
import org.komea.product.plugins.datasource.PluginDataCustomFields;
import org.komea.product.plugins.mantis.model.MantisServerConfiguration;
import org.komea.product.plugins.model.IPluginDataCustomFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biz.futureware.mantis.rpc.soap.client.IssueData;



/**
 * @author sleroy
 */
public class MantisIssueWrapper implements IIssue
{
    
    
    private static final Logger             LOGGER = LoggerFactory
                                                           .getLogger(MantisIssueWrapper.class);
    private final IssueData                 bug;
    
    private final Person                    handler;
    private final Project                   project;
    
    
    private final Person                    reporter;
    private final MantisServerConfiguration serverConfiguration;
    
    
    
    public MantisIssueWrapper(
            final IssueData _bug,
            final Person _handler,
            final Person _reporter,
            final Project _project,
            final MantisServerConfiguration _serverConfiguration) {
    
    
        bug = _bug;
        handler = _handler;
        reporter = _reporter;
        project = _project;
        serverConfiguration = _serverConfiguration;
        Validate.notNull(_bug);
        ;
        Validate.notNull(project);
        Validate.notNull(serverConfiguration);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getBugTrackerURL()
     */
    @Override
    public String getBugTrackerURL() {
    
    
        return "http://not supported://" + bug.getId();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getCategory()
     */
    @Override
    public String getCategory() {
    
    
        return bug.getCategory();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicData#getCustomFields()
     */
    @Override
    public IPluginDataCustomFields getCustomFields() {
    
    
        final PluginDataCustomFields pluginDataCustomFields = new PluginDataCustomFields();
        pluginDataCustomFields.put("info", bug.getAdditional_information());
        pluginDataCustomFields.put("os", bug.getOs());
        pluginDataCustomFields.put("platform", bug.getPlatform());
        pluginDataCustomFields.put("version", bug.getVersion());
        pluginDataCustomFields.put("build", bug.getBuild());
        pluginDataCustomFields.put("fixed_version", bug.getFixed_in_version());
        pluginDataCustomFields.put("os_build", bug.getOs_build());
        pluginDataCustomFields.put("steps", bug.getSteps_to_reproduce());
        pluginDataCustomFields.put("target_version", bug.getTarget_version());
        pluginDataCustomFields.put("submitted_date", bug.getDate_submitted());
        pluginDataCustomFields.put("eta", bug.getEta().getName());
        pluginDataCustomFields.put("last_update", bug.getLast_updated());
        pluginDataCustomFields.put("projection", bug.getProjection());
        pluginDataCustomFields.put("reproducibility", bug.getReproducibility());
        pluginDataCustomFields.put("sponsorship", bug.getSponsorship_total());
        pluginDataCustomFields.put("sticky", bug.getSticky());
        pluginDataCustomFields.put("view_state", bug.getView_state());
        return pluginDataCustomFields;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getDateSubmitted()
     */
    @Override
    public DateTime getDateSubmitted() {


        return new DateTime(bug.getDate_submitted());
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
    
    
        return bug.getId().toString();
    }
    
    
    @Override
    public String getPriority() {
    
    
        return bug.getPriority().getName();
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
    
    
        return serverConfiguration.isResolutionFixed(bug.getStatus().getName())
                ? IssueResolution.FIXED
                    : IssueResolution.NOT_FIXED;
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getSeverity()
     */
    @Override
    public Severity getSeverity() {
    
    
        return serverConfiguration.getSeverityMap().get(bug.getSeverity().getName());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getStatus()
     */
    @Override
    public IssueStatus getStatus() {
    
    
        final String statusId = bug.getStatus().getId().toString();
        if (serverConfiguration.isStatusOpened(statusId)) {
            return IssueStatus.OPENED;
        }
        if (serverConfiguration.isStatusClosed(statusId)) {
            return IssueStatus.CLOSED;
        }
        LOGGER.error("Status {} unknown , please update your mantis server configuration");
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getSummary()
     */
    @Override
    public String getSummary() {
    
    
        return bug.getSummary();
    }
    
}
