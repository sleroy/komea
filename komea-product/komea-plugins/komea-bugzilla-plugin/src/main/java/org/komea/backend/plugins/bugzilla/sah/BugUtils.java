package org.komea.backend.plugins.bugzilla.sah;

import java.util.ArrayList;
import java.util.List;
import org.komea.backend.plugins.bugzilla.data.BugzillaBug;
import org.komea.backend.plugins.bugzilla.sah.model.Bug;
import org.komea.backend.plugins.bugzilla.sah.model.BugPriority;
import org.komea.backend.plugins.bugzilla.sah.model.BugSeverity;
import org.komea.backend.plugins.bugzilla.sah.model.BugStatus;

public abstract class BugUtils {

    public static List<Bug> convert(final List<BugzillaBug> bugzillaBugs) {
        final List<Bug> bugs = new ArrayList<Bug>(bugzillaBugs.size());
        for (final BugzillaBug bugzillaBug : bugzillaBugs) {
            final Bug bug = convert(bugzillaBug);
            bugs.add(bug);
        }
        return bugs;
    }

    public static Bug convert(final BugzillaBug bugzillaBug) {
        final BugSeverity severity = null;
        final BugPriority priority = null;
        final BugStatus status = null;
        return new Bug(severity, priority, status);
    }

    private BugUtils() {
    }

}
