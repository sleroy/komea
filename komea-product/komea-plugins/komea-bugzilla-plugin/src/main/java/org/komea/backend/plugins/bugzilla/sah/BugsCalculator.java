package org.komea.backend.plugins.bugzilla.sah;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.logging.Logger;
import org.komea.backend.plugins.bugzilla.sah.model.Bug;
import org.komea.backend.plugins.bugzilla.sah.model.BugPriority;
import org.komea.backend.plugins.bugzilla.sah.model.BugSeverity;
import org.komea.backend.plugins.bugzilla.sah.model.BugStatus;

public final class BugsCalculator {

    private static final Logger LOGGER = Logger.getLogger(BugsCalculator.class.getName());
    private final List<Bug> bugs;
    private final int total;
    private final int closed;
    private final int open;
    private final int openNotFixed;
    private final BugStatus[] closedSatutes = {BugStatus.CLOSED, BugStatus.DELIVERED, BugStatus.RESOLVED};
    private final BugStatus[] openSatutes = reverse(closedSatutes);
    private final BugStatus[] openNotFixedSatutes = {BugStatus.NEW, BugStatus.UNCONFIRMED, BugStatus.ACCEPTED,
        BugStatus.ASSIGNED, BugStatus.REOPENED, BugStatus.ONHOLD, BugStatus.OPENED};
    private final EnumMap<BugSeverity, Integer> severityMap = new EnumMap<BugSeverity, Integer>(BugSeverity.class);
    private final EnumMap<BugPriority, Integer> priorityMap = new EnumMap<BugPriority, Integer>(BugPriority.class);
    private final EnumMap<BugSeverity, Integer> openBySeverityMap = new EnumMap<BugSeverity, Integer>(BugSeverity.class);
    private final EnumMap<BugPriority, Integer> openByPriorityMap = new EnumMap<BugPriority, Integer>(BugPriority.class);
    private final EnumMap<BugSeverity, Integer> openNotFixedBySeverityMap = new EnumMap<BugSeverity, Integer>(BugSeverity.class);
    private final EnumMap<BugPriority, Integer> openNotFixedByPriorityMap = new EnumMap<BugPriority, Integer>(BugPriority.class);
    private final EnumMap<BugStatus, Integer> statusMap = new EnumMap<BugStatus, Integer>(BugStatus.class);

    public BugsCalculator(List<Bug> bugs) {
        this.bugs = bugs;
        this.total = bugs.size();
        for (final BugSeverity bugSeverity : BugSeverity.values()) {
            severityMap.put(bugSeverity, 0);
            openBySeverityMap.put(bugSeverity, countBySeverityAndStatutes(bugSeverity, openSatutes));
            openNotFixedBySeverityMap.put(bugSeverity, countBySeverityAndStatutes(bugSeverity, openNotFixedSatutes));
        }
        for (final BugStatus bugStatus : BugStatus.values()) {
            statusMap.put(bugStatus, 0);
        }
        for (final BugPriority bugPriority : BugPriority.values()) {
            priorityMap.put(bugPriority, 0);
            openByPriorityMap.put(bugPriority, countByPriorityAndStatutes(bugPriority, openSatutes));
            openNotFixedByPriorityMap.put(bugPriority, countByPriorityAndStatutes(bugPriority, openNotFixedSatutes));
        }
        for (final Bug bug : bugs) {
            final BugPriority bugPriority = bug.getPriority();
            final BugSeverity bugSeverity = bug.getSeverity();
            final BugStatus bugStatus = bug.getStatus();
            if (bugSeverity != null) {
                severityMap.put(bugSeverity, severityMap.get(bugSeverity) + 1);
            }
            if (bugPriority != null) {
                priorityMap.put(bugPriority, priorityMap.get(bugPriority) + 1);
            }
            if (bugStatus != null) {
                statusMap.put(bugStatus, statusMap.get(bugStatus) + 1);
            }
        }
        closed = countByStatutes(closedSatutes);
        open = total - closed;
        openNotFixed = countByStatutes(openNotFixedSatutes);
    }

    private int countByPriorityAndStatutes(final BugPriority bugPriority, final BugStatus... bugStatutes) {
        int count = 0;
        final List<BugStatus> statutes = Arrays.asList(bugStatutes);
        for (final Bug bug : bugs) {
            if (bugPriority.equals(bug.getPriority()) && statutes.contains(bug.getStatus())) {
                count++;
            }
        }
        return count;
    }

    private int countBySeverityAndStatutes(final BugSeverity bugSeverity, final BugStatus... bugStatutes) {
        int count = 0;
        final List<BugStatus> statutes = Arrays.asList(bugStatutes);
        for (final Bug bug : bugs) {
            if (bugSeverity.equals(bug.getSeverity()) && statutes.contains(bug.getStatus())) {
                count++;
            }
        }
        return count;
    }

    private int countByStatutes(final BugStatus... bugStatutes) {
        int count = 0;
        for (final BugStatus bugStatus : bugStatutes) {
            count += statusMap.get(bugStatus);
        }
        return count;
    }

    private BugStatus[] reverse(final BugStatus... bugStatutes) {
        final List<BugStatus> result = new ArrayList<BugStatus>();
        final List<BugStatus> bugStatutesList = Arrays.asList(bugStatutes);
        for (final BugStatus bugStatus : BugStatus.values()) {
            if (!bugStatutesList.contains(bugStatus)) {
                result.add(bugStatus);
            }
        }
        return result.toArray(new BugStatus[result.size()]);
    }

    public int countClosedBugs() {
        return closed;
    }

    public int countOpenBugs() {
        return open;
    }

    public int countOpenNotFixedBugs() {
        return openNotFixed;
    }

    public int countTotalBugs() {
        return total;
    }

    public int countBugsByStatus(final BugStatus status) {
        return statusMap.get(status);
    }

    public int countBugsBySeverity(final BugSeverity severity) {
        return severityMap.get(severity);
    }

    public int countBugsByPriority(final BugPriority priority) {
        return priorityMap.get(priority);
    }

    public int countOpenBugsBySeverity(final BugSeverity severity) {
        return openBySeverityMap.get(severity);
    }

    public int countOpenBugsByPriority(final BugPriority priority) {
        return openByPriorityMap.get(priority);
    }

    public int countOpenNotFixedBugsBySeverity(final BugSeverity severity) {
        return openNotFixedBySeverityMap.get(severity);
    }

    public int countOpenNotFixedBugsByPriority(final BugPriority priority) {
        return openNotFixedByPriorityMap.get(priority);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Total: ").append(total).append("\n");
        stringBuilder.append("Open: ").append(open).append("\n");
        stringBuilder.append("Open NOT fixed: ").append(openNotFixed).append("\n");
        stringBuilder.append("Closed: ").append(closed).append("\n");
        stringBuilder.append("\n");
        for (final BugSeverity severity : BugSeverity.values()) {
            stringBuilder.append("Open ").append(severity.name()).append(": ")
                    .append(openBySeverityMap.get(severity)).append("\n");
        }
        stringBuilder.append("\n");
        for (final BugPriority priority : BugPriority.values()) {
            stringBuilder.append("Open ").append(priority.name()).append(": ")
                    .append(openByPriorityMap.get(priority)).append("\n");
        }
        stringBuilder.append("\n");
        for (final BugSeverity severity : BugSeverity.values()) {
            stringBuilder.append("Open NOT fixed ").append(severity.name()).append(": ")
                    .append(openNotFixedBySeverityMap.get(severity)).append("\n");
        }
        stringBuilder.append("\n");
        for (final BugPriority priority : BugPriority.values()) {
            stringBuilder.append("Open NOT fiexd").append(priority.name()).append(": ")
                    .append(openNotFixedByPriorityMap.get(priority)).append("\n");
        }
        return stringBuilder.toString();
    }

}
