
package org.komea.connectors.git.events;


import org.junit.Assert;
import org.junit.Test;
import org.komea.connectors.git.AbstractLocalGitTest;
import org.komea.connectors.git.CountingStorage;

public class GitEventTagProducerTests extends AbstractLocalGitTest
{
    
    @Test
    public void testEventsProduction() {
    
        CountingStorage storage = new CountingStorage();
        GitEventTagProducer producer = new GitEventTagProducer(storage,this.repository.getGit());
        this.repository.processAllCommits(producer);
        Assert.assertEquals(22, storage.count);
    }
    
}
