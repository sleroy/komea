
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.dto.EventDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.komea.product.rest.client.api.IEventsAPI;
import org.komea.product.service.dto.errors.InternalServerException;

public class EventsAPIIT extends AbstractRestClientIntegrationTestCase
{
    
    @Before
    public void setUp() throws Exception {
    
    }
    //
    
    @Test
    public void testPush() throws ConnectException, URISyntaxException, InternalServerException {
    
        IEventsAPI projectsAPI = RestClientFactory.INSTANCE.createEventsAPI("http://localhost:8585/komea");
        Assert.assertNotNull(projectsAPI);
        
        EventDto event = new EventDto();
        event.setDate(new Date());
        event.setEntityName("romain");
        
        EventType eventType = new EventType();
        eventType.setCategory("large category");
        eventType.setDescription("a large event");
        eventType.setEntityType(EntityType.PROJECT);
        eventType.setEventKey("dtc");
        eventType.setIdProvider(1);
        eventType.setName("dtc");
        eventType.setSeverity(Severity.MINOR);
        event.setEventType(eventType);
        
        event.setMessage("a massage");
        
        Provider provider = new Provider();
        provider.setIcon("/incon.png");
        provider.setId(1);
        provider.setProviderType(ProviderType.JENKINS);
        event.setProvider(provider);
        
        projectsAPI.pushEvent(event);
        
        // List<EventDto> events = projectsAPI.getEvents("MINOR", 1);
        // Assert.assertEquals(1, events.size());
        
    }
}
