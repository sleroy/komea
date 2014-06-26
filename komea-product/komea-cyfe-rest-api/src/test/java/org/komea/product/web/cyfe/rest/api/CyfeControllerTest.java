package org.komea.product.web.cyfe.rest.api;

import java.nio.charset.Charset;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.komea.product.backend.api.exceptions.EntityNotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Person;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.model.timeserie.TimeSerieOptions;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.service.dto.EntityStringKey;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.enums.Severity;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.komea.product.web.cyfe.rest.service.IStatsService;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ContextConfiguration(locations = {
        "classpath*:/spring/rest-servlet-test.xml"})
public class CyfeControllerTest extends AbstractSpringWebIntegrationTestCase {

    @Autowired
    private WebApplicationContext context;
    
    private MockMvc mockMvc;
    
    @Autowired
    @InjectMocks
    private CyfeController CyfeController;
    
    /*@Mock
    private IKPIService kpiService;
    
    @Mock
    private IEntityService entityService;
    
    @Mock
    private IStatisticsAPI statisticsAPI;*/
    
    @Mock
	private IStatsService service;
    
    private final String PATH = "/cyfe";
    private final String CSV_CONTENT_TYPE = "text/csv;charset=utf-8";
    
    
    @Before
    public void setUp() {
    
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockitoAnnotations.initMocks(this);
        
    }
    
    @Test
    @Ignore
    public void testGetValueOk() throws Exception {
    	
    	String kpiKey = "COMMIT_MESSAGE_LENGTH";
    	String entityKey = "sleroy";
    	TimeScale timescale = TimeScale.PER_DAY;
    	String dateAsString = "10012014";
    	Double goal = 98.20;
    	
    	/*Kpi kpi = new Kpi();
    	Mockito.when(kpiService.findKPIOrFail(kpiKey)).thenReturn(kpi);
    	
    	IEntity entity = new Person();
    	Mockito.when(entityService.getEntityOrFail(Matchers.any(EntityStringKey.class))).thenReturn(entity);  	    	
    	*/
    	Double kpiValue = Double.valueOf(92);
    	//Mockito.when(statisticsAPI.evaluateKpiValue(Matchers.any(TimeSerieOptions.class),Matchers.any(EntityKey.class))).thenReturn(kpiValue);
    	
    	Mockito.when(service.evaluateKpiValueWithDate(Matchers.anyString(), Matchers.anyString(), Matchers.any(TimeScale.class), Matchers.any(Date.class))).thenReturn(kpiValue);
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/value/{kpiKey}/{entityKey}", kpiKey, entityKey)
    			.param("timescale", timescale.toString())
    			.param("date", dateAsString)
    			.param("goal", goal.toString())
    			.accept(new MediaType("text", "csv", Charset.forName("utf-8"))));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
        
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void testGetValueWithKpiNotFound() throws Exception {
    	
    	String kpiKey = "INCORRECT_KPI";
    	String entityKey = "sleroy";
    	
    	//Mockito.when(kpiService.findKPIOrFail(kpiKey)).thenThrow(KPINotFoundException.class);
    	
    	Mockito.when(service.evaluateKpiValue(Matchers.anyString(), Matchers.anyString(), Matchers.any(TimeScale.class))).thenThrow(KPINotFoundException.class);
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/value/{kpiKey}/{entityKey}", kpiKey, entityKey));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isNotFound());
    	
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void testGetValueWithEntityNotFound() throws Exception {
    	
    	String kpiKey = "COMMIT_MESSAGE_LENGTH";
    	String entityKey = "entity-incorrect-key";
    	
    	/*Kpi kpi = new Kpi();
    	Mockito.when(kpiService.findKPIOrFail(kpiKey)).thenReturn(kpi);
    	
    	Mockito.when(entityService.getEntityOrFail(Matchers.any(EntityStringKey.class))).thenThrow(EntityNotFoundException.class);*/
    	
    	Mockito.when(service.evaluateKpiValue(Matchers.anyString(), Matchers.anyString(), Matchers.any(TimeScale.class))).thenThrow(EntityNotFoundException.class);  	
    	    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/value/{kpiKey}/{entityKey}", kpiKey, entityKey));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isNotFound());
    	
    }
    
    @Test
    public void testGetValueWithoutKpiKey() throws Exception {
    		  
    	String entityKey = "entity-key";
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/value/{kpiKey}/{entityKey}", null, entityKey));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isNotFound());
    	
    }
    
    @Test
    public void testGetValueWithoutEntityKey() throws Exception {
    		  
    	String kpiKey = "a-kpi-key";
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/value/{kpiKey}", kpiKey));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isNotFound());
    	
    }
    
    @Test
    public void testGetValues() throws Exception {
    	
    	List<String> kpiKeys = Arrays.asList("COMMIT_MESSAGE_LENGTH", "MODIFIED_FILES");
    	List<String> entityKeys = Arrays.asList("sleroy", "jguidoux");
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/values")
    			.param("kpiKeys", kpiKeys.toString())
    			.param("entityKeys", entityKeys.toString()));

        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    public void testGetValuesWithoutEntityKeys() throws Exception {
    	
    	List<String> kpiKeys = Arrays.asList("COMMIT_MESSAGE_LENGTH", "MODIFIED_FILES");
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/values")
    			.param("kpiKeys", kpiKeys.toString()));

        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
        
    }
    
    @Test
    public void testGetValuesWithoutKpiKeys() throws Exception {
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/values"));

        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isBadRequest());
    
    }
    
    @Test
    public void testGetValuesWithDateAndTimescale() throws Exception {
    	
    	List<String> kpiKeys = Arrays.asList("COMMIT_MESSAGE_LENGTH", "MODIFIED_FILES");
    	String dateAsString = "19062014";
    	TimeScale timescale = TimeScale.PER_MONTH;
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/values")
    			.param("kpiKeys", kpiKeys.toString())
    			.param("date", dateAsString)
    			.param("timescale", timescale.toString()));

        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    public void testGetValuesWithDateOnly() throws Exception {
    	
    	List<String> kpiKeys = Arrays.asList("COMMIT_MESSAGE_LENGTH", "MODIFIED_FILES");
    	String dateAsString = "19062014";
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/values")
    			.param("kpiKeys", kpiKeys.toString())
    			.param("date", dateAsString));

        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    public void testGetValuesWithTimescaleOnly() throws Exception {
    
    	List<String> kpiKeys = Arrays.asList("COMMIT_MESSAGE_LENGTH", "MODIFIED_FILES");
    	TimeScale timescale = TimeScale.PER_MONTH;
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/values")
    			.param("kpiKeys", kpiKeys.toString())
    			.param("timescale", timescale.toString()));

        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
        
    }
    
    @Test
    public void testGetSerie() throws Exception {
    	
    	String kpiKey = "COMMIT_MESSAGE_LENGTH";
    	List<String> entityKeys = Arrays.asList("sleroy", "jguidoux");
    	TimeScale timescale = TimeScale.PER_DAY;
    	String dateAsString = "10012014";
    	List<String> colors = Arrays.asList("#FFFFFF", "#FF1245");
    	List<String> types = Arrays.asList("area", "line");
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/serie/{kpiKey}", kpiKey)
    			.param("entityKeys", entityKeys.toString())
    			.param("timescale", timescale.toString())
    			.param("since", dateAsString)
    			.param("colors", colors.toString())
    			.param("types", types.toString()));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
        
    }
    
    @Test
    public void testGetSerieWithoutKpiKey() throws Exception {
    	
    	List<String> entityKeys = Arrays.asList("sleroy", "jguidoux");
    	TimeScale timescale = TimeScale.PER_DAY;
    	String dateAsString = "10012014";
    	List<String> colors = Arrays.asList("#FFFFFF", "#FF1245");
    	List<String> types = Arrays.asList("area", "line");
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/serie")
    			.param("entityKeys", entityKeys.toString())
    			.param("timescale", timescale.toString())
    			.param("since", dateAsString)
    			.param("colors", colors.toString())
    			.param("types", types.toString()));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isNotFound());
        
    }
    
    @Test
    public void testGetSerieWithoutOptionalParams() throws Exception {
    	
    	String kpiKey = "COMMIT_MESSAGE_LENGTH";
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/serie/{kpiKey}", kpiKey));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    public void testGetPie() throws Exception {
    	
    	String kpiKey = "COMMIT_MESSAGE_LENGTH";
    	List<String> entityKeys = Arrays.asList("sleroy", "jguidoux");
    	List<String> colors = Arrays.asList("#FFFFFF", "#FF1245");
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/pie/{kpiKey}", kpiKey)
    			.param("entityKeys", entityKeys.toString())
    			.param("colors", colors.toString()));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    public void testGetPieWithouOptionalParams() throws Exception {
    	
    	String kpiKey = "COMMIT_MESSAGE_LENGTH";
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/pie/{kpiKey}", kpiKey));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    public void testGetEvents() throws Exception {

    	String entityType = "Project";
    	Severity severityMin = Severity.INFO;
    	Integer sizeMax = 10;
    	List<String> eventKeys = Arrays.asList("Jenkins build failed", "Jenkins build industrialization");
    	List<String> entityKeys = Arrays.asList("komea", "komea-bundle");
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/events/{entityType}", entityType)
    			.param("severityMin", severityMin.toString())
    			.param("sizeMax", sizeMax.toString())
    			.param("eventKeys", eventKeys.toString())
    			.param("entityKeys", entityKeys.toString()));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
        
    }
    
    @Test
    public void testGetCohort() throws Exception {
    	
    	String kpiKey = "COMMIT_MESSAGE_LENGTH";
    	List<String> entityKeys = Arrays.asList("sleroy", "jguidoux");
    	String dateAsString = "19062014";
    	TimeScale timescale = TimeScale.PER_MONTH;
    	Double goal = 95.20;
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/pie/{kpiKey}", kpiKey)
    			.param("entityKeys", entityKeys.toString())
    			.param("since", dateAsString)
    			.param("timescale", timescale.toString())
    			.param("goal", goal.toString()));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    	
    }
    
	
}
