package org.komea.connectors.bugzilla.launch;

import org.komea.connectors.sdk.bugtracker.IBugTrackerAPI;
import org.komea.connectors.sdk.main.impl.Connector;

public class Launcher {

    public static void main(String[] args) {
        final Connector connector = new Connector("Bugzilla Provider");
        connector.addDefaultCommands(IBugTrackerAPI.EVENT_NEW_BUG, IBugTrackerAPI.EVENT_UPDATED_BUG);
        connector.addCommand(new BugzillaPushEventsCommand());
        connector.run(args);
    }
}
