package org.komea.product.plugins.bugzilla.sah;

import java.util.List;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.bugzilla.data.BugZillaServer;

public abstract class EventService {

    public static void sendAllEvents(final BugsCalculator bugsCalculator, final IEventPushService service,
            final String project, final BugZillaServer server) {
        final BugzillaServerConfiguration configuration = bugsCalculator.getConfiguration();
        final EventBuilder eventFactory = new EventBuilder(project, server.getAddress());
        service.sendEventDto(eventFactory.totalBugs(bugsCalculator.countTotalBugs()));
        for (final String severity : configuration.getSeverities()) {
            service.sendEventDto(eventFactory.severityBugs(severity, bugsCalculator.countBugsBySeverity(severity)));
        }
        for (final String status : configuration.getStatutes()) {
            service.sendEventDto(eventFactory.statusBugs(status, bugsCalculator.countBugsByStatus(status)));
        }
        for (final String priority : configuration.getPriorities()) {
            service.sendEventDto(eventFactory.priorityBugs(priority, bugsCalculator.countBugsByPriority(priority)));
        }
        for (final BugStatusGroup group : BugStatusGroup.values()) {
            service.sendEventDto(eventFactory.statusGroupBugs(group, bugsCalculator.countStatusGroupBugs(group)));
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

    private EventService() {
    }

}
