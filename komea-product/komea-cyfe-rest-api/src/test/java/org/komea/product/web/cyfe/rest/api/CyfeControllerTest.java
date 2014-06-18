package org.komea.product.web.cyfe.rest.api;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


public class CyfeControllerTest extends AbstractSpringWebIntegrationTestCase {

    @Autowired
    private WebApplicationContext context;
    
    private MockMvc mockMvc;
    
    @Autowired
    @InjectMocks
    private CyfeController CyfeController;
    
    private final String PATH = "/cyfe";
    
    
    @Before
    public void setUp() {
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockitoAnnotations.initMocks(this);
        
    }
    
    @Test
    public void testgetValue() throws Exception {
    	
    	String kpiKey = "COMMIT_MESSAGE_LENGTH";
    	String entityKey = "sleroy";
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/value")
    			.param("kpiKey", kpiKey)
    			.param("entityKey", entityKey));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType("text/csv;charset=utf-8"));
        
    }
    
    @Test
    public void testGetValues() throws Exception {
    	
    	List<String> kpiKeys = Arrays.asList("COMMIT_MESSAGE_LENGTH", "MODIFIED_FILES");
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/values")
    			.param("kpiKeys", kpiKeys.toString()));

        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType("text/csv;charset=utf-8"));
    	
    }
    
    @Test
    public void testgetSerie() throws Exception {
    	
    	String kpiKey = "COMMIT_MESSAGE_LENGTH";
    	List<String> entityKeys = Arrays.asList("sleroy", "jguidoux");
    	TimeScale timescale = TimeScale.PER_DAY;
    	String dateAsString = "10012014";
    	List<String> colors = Arrays.asList("#FFFFFF", "#FF1245");
    	List<String> types = Arrays.asList("area", "line");
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/serie")
    			.param("kpiKey",kpiKey)
    			.param("entityKeys", entityKeys.toString())
    			.param("timescale", timescale.toString())
    			.param("since", dateAsString)
    			.param("colors", colors.toString())
    			.param("types", types.toString()));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType("text/csv;charset=utf-8"));
        
    }
	
}
