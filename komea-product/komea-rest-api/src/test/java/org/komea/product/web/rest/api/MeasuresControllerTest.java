
package org.komea.product.web.rest.api;


import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.esper.reactor.KPINotFoundException;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.dto.SearchHistoricalMeasuresDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Measure;
import org.komea.product.database.model.MeasureCriteria;
import org.komea.product.service.dto.KpiKey;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.Lists;

public class MeasuresControllerTest extends AbstractSpringWebIntegrationTestCase {
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc               mockMvc;
    
    @InjectMocks
    @Autowired
    private MeasuresController    controller;
    
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
    
    @Test
    public void testLastListMeasures() throws Exception {
    
        Measure measure = new Measure();
        measure.setDate(new Date());
        measure.setId(1);
        measure.setIdKpi(1);
        measure.setIdPerson(1);
        measure.setValue(12D);
        
        Mockito.when(measureHistoryService.getKpiMeasureValue(org.mockito.Matchers.any(KpiKey.class))).thenReturn(measure);
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        
        // MyKpiKey kpiKey2 = MyKpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(Lists.newArrayList(kpiKey));
        
        LOGGER.info(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/lastList/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
        httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].measure.value", Matchers.comparesEqualTo(12D)));
        
        Mockito.verify(measureHistoryService, Mockito.times(1)).getLastMeasures(org.mockito.Matchers.any(KpiKey.class));
        
    }
    
    @Test
    public void testLastListMeasuresWithExceptions() throws Exception {
    
        Measure measure = new Measure();
        measure.setDate(new Date());
        measure.setId(1);
        measure.setIdKpi(1);
        measure.setIdPerson(1);
        measure.setValue(12D);
        
        Mockito.when(measureHistoryService.getKpiMeasureValue(org.mockito.Matchers.any(KpiKey.class))).thenThrow(KPINotFoundException.class);
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        
        // MyKpiKey kpiKey2 = MyKpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(Lists.newArrayList(kpiKey));
        
        LOGGER.info(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/lastList/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isInternalServerError());
        Mockito.verify(measureHistoryService, Mockito.times(1)).getLastMeasures(org.mockito.Matchers.any(KpiKey.class));
        
    }
    
    @Test
    public void testLastMeasuresWithExceptions() throws Exception {
    
        Mockito.when(measureHistoryService.getKpiDoubleValue(org.mockito.Matchers.any(KpiKey.class))).thenThrow(KPINotFoundException.class);
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        
        // MyKpiKey kpiKey2 = MyKpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(kpiKey);
        
        LOGGER.info(jsonMessage);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/last/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isInternalServerError());
        
        Mockito.verify(measureHistoryService, Mockito.times(1)).getKpiDoubleValue(org.mockito.Matchers.any(KpiKey.class));
        
    }
    
    @Test
    public void testLastMeasuresWithNoMessage() throws Exception {
    
        Mockito.when(measureHistoryService.getKpiDoubleValue(null)).thenReturn(0D);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/last/"));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().is(500));
        
    }
    
    @Test
    public void testHistoricalMeasures() throws Exception {
    
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        
        Measure measure = new Measure();
        measure.setDate(new Date());
        measure.setId(1);
        measure.setIdKpi(1);
        measure.setIdPerson(1);
        measure.setValue(12D);
        List<Measure> measures = Lists.newArrayList(measure);
        
        Mockito.when(measureHistoryService.getHistory(kpiKey)).thenReturn(measures);
        
        SearchHistoricalMeasuresDto searchHistoricalMeasure = new SearchHistoricalMeasuresDto();
        searchHistoricalMeasure.setStart(new Date(2014, 1, 1));
        searchHistoricalMeasure.setEnd(new Date());
        searchHistoricalMeasure.setKpiKeys(Lists.newArrayList(kpiKey));
        
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(searchHistoricalMeasure);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/historical/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        
        Mockito.verify(measureHistoryService, Mockito.times(1)).getHistory(org.mockito.Matchers.any(KpiKey.class),
                org.mockito.Matchers.any(MeasureCriteria.class));
    }
    
    @Test
    public void testHistoricalMeasuresInvalideDate() throws Exception {
    
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        
        Measure measure = new Measure();
        measure.setDate(new Date());
        measure.setId(1);
        measure.setIdKpi(1);
        measure.setIdPerson(1);
        measure.setValue(12D);
        List<Measure> measures = Lists.newArrayList(measure);
        
        Mockito.when(measureHistoryService.getHistory(kpiKey)).thenReturn(measures);
        
        SearchHistoricalMeasuresDto searchHistoricalMeasure = new SearchHistoricalMeasuresDto();
        searchHistoricalMeasure.setEnd(new Date(2014, 1, 1));
        searchHistoricalMeasure.setStart(new Date());
        searchHistoricalMeasure.setKpiKeys(Lists.newArrayList(kpiKey));
        
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(searchHistoricalMeasure);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/historical/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
    
    @Test
    public void testHistoricalMeasuresByNumbers() throws Exception {
    
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        
        Measure measure = new Measure();
        measure.setDate(new Date());
        measure.setId(1);
        measure.setIdKpi(1);
        measure.setIdPerson(1);
        measure.setValue(12D);
        List<Measure> measures = Lists.newArrayList(measure);
        
        Mockito.when(measureHistoryService.getHistory(kpiKey)).thenReturn(measures);
        
        SearchHistoricalMeasuresDto searchHistoricalMeasure = new SearchHistoricalMeasuresDto();
        searchHistoricalMeasure.setKpiKeys(Lists.newArrayList(kpiKey));
        searchHistoricalMeasure.setNumber(25);
        
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(searchHistoricalMeasure);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/historical/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
    
    @Test
    public void testHistoricalMeasuresByNegativeNumbers() throws Exception {
    
        KpiKey kpiKey = KpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
        
        Measure measure = new Measure();
        measure.setDate(new Date());
        measure.setId(1);
        measure.setIdKpi(1);
        measure.setIdPerson(1);
        measure.setValue(12D);
        List<Measure> measures = Lists.newArrayList(measure);
        
        Mockito.when(measureHistoryService.getHistory(kpiKey)).thenReturn(measures);
        
        SearchHistoricalMeasuresDto searchHistoricalMeasure = new SearchHistoricalMeasuresDto();
        searchHistoricalMeasure.setKpiKeys(Lists.newArrayList(kpiKey));
        searchHistoricalMeasure.setNumber(-25);
        
        String jsonMessage = IntegrationTestUtil.convertObjectToJSON(searchHistoricalMeasure);
        
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/historical/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}
