
package org.komea.connectors.git.events;


import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.komea.connectors.git.AbstractLocalGitTest;
import org.komea.connectors.git.BasicEventsListStorage;
import org.komea.event.model.IFlatEvent;
import org.komea.event.model.beans.AbstractEvent;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.storage.IEventStorage;

public class CommitEventProducerTests extends AbstractLocalGitTest
{
    
    @Test
    public void testEventsProduction() {
    
        BasicEventsListStorage storage = new BasicEventsListStorage();
        
        CommitEventProducer producer = new CommitEventProducer(storage);
        
        this.repository.processAllCommits(producer);
        Assert.assertEquals(getExpectedNumberOfCommits(), storage.getEvents().size());
        
        ComplexEvent event = (ComplexEvent) storage.getEvents().get(0);
        String author = (String) event.getProperties().get("author");
        Assert.assertNotNull(author);
    }
    
    @Test
    public void testExceptionInEventsProduction() {
    
        NotWorkingStorage storage = new NotWorkingStorage();
        
        CommitEventProducer producer = new CommitEventProducer(storage);
        
        this.repository.processAllCommits(producer);
        //exception should be logged but won't stop producer execution
        Assert.assertEquals(getExpectedNumberOfCommits(), storage.counter);
    }
    
    private static class NotWorkingStorage implements IEventStorage
    {
        
        int counter;
        
        @Override
        public void close() throws IOException {
        
            // TODO Auto-generated method stub
            
        }
        
        @Override
        public void storeBasicEvent(final BasicEvent _event) {
        
            this.counter++;
            throw new RuntimeException("Storage is throwing exeception for test purpose.");
            
        }
        
        @Override
        public void storeComplexEvent(final ComplexEvent _event) {
        
            this.counter++;
            throw new RuntimeException("Storage is throwing exeception for test purpose.");
        }
        
        @Override
        public void storeEvent(final AbstractEvent _event) {
        
            this.counter++;
            throw new RuntimeException("Storage is throwing exeception for test purpose.");
        }
        
        @Override
        public void storeFlatEvent(final IFlatEvent _event) {
        
            throw new RuntimeException("Storage is throwing exeception for test purpose.");
        }
        
        @Override
        public void storeMap(final Map<String, Serializable> _fieldMap) {
        
            this.counter++;
            throw new RuntimeException("Storage is throwing exeception for test purpose.");
        }
        
        @Override
        public void storePojo(final Object _pojo) {
        
            this.counter++;
            throw new RuntimeException("Storage is throwing exeception for test purpose.");
        }

        @Override
        public void clearEventsOfType(final String _eventType) {
        
            // TODO Auto-generated method stub
            
        }
        
    }
}
