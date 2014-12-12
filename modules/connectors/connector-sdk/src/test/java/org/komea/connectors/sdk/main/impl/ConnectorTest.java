package org.komea.connectors.sdk.main.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;
import org.kohsuke.args4j.Argument;
import org.komea.connectors.sdk.main.ConnectorCommand;

public class ConnectorTest {

	@ConnectorCommand("println")
	public static final class ExampleCommand {
		@Argument(index = 0, usage = "message")
		private String	argument;

		public void run() {
			Assert.assertEquals("expected", this.argument);

		}

	}

	private static final String	TEST_JAR	= "Test.jar";

	@Test
	public void testRun() throws Exception {
		final Connector connector = new Connector(TEST_JAR);
		connector.run();
		assertFalse(connector.isHasParsed());

	}

	@Test(expected = UnknownConnectorCommandException.class)
	public void testRun_fail() {
		final Connector connector = new Connector(TEST_JAR);
		connector.run(RunArgs.newArgs("action").asMainArgs());
		// Fail missing command
	}

	@Test
	public void testRunWithCommand() {
		final Connector connector = new Connector(TEST_JAR);
		connector.addCommand(ExampleCommand.class);

		connector.run(RunArgs.newArgs("println", "action").asMainArgs());
		assertTrue(connector.isHasParsed());
	}

	@Test(expected = ConnectorCommandCliException.class)
	public void testRunWithCommandAndIllegalArguments() {
		final Connector connector = new Connector(TEST_JAR);
		connector.addCommand(ExampleCommand.class);

		connector.run(RunArgs.newArgs("println", "action", "wrongArg").asMainArgs());
		assertFalse(connector.isHasParsed());
	}

	@Test(expected = InvalidConnectorCommandException.class)
	public void testRunWithInvalidCommand() {
		final Connector connector = new Connector(TEST_JAR);
		connector.addCommand(String.class);
		fail("Should not be there");
	}
}
