
package org.komea.product.rest.client;



import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.rest.client.api.IEventsAPI;
import org.komea.product.service.dto.errors.InternalServerException;



public class EventsAPIIT extends AbstractRestClientIntegrationTestCase
{
    
    
    private IEventsAPI projectsAPI;
    
    
    
    @Test
    public void findEvents() throws Exception {
    
    
        final SearchEventDto searchEvent = new SearchEventDto();
        searchEvent.getEntityKeys().add("1");
        searchEvent.getEventTypeKeys().add("dtc");
        
        final List<IEvent> events = projectsAPI.findEvents(searchEvent);
        Assert.assertFalse(events.isEmpty());
        
        // FIXME::Assert.assertEquals("romain", events.get(0).getEntityName());
        
    }
    
    
    @Test
    public void getEvents() throws Exception {
    
    
        final List<IEvent> events = projectsAPI.getEvents(Criticity.MINOR, 1);
        Assert.assertFalse(events.isEmpty());
        
        // FIXME:: Assert.assertEquals("romain", events.get(0).getEntityName());
        
    }
    
    
    @Before
    public void setUp() throws Exception {
    
    
        projectsAPI = RestClientFactory.INSTANCE.createEventsAPI("http://localhost:8585/komea");
        Assert.assertNotNull(projectsAPI);
    }
    
    
    //
    
    @Test
    public void testPush() throws ConnectException, URISyntaxException, InternalServerException {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        
        
        event.setEventType("eventType");
        
        event.setMessage("a massage");
        
        
        event.setProvider("jenkins");
        
        projectsAPI.pushEvent(event);
        
        // List<EventDto> events = projectsAPI.getEvents("MINOR", 1);
        // Assert.assertEquals(1, events.size());
        
    }
    
}
