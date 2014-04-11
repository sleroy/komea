
package org.komea.product.plugins.bugzilla.service;



import java.util.List;

import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.bugzilla.api.BugStatusGroup;
import org.komea.product.plugins.bugzilla.api.IBZEventService;
import org.komea.product.plugins.bugzilla.core.BZEventFactory;
import org.komea.product.plugins.bugzilla.core.BugsCalculator;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class BZEventService implements IBZEventService
{
    
    
    @Autowired
    private IEventPushService service;
    
    
    
    private BZEventService() {
    
    
    }
    
    
    public IEventPushService getService() {
    
    
        return service;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.bugzilla.core.IBZEventService#sendAllEvents(org.komea.product.plugins.bugzilla.core.BugsCalculator,
     * org.komea.product.backend.service.esper.IEventPushService, java.lang.String,
     * org.komea.product.plugins.bugzilla.model.BZServerConfiguration)
     */
    @Override
    public void sendAllEvents(
            final BugsCalculator bugsCalculator,
            final String project,
            final BZServerConfiguration server) {
    
    
        final BZServerConfiguration configuration = bugsCalculator.getConfiguration();
        final BZEventFactory eventFactory =
                new BZEventFactory(project, server.getAddress().toString());
        service.sendEventDto(eventFactory.totalBugs(bugsCalculator.countTotalBugs()));
        for (final String severity : configuration.getSeverities()) {
            service.sendEventDto(eventFactory.severityBugs(severity,
                    bugsCalculator.countBugsBySeverity(severity)));
        }
        for (final String status : configuration.getStatutes()) {
            service.sendEventDto(eventFactory.statusBugs(status,
                    bugsCalculator.countBugsByStatus(status)));
        }
        for (final String priority : configuration.getPriorities()) {
            service.sendEventDto(eventFactory.priorityBugs(priority,
                    bugsCalculator.countBugsByPriority(priority)));
        }
        for (final BugStatusGroup group : BugStatusGroup.values()) {
            service.sendEventDto(eventFactory.statusGroupBugs(group,
                    bugsCalculator.countStatusGroupBugs(group)));
            final List<String> statutes = configuration.getStatusGroups().get(group);
            for (final String severity : configuration.getSeverities()) {
                service.sendEventDto(eventFactory.severityStatusGroupBugs(severity, group,
                        bugsCalculator.countBySeverityAndStatutes(severity, statutes)));
            }
            for (final String priority : configuration.getPriorities()) {
                service.sendEventDto(eventFactory.priorityStatusGroupBugs(priority, group,
                        bugsCalculator.countByPriorityAndStatutes(priority, statutes)));
            }
        }
    }
    
    
    public void setService(final IEventPushService _service) {
    
    
        service = _service;
    }
    
}
