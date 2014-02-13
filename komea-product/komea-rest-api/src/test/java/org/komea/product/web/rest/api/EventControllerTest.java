
package org.komea.product.web.rest.api;



import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.Lists;



public class EventControllerTest extends AbstractSpringWebIntegrationTestCase
{
    
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc               mockMvc;
    
    @InjectMocks
    @Autowired
    private EventsController      contoller;
    
    @Mock
    private IEventViewerService   eventService;
    
    @Mock
    private IEventPushService     eventPushService;
    
    
    
    // @Test
    // public void findEventsTest() throws Exception {
    //
    // Mockito.when(eventService.findEvents(Matchers.any(SearchEventDto.class))).thenReturn(getEvents());
    //
    // final SearchEventDto search = new SearchEventDto();
    // search.setSeverityMin(Severity.INFO);
    //
    // final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(search);
    //
    // LOGGER.info(jsonMessage);
    //
    // final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/events/find/")
    // .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
    //
    // httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
    // httpRequest.andDo(MockMvcResultHandlers.print());
    // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(1)));
    // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].eventType.name", org.hamcrest.Matchers.equalToIgnoringCase("dtc")));
    //
    // Mockito.verify(eventService, Mockito.times(1)).findEvents(Matchers.any(SearchEventDto.class));
    // }
    //
    // @Test
    // public void getEventsTest() throws Exception {
    //
    // Mockito.when(eventService.getEvents("MINOR", 1)).thenReturn(getEvents());
    //
    // final SearchEventDto search = new SearchEventDto();
    // search.setSeverityMin(Severity.INFO);
    //
    // final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get("/events/get/MINOR/1"));
    //
    // httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
    // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(1)));
    // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].eventType.name", org.hamcrest.Matchers.equalToIgnoringCase("dtc")));
    //
    // Mockito.verify(eventService, Mockito.times(1)).getEvents("MINOR", 1);
    // }
    
    @Test
    public void pushEventTest() throws Exception {
    
    
        // final Provider provider = new Provider();
        // provider.setIcon("/incon.png");
        // provider.setId(1);
        // provider.setProviderType(ProviderType.JENKINS);
        //
        // final EventType eventType = new EventType();
        // eventType.setCategory("large category");
        // eventType.setDescription("a large event");
        // eventType.setEntityType(EntityType.PROJECT);
        // eventType.setEventKey("dtc");
        // eventType.setId(1);
        // eventType.setIdProvider(1);
        // eventType.setName("dtc");
        // eventType.setSeverity(Severity.MINOR);
        
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType("dtc");
        event.setMessage("a message");
        event.setProject("komea");
        event.setProvider("http://komea.tocea.com/jenkins");
        event.setUrl("http://...");
        event.setValue(12);
        
        final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(event);
        
        LOGGER.info(jsonMessage);
        
        final ResultActions httpRequest =
                mockMvc.perform(MockMvcRequestBuilders.post("/events/push")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    
    @Before
    public void setUp() {
    
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockitoAnnotations.initMocks(this);
    }
    
    
    private List<IEvent> getEvents() {
    
    
        final List<IEvent> events = Lists.newArrayList();
        final Event event = new Event();
        event.setDate(new Date());
        
        final EventType eventType = new EventType();
        eventType.setCategory("large category");
        eventType.setDescription("a large event");
        eventType.setEntityType(EntityType.PROJECT);
        eventType.setEventKey("dtc");
        eventType.setId(1);
        eventType.setIdProvider(1);
        eventType.setName("dtc");
        eventType.setSeverity(Severity.MINOR);
        event.setEventType(eventType);
        
        event.setMessage("a message");
        
        final Provider provider = new Provider();
        provider.setIcon("/incon.png");
        provider.setId(1);
        provider.setProviderType(ProviderType.CI_BUILD);
        event.setProvider(provider);
        
        events.add(event);
        return events;
    }
    
}
