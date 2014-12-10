
package org.komea.connectors.git.events;


import org.junit.Assert;
import org.junit.Test;
import org.komea.connectors.git.AbstractLocalGitTest;
import org.komea.connectors.git.BasicEventsListStorage;
import org.komea.event.model.api.IComplexEvent;

public class CommitEventProducerTests extends AbstractLocalGitTest
{
    
    @Test
    public void testEventsProduction() {
    
        BasicEventsListStorage storage = new BasicEventsListStorage();
        
        CommitEventProducer producer = new CommitEventProducer(storage);
        
        this.repository.processAllCommits(producer);
        Assert.assertEquals(getExpectedNumberOfCommits(), storage.getEvents().size());
        
        IComplexEvent event = (IComplexEvent) storage.getEvents().get(0);
        String author = (String) event.getProperties().get("author");
        Assert.assertNotNull(author);
    }
}
