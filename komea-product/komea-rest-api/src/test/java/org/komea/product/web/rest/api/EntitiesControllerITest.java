
package org.komea.product.web.rest.api;


import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(locations = {
        "classpath*:/spring/application-context-test.xml", "classpath*:/spring/dispatcher-servlet-test.xml" })
@TransactionConfiguration(defaultRollback = true)
// @DatabaseTearDown(value = "measures.xml", type = DatabaseOperation.DELETE_ALL)
public class EntitiesControllerITest extends AbstractSpringDBunitIntegrationTest {
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc               mockMvc;
    
    @Before
    public void setUp() {
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    //
    @Test
    // @DatabaseSetup("database.xml")
    public void testAllDepartments() throws Exception {
    
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get("/departments/all"));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(5)));
        // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].name",
        // Matchers.equalToIgnoringCase("Department ABC")));
    }
    
    @Test
    // @DatabaseSetup("database.xml")
    public void testAllPersons() throws Exception {
    
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get("/persons/all"));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(22)));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[1].login", Matchers.equalToIgnoringCase("jguidoux")));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].login", Matchers.equalToIgnoringCase("sleroy")));
    }
    
    @Test
    // @DatabaseSetup("database.xml")
    public void testAllProjects() throws Exception {
    
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get("/projects/all"));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[2].projectKey", Matchers.equalToIgnoringCase("SCERTIFY")));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[1].projectKey", Matchers.equalToIgnoringCase("KOMEA")));
    }
    
    @Test
    // @DatabaseSetup("database.xml")
    public void testAllTeams() throws Exception {
    
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get("/teams/all"));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
        // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].name",
        // Matchers.equalToIgnoringCase("Team 1")));
    }
}
