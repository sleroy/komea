
package org.komea.connectors.git.events;


import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.komea.connectors.git.AbstractLocalGitTest;
import org.komea.connectors.git.CountingStorage;

public class GitEventsConnectorTests extends AbstractLocalGitTest
{
    
    @Test
    public void launchTest() {
    
        CountingStorage storage = new CountingStorage();
        GitEventsConnector connector = new GitEventsConnector(storage);
        DateTime since = new DateTime(2000, 01, 01, 0, 0);
        DateTime until = DateTime.now();
        GitConnectorConfiguration conf = new GitConnectorConfiguration("github-gmail", this.folder, since, until);
        
        connector.launch(conf);
        Assert.assertTrue(storage.count > 0);
    }
    
}
