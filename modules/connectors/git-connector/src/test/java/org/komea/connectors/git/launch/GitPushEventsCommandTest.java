/**
 *
 */
package org.komea.connectors.git.launch;

import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.events.api.IEventsClient;
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
            public IEventsClient initClient() {
                final IEventsClient mock = mock(IEventsClient.class);
                return mock;
            }
        ;
        };
		gitPushEventsCommand.setCommitMessage(true);
        gitPushEventsCommand
                .setRepository("src/test/resources/github-gmail/git");
        gitPushEventsCommand.setRepositoryURL("http://");
        gitPushEventsCommand.run();

    }
}
