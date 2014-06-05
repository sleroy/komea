/**
 * 
 */

package org.komea.product.plugins.bugzilla.datasource;



import org.apache.commons.lang.Validate;
import org.joda.time.DateTime;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.datasource.PluginDataCustomFields;
import org.komea.product.plugins.model.IPluginDataCustomFields;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.Flag;



/**
 * @author sleroy
 */
public class BZIssueWrapper implements IIssue
{
    
    
    private final Bug     bug;
    private final Person  handler;
    
    private final Project project;
    
    private final Person  reporter;
    
    
    
    public BZIssueWrapper(
            final Bug _bug,
            final Person _handler,
            final Person _reporter,
            final Project _project) {
    
    
        bug = _bug;
        handler = _handler;
        reporter = _reporter;
        project = _project;
        Validate.notNull(_bug);
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
    public String getResolution() {
    
    
        return bug.getResolution();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getSeverity()
     */
    @Override
    public String getSeverity() {
    
    
        return bug.getSeverity();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugtracking.model.IIssue#getStatus()
     */
    @Override
    public String getStatus() {
    
    
        return bug.getStatus();
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
