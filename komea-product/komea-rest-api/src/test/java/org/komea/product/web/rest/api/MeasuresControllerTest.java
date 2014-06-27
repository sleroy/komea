
package org.komea.product.web.rest.api;



import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.service.dto.KpiStringKey;
import org.komea.product.service.dto.ManyHistoricalMeasureRequest;
import org.komea.product.service.dto.PeriodCriteria;
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

import com.google.common.collect.Lists;



public class MeasuresControllerTest extends AbstractSpringWebIntegrationTestCase
{
    
    
    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    @InjectMocks
    private MeasuresController    measureController;
    
    private MockMvc               mockMvc;
    
    @Mock
    private IMeasureService       service;
    
    
    
    @Before
    public void setUp() {
    
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockitoAnnotations.initMocks(this);
        
    }
    
    
    @Test
    public void testAverageHistoricalWithEvolution_http_success() throws Exception {
    
    
        final List<TimeSerieDTO> series = Lists.newArrayList();
        final ManyHistoricalMeasureRequest request = getHistoricalRequest();
        Mockito.when(
                service.findMultipleHistoricalMeasure(request.getKpiKeyList(), request.getPeriod()
                        .previous())).thenReturn(series);
        // .thenReturn(measures);
        
        // HistoryStringKeyList history = new HistoryStringKeyList(ExtendedEntityType.PROJECT);
        // LimitCriteria limit = LimitCriteria.createDefaultLimitCriteria();
        // MeasureEvolutionResult evolution = new MeasureEvolutionResult();
        // MeasureResult measureResult = new MeasureResult();
        // BaseEntityDto entity = new BaseEntityDto(EntityType.PROJECT, 1, "KOMEA", "KOMEA", "");
        // measureResult.setEntity(entity);
        // measureResult.setKpi(KpiBuilder.create().build());
        // evolution.setMeasureResult(measureResult);
        // evolution.set
        final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        
        final ResultActions httpRequest =
                mockMvc.perform(MockMvcRequestBuilders.post("/measures/averageHistoricEvolution")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        
    }
    
    
    @Test
    public void testAverageHistoricalWithEvolution_old_value() throws Exception {
    
    
        final List<TimeSerieDTO> series = Lists.newArrayList();
        final ManyHistoricalMeasureRequest request = getHistoricalRequest();
        Mockito.when(
                service.findMultipleHistoricalMeasure(request.getKpiKeyList(), request.getPeriod()
                        .previous())).thenReturn(series);
        // .thenReturn(measures);
        
        // HistoryStringKeyList history = new HistoryStringKeyList(ExtendedEntityType.PROJECT);
        // LimitCriteria limit = LimitCriteria.createDefaultLimitCriteria();
        // MeasureEvolutionResult evolution = new MeasureEvolutionResult();
        // MeasureResult measureResult = new MeasureResult();
        // BaseEntityDto entity = new BaseEntityDto(EntityType.PROJECT, 1, "KOMEA", "KOMEA", "");
        // measureResult.setEntity(entity);
        // measureResult.setKpi(KpiBuilder.create().build());
        // evolution.setMeasureResult(measureResult);
        // evolution.set
        final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        
        final ResultActions httpRequest =
                mockMvc.perform(MockMvcRequestBuilders.post("/measures/averageHistoricEvolution")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        
    }
    
    
    @Test
    public void testfindHistoricalMeasure() throws Exception {
    
    
        final TimeSerieDTO serie = new TimeSerieDTO();
        Mockito.when(
                service.findHistoricalMeasure(Matchers.any(KpiStringKey.class),
                        Matchers.any(PeriodTimeSerieOptions.class))).thenReturn(serie);
        // .thenReturn(measures);
        
        // HistoryStringKeyList history = new HistoryStringKeyList(ExtendedEntityType.PROJECT);
        // LimitCriteria limit = LimitCriteria.createDefaultLimitCriteria();
        final ManyHistoricalMeasureRequest request = getHistoricalRequest();
        final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        
        final ResultActions httpRequest =
                mockMvc.perform(MockMvcRequestBuilders.post("/measures/historic")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        
    }
    
    
    private ManyHistoricalMeasureRequest getHistoricalRequest() {
    
    
        final ManyHistoricalMeasureRequest request = new ManyHistoricalMeasureRequest();
        final PeriodCriteria period = new PeriodCriteria();
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.JANUARY, 1);
        period.setStartDate(calendar.getTime());
        period.setEndDate(new Date());
        request.setPeriod(period);
        return request;
    }


}
