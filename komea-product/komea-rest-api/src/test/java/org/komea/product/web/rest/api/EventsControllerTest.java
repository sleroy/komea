
package org.komea.product.web.rest.api;



import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



public class EventsControllerTest extends AbstractSpringWebIntegrationTestCase
{
    
    
    private static final Logger   LOGGER = LoggerFactory.getLogger(EventsControllerTest.class);
    
    @Autowired
    private WebApplicationContext context;
    
    @InjectMocks
    @Autowired
    private EventsController      contoller;
    
    @Mock
    private IEventPushService     eventPushService;
    
    @Mock
    private IEventViewerService   eventService;
    
    private MockMvc               mockMvc;
    
    
    
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
    
    
    public void testGet() throws Exception {
    
    
        final ResultActions httpRequest =
                mockMvc.perform(MockMvcRequestBuilders.post("/events/get"));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
    }
    
}
