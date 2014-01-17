
package org.komea.product.web.rest.api;



import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@RunWith(SpringJUnit4ClassRunner.class)
public class DepartmentControllerTest extends AbstractSpringWebIntegrationTestCase
{
    
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc               mockMvc;
    
    
    
    @Before
    public void setUp() {
    
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    
    //
    @Test
    public void testAllTeams() throws Exception {
    
    
        final ResultActions httpRequest =
                mockMvc.perform(MockMvcRequestBuilders.get("/departments/all"));
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }
}
