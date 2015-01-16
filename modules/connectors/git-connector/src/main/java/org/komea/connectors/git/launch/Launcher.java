package org.komea.connectors.git.launch;

import org.komea.connectors.sdk.main.impl.Connector;

/**
 * Created by jguidoux on 12/12/14.
 */
public class Launcher {
	public static void main(final String[] args) {
		final Connector connector = new Connector("Git Provider");
		connector.addCommand(GitPurgeEventCommand.class);
	}
}
