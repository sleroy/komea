
package org.komea.product.web.rest.api;



import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



public class ProvidersControllerTest extends AbstractSpringWebIntegrationTestCase
{
    
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc               mockMvc;
    
    
    
    @Before
    public void setUp() {
    
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    
    @Test
    public void testGetProject() throws Exception {
    
    
        final Provider provider = new Provider(1, "UN", "MyProvider", "file://", "http://");
        
        final EventType eventType =
                new EventType(2, 1, "EventUN", "MyEvent", 3, true, "a description", "a catogeory",
                        1);
        
        final List<EventType> eventTypes = new ArrayList<EventType>();
        eventTypes.add(eventType);
        final ProviderDto dto = new ProviderDto(provider, eventTypes);
        
        final String jsonString = IntegrationTestUtil.convertObjectToJSON(dto);
        System.out.println(jsonString);
        
        // String jsonString = "";
        final ResultActions httpRequest =
                mockMvc.perform(MockMvcRequestBuilders.post("/providers/register")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonString));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
    }
    //
}
