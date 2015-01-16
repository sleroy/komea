/**
 *
 */
package org.komea.connectors.git.launch;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.connectors.sdk.rest.impl.EventoryClientAPI;
import org.komea.event.storage.IEventStorage;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class GitPushEventsCommandTest {

	/**
	 * Test method for
	 * {@link org.komea.connectors.sdk.std.impl.AbstractPushEventsCommand#run()}
	 * .
	 */
	@Test
	public void testRun() throws Exception {

		final GitPushEventsCommand gitPushEventsCommand = new GitPushEventsCommand() {
			@Override
			public org.komea.connectors.sdk.rest.impl.EventoryClientAPI initClientAPI() {
				final EventoryClientAPI mock = mock(EventoryClientAPI.class);
				when(mock.getEventStorage()).thenReturn(
						mock(IEventStorage.class));
				return mock;

			};
		};
		gitPushEventsCommand.setCommitMessage(true);
		gitPushEventsCommand.setRepository("src/test/resources/github-gmail");
		gitPushEventsCommand.setRepositoryURL("http://");
		gitPushEventsCommand.run();

	}
}
