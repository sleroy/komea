package org.komea.connectors.jira.launch;

import org.komea.connectors.jira.IJiraEvents;
import org.komea.connectors.sdk.main.impl.Connector;

/**
 * Created by jguidoux on 12/12/14.
 */
public class Launcher {
	public static void main(final String[] args) {
		final Connector connector = new Connector("Jira Provider");
		connector.addDefaultCommands(IJiraEvents.EVENT_NEW_BUG,
				IJiraEvents.EVENT_UPDATE_BUG);
		connector.addCommand(new JiraPushEventsCommand());
		connector.run(args);
	}
}