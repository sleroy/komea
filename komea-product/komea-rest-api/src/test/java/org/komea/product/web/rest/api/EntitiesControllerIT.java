
package org.komea.product.web.rest.api;


import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@TransactionConfiguration(defaultRollback = true)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
public class EntitiesControllerIT extends AbstractSpringWebIntegrationTestCase {
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc               mockMvc;
    
    @Before
    public void setUp() {
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    //
    @Test
    @DatabaseSetup("database.xml")
    public void testAllDepartments() throws Exception {
    
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get("/departments/all"));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.equalToIgnoringCase("devs")));
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testAllPersons() throws Exception {
    
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get("/persons/all"));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].login", Matchers.equalToIgnoringCase("jguidoux")));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[1].login", Matchers.equalToIgnoringCase("sleroy")));
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testAllProjects() throws Exception {
    
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get("/projects/all"));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].projectKey", Matchers.equalToIgnoringCase("komea")));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[1].projectKey", Matchers.equalToIgnoringCase("techdebt")));
    }
}
