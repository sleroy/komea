
package org.komea.connectors.git.events;


import org.junit.Assert;
import org.junit.Test;
import org.komea.connectors.git.AbstractLocalGitTest;
import org.komea.connectors.git.CountingStorage;

public class FileModificationEventProducerTests extends AbstractLocalGitTest
{
    
    @Test
    public void testEventsProduction() {
    
        CountingStorage storage = new CountingStorage();
        FileModificationEventProducer producer = new FileModificationEventProducer(storage, this.repository.getGit());
        this.repository.processAllCommits(producer);
        Assert.assertEquals(266, storage.count);
    }
    
}
