
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.enums.Criticity;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.rest.client.api.IEventsAPI;
import org.komea.product.rest.client.api.IProvidersAPI;
import org.komea.product.service.dto.errors.InternalServerException;

public class EventsAPIIT extends AbstractRestClientIntegrationTestCase {
    
    private IEventsAPI projectsAPI;
    
    @Test
    public void findEvents() throws Exception {
    
        final SearchEventDto searchEvent = new SearchEventDto();
        searchEvent.getEntityKeys().add("1");
        searchEvent.getEventTypeKeys().add("dtc");
        
        final List<Event> events = projectsAPI.findEvents(searchEvent);
        Assert.assertFalse(events.isEmpty());
        
        // FIXME::Assert.assertEquals("romain", events.get(0).getEntityName());
        
    }
    
    @Test
    public void getEvents() throws Exception {
    
        final List<Event> events = projectsAPI.getEvents(Criticity.MINOR, 1);
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
    
        IProvidersAPI providersAPI = RestClientFactory.INSTANCE.createProvidersAPI("http://localhost:8585/komea");
        Assert.assertNotNull(providersAPI);
        
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType("BUILD_LAUNCHED");
        event.setMessage("a message");
        event.setProvider("http://komea.tocea.com/jenkins");
        event.setValue(12);
        event.setProject("komea");
        event.setUrl("http://komea.tocea.com/jenkins/event1");
        projectsAPI.pushEvent(event);
        
        // List<EventDto> events = projectsAPI.getEvents("MINOR", 1);
        // Assert.assertEquals(1, events.size());
        
    }
    
}
