
package org.komea.product.web.rest.api;


import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.KpiStringKeyList;
import org.komea.product.service.dto.ManyHistoricalMeasureRequest;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
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

public class MeasureControllerTest extends AbstractSpringWebIntegrationTestCase {
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc               mockMvc;
    
    @Autowired
    @InjectMocks
    private MeasuresController    measureController;
    
    @Mock
    private IMeasureService       service;
    
    @Before
    public void setUp() {
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockitoAnnotations.initMocks(this);
        
    }
    
    @Test
    public void testfindHistoricalMeasure() throws Exception {
    
        TimeSerieDTO serie = new TimeSerieDTO();
        Mockito.when(service.findHistoricalMeasure(Matchers.any(KpiStringKey.class), Matchers.any(PeriodTimeSerieOptions.class)))
                .thenReturn(serie);
        // .thenReturn(measures);
        
        // HistoryStringKeyList history = new HistoryStringKeyList(ExtendedEntityType.PROJECT);
        // LimitCriteria limit = LimitCriteria.createDefaultLimitCriteria();
        ManyHistoricalMeasureRequest request = new ManyHistoricalMeasureRequest();
        PeriodTimeSerieOptions period = new PeriodTimeSerieOptions();
        period.setFromPeriod(new DateTime(2014, 1, 1, 0, 0));
        period.setToPeriod(new DateTime());
        period.pickBestGranularity();
        request.setPeriod(period);
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/historic")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        
    }
    @Test
    public void testCurrentMeasures() throws Exception {
    
        // List<MeasureResult> measures = Lists.newArrayList();
        // MeasureResult result = new MeasureResult();
        // result.addHistoricalValue(12D, new Date());
        // measures.add(result);
        // Mockito.when(service.getHistoricalMeasures(Matchers.any(HistoryStringKeyList.class), Matchers.any(LimitCriteria.class)))
        // .thenReturn(measures);
        
        // HistoryStringKeyList history = new HistoryStringKeyList(ExtendedEntityType.PROJECT);
        // LimitCriteria limit = LimitCriteria.createDefaultLimitCriteria();
        KpiStringKeyList request = new KpiStringKeyList();
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/current")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        
    }
    
}
