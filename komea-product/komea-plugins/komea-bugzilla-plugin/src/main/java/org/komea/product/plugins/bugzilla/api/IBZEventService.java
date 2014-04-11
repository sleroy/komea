/**
 * 
 */

package org.komea.product.plugins.bugzilla.api;



import org.komea.product.plugins.bugzilla.core.BugsCalculator;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;



/**
 * @author sleroy
 */
public interface IBZEventService
{
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.plugins.bugzilla.core.IBugZillaEventService#sendAllEvents(org.komea.product.plugins.bugzilla.core.BugsCalculator,
     * org.komea.product.backend.service.esper.IEventPushService, java.lang.String,
     * org.komea.product.plugins.bugzilla.model.BZServerConfiguration)
     */
    public abstract void sendAllEvents(
            BugsCalculator bugsCalculator,
            String project,
            BZServerConfiguration server);
    
}
