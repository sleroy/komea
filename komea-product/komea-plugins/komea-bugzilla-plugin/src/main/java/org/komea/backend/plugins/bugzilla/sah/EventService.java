package org.komea.backend.plugins.bugzilla.sah;

import org.komea.backend.plugins.bugzilla.data.BugZillaServer;
import org.komea.backend.plugins.bugzilla.sah.model.BugSeverity;
import org.komea.product.backend.service.esper.IEventPushService;

public abstract class EventService {

    public static void sendAllEvents(final BugsCalculator bugsCalculator, final IEventPushService service,
            final String project, final BugZillaServer server) {
        final EventBuilder eventFactory = new EventBuilder(project, server.getAddress());
        service.sendEventDto(eventFactory.closedBugs(bugsCalculator.countClosedBugs()));
        service.sendEventDto(eventFactory.openBugs(bugsCalculator.countOpenBugs()));
        service.sendEventDto(eventFactory.openNotFixedBugs(bugsCalculator.countOpenNotFixedBugs()));
        for (final BugSeverity severity : BugSeverity.values()) {
            service.sendEventDto(eventFactory.severityOpenBugs(severity, bugsCalculator.countOpenBugsBySeverity(severity)));
//            service.sendEventDto(eventFactory.severityBugs(severity, bugsCalculator.countBugsBySeverity(severity)));
//            service.sendEventDto(eventFactory.severityOpenNotFixedBugs(severity, bugsCalculator.countOpenNotFixedBugsBySeverity(severity)));
        }
//        service.sendEventDto(eventFactory.totalBugs(bugsCalculator.countTotalBugs()));
//        for (final BugStatus status : BugStatus.values()) {
//            service.sendEventDto(eventFactory.statusBugs(status, bugsCalculator.countBugsByStatus(status)));
//        }
//        for (final BugPriority priority : BugPriority.values()) {
//            service.sendEventDto(eventFactory.priorityBugs(priority, bugsCalculator.countBugsByPriority(priority)));
//            service.sendEventDto(eventFactory.priorityOpenBugs(priority, bugsCalculator.countOpenBugsByPriority(priority)));
//            service.sendEventDto(eventFactory.priorityOpenNotFixedBugs(priority, bugsCalculator.countOpenNotFixedBugsByPriority(priority)));
//        }
    }

    private EventService() {
    }

}
