
package org.komea.product.web.rest.api;


import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.dto.KpiKey;
import org.komea.product.database.enums.EntityType;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

public class MeasuresControllerIT extends AbstractSpringWebIntegrationTestCase
{
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc               mockMvc;
    
    @InjectMocks
    @Autowired
    private MeasuresController    contoller;
    
    @Mock
    private IKPIService           measureHistoryService;
    
    @Before
    public void setUp() {
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testLastMeasures() throws Exception {
    
        Mockito.when(measureHistoryService.getKpiDoubleValue(org.mockito.Matchers.any(KpiKey.class))).thenReturn(25D);
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        
        // MyKpiKey kpiKey2 = MyKpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(kpiKey);
        
        LOGGER.info(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/last/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.comparesEqualTo(25D)));
    }
    
    @Test(expected = NestedServletException.class)
    public void testLastMeasuresWithExceptions() throws Exception {
    
        Mockito.when(measureHistoryService.getKpiDoubleValue(org.mockito.Matchers.any(KpiKey.class))).thenThrow(KPINotFoundException.class);
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        
        // MyKpiKey kpiKey2 = MyKpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(kpiKey);
        
        LOGGER.info(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/last/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testLastMeasuresWithNoMessage() throws Exception {
    
        Mockito.when(measureHistoryService.getKpiDoubleValue(null)).thenReturn(0D);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/last/"));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().is(415));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.comparesEqualTo(0D)));
    }
}
