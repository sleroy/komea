
package org.komea.product.web.rest.api;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.komea.product.backend.service.kpi.IMeasureService;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.model.timeserie.dto.TimeSerieDTO;
import org.komea.product.service.dto.KpiStringKeyList;
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

public class MeasuresControllerTest extends AbstractSpringWebIntegrationTestCase {
    
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
        Mockito.when(service.findMultipleHistoricalMeasure(Matchers.any(KpiStringKeyList.class), Matchers.any(PeriodCriteria.class)))
                .thenReturn(Lists.newArrayList(serie));
        // .thenReturn(measures);
        
        // HistoryStringKeyList history = new HistoryStringKeyList(ExtendedEntityType.PROJECT);
        // LimitCriteria limit = LimitCriteria.createDefaultLimitCriteria();
        ManyHistoricalMeasureRequest request = getHistoricalRequest();
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/historic")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        Mockito.verify(service, Mockito.times(1)).findMultipleHistoricalMeasure(Matchers.any(KpiStringKeyList.class),
                Matchers.any(PeriodCriteria.class));
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        
    }
    @Test
    public void testfindHistoricalMeasure_with_error() throws Exception {
    
        Mockito.when(service.findMultipleHistoricalMeasure(Matchers.any(KpiStringKeyList.class), Matchers.any(PeriodCriteria.class)))
                .thenThrow(new KPINotFoundRuntimeException());
        // .thenReturn(measures);
        // HistoryStringKeyList history = new HistoryStringKeyList(ExtendedEntityType.PROJECT);
        // LimitCriteria limit = LimitCriteria.createDefaultLimitCriteria();
        ManyHistoricalMeasureRequest request = getHistoricalRequest();
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/historic")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isInternalServerError());
        
        Mockito.verify(service, Mockito.times(1)).findMultipleHistoricalMeasure(Matchers.any(KpiStringKeyList.class),
                Matchers.any(PeriodCriteria.class));
        
    }
    private ManyHistoricalMeasureRequest getHistoricalRequest() {
    
        ManyHistoricalMeasureRequest request = new ManyHistoricalMeasureRequest();
        PeriodCriteria period = new PeriodCriteria();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.JANUARY, 1);
        period.setStartDate(calendar.getTime());
        period.setEndDate(new Date());
        request.setPeriod(period);
        
        return request;
    }
    
    @Test
    public void testAverageHistoricalWithEvolution_http_success() throws Exception {
    
        List<TimeSerieDTO> series = Lists.newArrayList();
        ManyHistoricalMeasureRequest request = getHistoricalRequest();
        Mockito.when(service.findMultipleHistoricalMeasure(request.getKpiKeyList(), request.getPeriod().previous())).thenReturn(series);
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
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/averageHistoricEvolution")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        
        Mockito.verify(service, Mockito.atLeast(1)).findMultipleHistoricalMeasure(Matchers.any(KpiStringKeyList.class),
                Matchers.any(PeriodCriteria.class));
        
    }
    
    @Test
    public void testAverageHistoricalWithEvolution_old_value() throws Exception {
    
        List<TimeSerieDTO> series = Lists.newArrayList();
        ManyHistoricalMeasureRequest request = getHistoricalRequest();
        Mockito.when(service.findMultipleHistoricalMeasure(request.getKpiKeyList(), request.getPeriod().previous())).thenReturn(series);
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
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(request);
        System.out.println(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/averageHistoricEvolution")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        
        Mockito.verify(service, Mockito.atLeast(1)).findMultipleHistoricalMeasure(Matchers.any(KpiStringKeyList.class),
                Matchers.any(PeriodCriteria.class));
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testValidate() throws Exception {
    
        Validate.isTrue(false);
        
    }
    
    public static void main(final String[] args) {
    
        ManyHistoricalMeasureRequest measureRequest = new ManyHistoricalMeasureRequest();
        KpiStringKeyList kpiKeyList = new KpiStringKeyList();
        kpiKeyList.getEntityKeys().add("sleroy");
        kpiKeyList.getEntityKeys().add("jguidoux");
        kpiKeyList.getKpiKeys().add("NOT_EXIST_KPI");
        kpiKeyList.setEntityType(ExtendedEntityType.PERSON);
        measureRequest.setKpiKeyList(kpiKeyList);
        PeriodCriteria periodCriteria = new PeriodCriteria();
        periodCriteria.setStartDate(Calendar.getInstance().getTime());
        periodCriteria.setEndDate(Calendar.getInstance().getTime());
        measureRequest.setPeriod(periodCriteria);
        try {
            System.out.println(IntegrationTestUtil.convertObjectToJSON(measureRequest));
        } catch (JsonGenerationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
