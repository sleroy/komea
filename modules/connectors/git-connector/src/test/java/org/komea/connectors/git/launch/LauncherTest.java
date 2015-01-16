/**
 *
 */
package org.komea.connectors.git.launch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.connectors.sdk.main.impl.RunArgs;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LauncherTest {

	/**
	 * Test method for
	 * {@link org.komea.connectors.sdk.std.impl.AbstractPushEventsCommand#run()}
	 * .
	 */
	@Test
	public void testRun() throws Exception {

		Launcher.main(RunArgs.newArgs("push", "-m", "-url", "http://", "-git",
				"src/test/resources/github-gmail", "-gitURL", "http://",
				"-commit", "-tag").asMainArgs());

	}
}
