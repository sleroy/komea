/**
 *
 */

package org.komea.product.plugins.bugzilla.datasource;



import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IssueResolution;
import org.komea.product.plugins.bugtracking.model.IssueStatus;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.plugins.datasource.PluginDataCustomFields;
import org.komea.product.plugins.model.IPluginDataCustomFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.Flag;



/**
 * @author sleroy
 */
public class BZIssueWrapper implements IIssue
{
    
    
    private static final Logger         LOGGER = LoggerFactory.getLogger(BZIssueWrapper.class);
    private final Bug                   bug;
    
    private final Person                handler;
    private final Project               project;
    
    
    private final Person                reporter;
    private final BZServerConfiguration serverConfiguration;
    
    
    
    public BZIssueWrapper(
            final Bug _bug,
            final Person _handler,
            final Person _reporter,
            final Project _project,
            final BZServerConfiguration _serverConfiguration) {
    
    
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
    
    
        return "http://not supported://" + bug.getAlias();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getCategory()
     */
    @Override
    public String getCategory() {
    
    
        return "";
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicData#getCustomFields()
     */
    @Override
    public IPluginDataCustomFields getCustomFields() {
    
    
        final PluginDataCustomFields pluginDataCustomFields = new PluginDataCustomFields();
        pluginDataCustomFields.put("component", bug.getComponent());
        pluginDataCustomFields.put("os", bug.getOperatingSystem());
        pluginDataCustomFields.put("platform", bug.getPlatform());
        pluginDataCustomFields.put("version", bug.getVersion());
        
        
        for (final Flag flag : bug.getFlags()) {
            pluginDataCustomFields.put(flag.getName(), flag.getStatus().name());
            
        }
        for (final java.util.Map.Entry<Object, Object> entry : bug.getParameterMap().entrySet()) {
            pluginDataCustomFields.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return pluginDataCustomFields;
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
    
    
        return serverConfiguration.isResolutionFixed(bug.getStatus())
                ? IssueResolution.FIXED
                    : IssueResolution.NOT_FIXED;
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getSeverity()
     */
    @Override
    public Severity getSeverity() {
    
    
        return Severity.valueOf(bug.getSeverity().toUpperCase());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getStatus()
     */
    @Override
    public IssueStatus getStatus() {
    
    
        if (serverConfiguration.isStatusOpened(bug.getStatus())) {
            return IssueStatus.OPENED;
        }
        if (serverConfiguration.isStatusClosed(bug.getStatus())) {
            return IssueStatus.CLOSED;
        }
        LOGGER.error("Status {} unknown , please update your bugzilla server configuration");
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
