
package org.komea.product.web.rest.api;



import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dto.ProviderDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "classpath*:/spring/application-context-test.xml",
        "classpath*:/spring/dispatcher-servlet-test.xml", })
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class })
public class ProvidersControllerIT
{
    
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc               mockMvc;
    
    
    
    @Before
    public void setUp() {
    
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    
    
    @Test
    @ExpectedDatabase(value = "addProvider.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    @Ignore
    public void testRegisterProvider() throws Exception {
    
    
        final Provider provider =
                new Provider(null, ProviderType.JENKINS, "MyProvider", "file://", "http://");
        
        final EventType eventType =
                new EventType(null, 1, "EventUN", "MyEvent", Severity.CRITICAL, true,
                        "a description", "a catogeory", EntityType.PERSON);
        
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
