
package org.komea.product.web.rest.api;


import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.demodata.KpiDemoService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.KPIValueTable;
import org.komea.product.backend.service.kpi.KpiLineValue;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Project;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
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
    private IKPIService           kpiService;
    
    @Autowired
    private KpiDemoService        kpiDemoService;
    
    @Before
    public void setUp() {
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGetKpiRealTimeValues() throws Exception {
    
        final Kpi kpi = kpiDemoService.numberBuildPerDay();
        
        final KpiKey kpiKey = KpiKey.ofKpi(kpi);
        
        Project project = new Project();
        project.setId(1);
        project.setName("project 1");
        
        Mockito.when(kpiService.getKpiRealTimeValues(kpiKey)).thenAnswer(new Answer<KPIValueTable<Project>>() {
            
            @Override
            public KPIValueTable<Project> answer(final InvocationOnMock _invocation) throws Throwable {
            
                Kpi kpi = new Kpi();
                kpi.setId(1);
                kpi.setKpiKey("KPI1");
                kpi.setEntityID(1);
                kpi.setEntityType(EntityType.PROJECT);
                List<KpiLineValue<Project>> values = Lists.newArrayList();
                
                Project project = new Project();
                project.setId(1);
                project.setName("project 1");
                
                values.add(new KpiLineValue<Project>(project, 5D));
                KPIValueTable<Project> kpiValueTable = new KPIValueTable<Project>(kpi, values);
                
                return kpiValueTable;
            }
        });
        
        final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(kpiKey);
        LOGGER.info(jsonMessage);
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/realtime")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("kpi.kpiKey",
        // org.hamcrest.Matchers.equalToIgnoringCase("NUMBER_OF_BUILD_PER_DAY")));
        
        Mockito.verify(kpiService, Mockito.times(1)).getKpiRealTimeValues(Matchers.any(KpiKey.class));
        
    }
    @Test
    public void testGetKpiRealTimeValuesWithException() throws Exception {
    
        final KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PROJECT, 1);
        
        Project project = new Project();
        project.setId(1);
        project.setName("project 1");
        
        Mockito.when(kpiService.getKpiRealTimeValues(org.mockito.Matchers.any(KpiKey.class))).thenThrow(KPINotFoundException.class);
        
        final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(kpiKey);
        LOGGER.info(jsonMessage);
        final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/realtime")
                .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isInternalServerError());
        
        Mockito.verify(kpiService, Mockito.times(1)).getKpiRealTimeValues(Matchers.any(KpiKey.class));
        
    }
    // @Test
    // public void testHistoricalMeasures() throws Exception {
    //
    // final KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PERSON, 1);
    //
    // final Measure measure = new Measure();
    // measure.setDate(new Date());
    // measure.setId(1);
    // measure.setIdKpi(1);
    // measure.setIdPerson(1);
    // measure.setValue(12D);
    // final List<Measure> measures = Lists.newArrayList(measure);
    //
    // Mockito.when(measureHistoryService.getHistory(kpiKey)).thenReturn(measures);
    //
    // final SearchHistoricalMeasuresDto searchHistoricalMeasure = new SearchHistoricalMeasuresDto();
    // searchHistoricalMeasure.setStart(new Date(2014, 1, 1));
    // searchHistoricalMeasure.setEnd(new Date());
    // searchHistoricalMeasure.setKpiKeys(Lists.newArrayList(kpiKey));
    //
    // final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(searchHistoricalMeasure);
    //
    // final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/historical/")
    // .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
    //
    // httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
    //
    // Mockito.verify(measureHistoryService, Mockito.times(1)).getHistory(org.mockito.Matchers.any(KpiKey.class),
    // org.mockito.Matchers.any(MeasureCriteria.class));
    // }
    //
    // @Test
    // public void testHistoricalMeasuresByNegativeNumbers() throws Exception {
    //
    // final KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PERSON, 1);
    //
    // final Measure measure = new Measure();
    // measure.setDate(new Date());
    // measure.setId(1);
    // measure.setIdKpi(1);
    // measure.setIdPerson(1);
    // measure.setValue(12D);
    // final List<Measure> measures = Lists.newArrayList(measure);
    //
    // Mockito.when(measureHistoryService.getHistory(kpiKey)).thenReturn(measures);
    //
    // final SearchHistoricalMeasuresDto searchHistoricalMeasure = new SearchHistoricalMeasuresDto();
    // searchHistoricalMeasure.setKpiKeys(Lists.newArrayList(kpiKey));
    // searchHistoricalMeasure.setNumber(-25);
    //
    // final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(searchHistoricalMeasure);
    //
    // final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/historical/")
    // .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
    //
    // httpRequest.andExpect(MockMvcResultMatchers.status().isInternalServerError());
    // }
    //
    // @Test
    // public void testHistoricalMeasuresByNumbers() throws Exception {
    //
    // final KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PERSON, 1);
    //
    // final Measure measure = new Measure();
    // measure.setDate(new Date());
    // measure.setId(1);
    // measure.setIdKpi(1);
    // measure.setIdPerson(1);
    // measure.setValue(12D);
    // final List<Measure> measures = Lists.newArrayList(measure);
    //
    // Mockito.when(measureHistoryService.getHistory(kpiKey)).thenReturn(measures);
    //
    // final SearchHistoricalMeasuresDto searchHistoricalMeasure = new SearchHistoricalMeasuresDto();
    // searchHistoricalMeasure.setKpiKeys(Lists.newArrayList(kpiKey));
    // searchHistoricalMeasure.setNumber(25);
    //
    // final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(searchHistoricalMeasure);
    //
    // final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/historical/")
    // .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
    //
    // httpRequest.andDo(MockMvcResultHandlers.print());
    // httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
    // }
    //
    // @Test
    // public void testHistoricalMeasuresInvalideDate() throws Exception {
    //
    // final KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PERSON, 1);
    //
    // final Measure measure = new Measure();
    // measure.setDate(new Date());
    // measure.setId(1);
    // measure.setIdKpi(1);
    // measure.setIdPerson(1);
    // measure.setValue(12D);
    // final List<Measure> measures = Lists.newArrayList(measure);
    //
    // Mockito.when(measureHistoryService.getHistory(kpiKey)).thenReturn(measures);
    //
    // final SearchHistoricalMeasuresDto searchHistoricalMeasure = new SearchHistoricalMeasuresDto();
    // searchHistoricalMeasure.setEnd(new Date(2014, 1, 1));
    // searchHistoricalMeasure.setStart(new Date());
    // searchHistoricalMeasure.setKpiKeys(Lists.newArrayList(kpiKey));
    //
    // final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(searchHistoricalMeasure);
    //
    // final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/historical/")
    // .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
    //
    // httpRequest.andExpect(MockMvcResultMatchers.status().isInternalServerError());
    // }
    //
    // @Test
    // public void testLastListMeasures() throws Exception {
    //
    // final Measure measure = new Measure();
    // measure.setDate(new Date());
    // measure.setId(1);
    // measure.setIdKpi(1);
    // measure.setIdPerson(1);
    // measure.setValue(12D);
    //
    // Mockito.when(measureHistoryService.getKpiRealTimeValueInMeasureObject(org.mockito.Matchers.any(KpiKey.class))).thenReturn(measure);
    // final KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PERSON, 1);
    //
    // // MyKpiKey kpiKey2 = MyKpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
    // final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(Lists.newArrayList(kpiKey));
    //
    // LOGGER.info(jsonMessage);
    //
    // final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/lastList/")
    // .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
    //
    // httpRequest.andDo(MockMvcResultHandlers.print());
    // httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
    // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$[0].measure.value", Matchers.comparesEqualTo(12D)));
    //
    // Mockito.verify(measureHistoryService, Mockito.times(1)).getKpiRealTimeValueInMeasureObject(org.mockito.Matchers.any(KpiKey.class));
    //
    // }
    //
    // @Test
    // public void testLastListMeasuresWithExceptions() throws Exception {
    //
    // final Measure measure = new Measure();
    // measure.setDate(new Date());
    // measure.setId(1);
    // measure.setIdKpi(1);
    // measure.setIdPerson(1);
    // measure.setValue(12D);
    //
    // Mockito.when(measureHistoryService.getKpiRealTimeValueInMeasureObject(org.mockito.Matchers.any(KpiKey.class)))
    // .thenThrow(KPINotFoundException.class);
    // final KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PERSON, 1);
    //
    // // MyKpiKey kpiKey2 = MyKpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
    // final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(Lists.newArrayList(kpiKey));
    //
    // LOGGER.info(jsonMessage);
    //
    // final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/lastList/")
    // .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
    //
    // httpRequest.andDo(MockMvcResultHandlers.print());
    // httpRequest.andExpect(MockMvcResultMatchers.status().isInternalServerError());
    // Mockito.verify(measureHistoryService, Mockito.times(1)).getKpiRealTimeValueInMeasureObject(org.mockito.Matchers.any(KpiKey.class));
    //
    // }
    //
    // @Test
    // public void testLastMeasures() throws Exception {
    //
    // Mockito.when(measureHistoryService.getKpiRealTimeValues(org.mockito.Matchers.any(KpiKey.class))).thenReturn(25D);
    // final KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PERSON, 1);
    //
    // // MyKpiKey kpiKey2 = MyKpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
    // final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(kpiKey);
    //
    // LOGGER.info(jsonMessage);
    //
    // final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/last/")
    // .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
    //
    // httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
    // httpRequest.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.comparesEqualTo(25D)));
    // }
    //
    // @Test
    // public void testLastMeasuresWithExceptions() throws Exception {
    //
    // Mockito.when(measureHistoryService.getKpiRealTimeValues(org.mockito.Matchers.any(KpiKey.class))).thenThrow(KPINotFoundException.class);
    // final KpiKey kpiKey = KpiKey.ofKpiNameAndEntityDetails("KPI1", EntityType.PERSON, 1);
    //
    // // MyKpiKey kpiKey2 = MyKpiKey.newKpiWithEntityDetails("KPI1", EntityType.PERSON, 1);
    // final String jsonMessage = IntegrationTestUtil.convertObjectToJSON(kpiKey);
    //
    // LOGGER.info(jsonMessage);
    //
    // final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/last/")
    // .contentType(MediaType.APPLICATION_JSON).content(jsonMessage));
    //
    // httpRequest.andExpect(MockMvcResultMatchers.status().isInternalServerError());
    //
    // Mockito.verify(measureHistoryService, Mockito.times(1)).getKpiRealTimeValues(org.mockito.Matchers.any(KpiKey.class));
    //
    // }
    //
    // @Test
    // public void testLastMeasuresWithNoMessage() throws Exception {
    //
    // Mockito.when(measureHistoryService.getKpiRealTimeValues(null)).thenReturn(0D);
    //
    // final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.post("/measures/last/"));
    //
    // httpRequest.andExpect(MockMvcResultMatchers.status().is(500));
    //
    // }
    
}
