
package org.kormea.product.rest.api.controllers;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("classpath:/spring/applicationContext-test.xml")
@TransactionConfiguration(defaultRollback = true)
public class ProvidersControllerTest
{
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc               mockMvc;
    
    @Before
    public void setUp() {
    
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }
    
    @Test
    public void testGetProject() throws Exception {
    
        Provider provider = new Provider(1, "UN", "MyProvider", "file://", "http://");
        
        EventType eventType = new EventType(2, 1, "EventUN", "MyEvent", 3, true, "a description", "a catogeory", 1);
        
        List<EventType> eventTypes = new ArrayList<EventType>();
        eventTypes.add(eventType);
        ProviderDto dto = new ProviderDto(provider, eventTypes);
        
        String jsonString = IntegrationTestUtil.convertObjectToJSON(dto);
        System.out.println(jsonString);
        
        // String jsonString = "";
        ResultActions httpRequest = this.mockMvc.perform(MockMvcRequestBuilders.post("/providers/register")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
    }
    //
}
