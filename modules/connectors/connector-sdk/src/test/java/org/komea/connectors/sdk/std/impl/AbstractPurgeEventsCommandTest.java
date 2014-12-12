package org.komea.connectors.sdk.std.impl;

import org.junit.Test;

public class AbstractPurgeEventsCommandTest {

	@Test
	public void testRun() throws Exception {
		final AbstractPurgeEventsCommand abstractPurgeEventsCommand = new AbstractPurgeEventsCommand() {

			@Override
			public String[] getEventTypes() {

				return new String[] { "new_bug" };
			}
		};
		abstractPurgeEventsCommand.setServerURL("http://localhost:8081");
		abstractPurgeEventsCommand.run();

	}

}
