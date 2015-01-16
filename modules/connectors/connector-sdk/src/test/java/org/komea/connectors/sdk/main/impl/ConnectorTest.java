package org.komea.connectors.sdk.main.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;
import org.kohsuke.args4j.Argument;
import org.komea.connectors.sdk.main.IConnectorCommand;

public class ConnectorTest {

	public static final class ExampleCommand implements IConnectorCommand {
		@Argument(index = 0, usage = "message")
		private String argument;

		/*
		 * (non-Javadoc)
		 *
		 * @see org.komea.connectors.sdk.main.IConnectorCommand#action()
		 */
		@Override
		public String action() {
			return "println";
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see org.komea.connectors.sdk.main.IConnectorCommand#description()
		 */
		@Override
		public String description() {
			return "";
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see org.komea.connectors.sdk.main.IConnectorCommand#init()
		 */
		@Override
		public void init() {
			//

		}

		@Override
		public void run() {
			Assert.assertEquals("expected", argument);

		}

	}

	private static final String TEST_JAR = "Test.jar";

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
		connector.addCommand(new ExampleCommand());

		connector.run(RunArgs.newArgs("println", "action").asMainArgs());
		assertTrue(connector.isHasParsed());
	}

	@Test(expected = ConnectorCommandCliException.class)
	public void testRunWithCommandAndIllegalArguments() {
		final Connector connector = new Connector(TEST_JAR);
		connector.addCommand(new ExampleCommand());

		connector.run(RunArgs.newArgs("println", "action", "wrongArg")
				.asMainArgs());
		assertFalse(connector.isHasParsed());
	}

}
