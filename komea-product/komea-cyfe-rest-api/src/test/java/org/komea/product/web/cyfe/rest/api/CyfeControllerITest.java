package org.komea.product.web.cyfe.rest.api;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IKpiLoadingService;
import org.komea.product.backend.service.IKpiGoalService;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.utils.EventsFilter;
import org.komea.product.backend.utils.StringToKpiConvertor;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.KpiGoal;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.komea.product.web.cyfe.rest.service.IStatsService;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
        "classpath*:/spring/application-context-test.xml", 
        "classpath*:/spring/dispatcher-servlet-test.xml",
        "classpath*:/spring/rest-servlet-test.xml" })
@TransactionConfiguration(defaultRollback = true)
@DatabaseTearDown(value = "database.xml", type = DatabaseOperation.DELETE_ALL)
public class CyfeControllerITest extends AbstractSpringDBunitIntegrationTest{

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc               mockMvc;
	
    @Autowired
    @InjectMocks
    private CyfeController CyfeController;
	
    @Autowired
    @InjectMocks
    private IStatsService statsService;
    
    @Mock
    private IEventViewerService eventService;
    
    @Mock
	private IKpiGoalService goalService;
    
    @Mock
    private EventsFilter eventsFilter;
    
    @Autowired
    private StringToKpiConvertor kpiConvertor;
    
	@Autowired
	private IKpiLoadingService    kpiLoading;
	
    private final String PATH = "/cyfe";
    private final String CSV_CONTENT_TYPE = "text/csv;charset=UTF-8";
	
	private final List<String> correct_kpi_keys = Lists.newArrayList(
			"COMMIT_MESSAGE_LENGTH", "TEST_KPI_PERSON"
	);
	
	private final List<String> correct_entity_keys = Lists.newArrayList(
			"sleroy", "jguidoux", "KOMEA", "SYSTEM"
	);
	
	private final List<String> correct_event_keys = Lists.newArrayList(
			"BUILD_LAUNCHED", "demo_alert"
	);
    
	@Before
	public void setUp() {
	
	    kpiLoading.initLoadingService();
	    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	    MockitoAnnotations.initMocks(this); 

	    System.out.println("FormulaID:");
	    for (String key : correct_kpi_keys) {
	    	System.out.println(FormulaID.of(kpiConvertor.convert(key)).getId());
	    }
	    
	}

    @Test
    @DatabaseSetup("database.xml")
    public void testGetValueOk() throws Exception {

    	TimeScale timescale = TimeScale.PER_DAY;
    	String dateAsString = "2014-01-06";
    	Double goal = 98.20;   	  	
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/value/{kpiKey}/{entityKey}", correct_kpi_keys.get(0), correct_entity_keys.get(0))
    			.param("timescale", timescale.toString())
    			.param("date", dateAsString)
    			.param("goal", goal.toString())
    			.accept(MediaType.parseMediaType(CSV_CONTENT_TYPE)));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(CSV_CONTENT_TYPE));
        
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetValueWithoutOptionalParams() throws Exception {
  
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/value/{kpiKey}/{entityKey}", correct_kpi_keys.get(0), correct_entity_keys.get(0))
    			.accept(MediaType.parseMediaType(CSV_CONTENT_TYPE)));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetValueKpiNotFound() throws Exception {
    
    	String kpiKey = "BAD_KEY"; 	 	

    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/value/{kpiKey}/{entityKey}", kpiKey, correct_entity_keys.get(0))
    			.accept(MediaType.parseMediaType(CSV_CONTENT_TYPE)));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isNotFound());
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetValueEntityNotFound() throws Exception {
    
    	String entityKey = "unknown";  	

    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/value/{kpiKey}/{entityKey}", correct_kpi_keys.get(0), entityKey)
    			.accept(MediaType.parseMediaType(CSV_CONTENT_TYPE)));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isNotFound());
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetValueWithWrongDateFormat() throws Exception {
    
    	TimeScale timescale = TimeScale.PER_DAY;
    	String dateAsString = "20140601";
    	Double goal = 98.20;   	
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/value/{kpiKey}/{entityKey}", correct_kpi_keys.get(0), correct_entity_keys.get(0))
    			.param("timescale", timescale.toString())
    			.param("date", dateAsString)
    			.param("goal", goal.toString())
    			.accept(MediaType.parseMediaType(CSV_CONTENT_TYPE)));
        
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isBadRequest());
    	
    }
	
    @Test
    @DatabaseSetup("database.xml")
    public void testGetValues() throws Exception {  
    	
    	TimeScale timescale = TimeScale.PER_DAY;
    	String dateAsString = "2014-06-01";
    	    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/values")
    			.param("timescale", timescale.toString())
    			.param("date", dateAsString)
    			.param("kpiKeys", correct_kpi_keys.get(0)+","+correct_kpi_keys.get(1))
    			.param("entityKeys", correct_entity_keys.get(0)+","+correct_entity_keys.get(1)));

        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetValuesWithoutOptionalParams() throws Exception {  
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/values")
    			.param("kpiKeys", correct_kpi_keys.get(0)+","+correct_kpi_keys.get(1)));

        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
	
    @Test
    @DatabaseSetup("database.xml")
    public void testGetValuesWithoutKpi() throws Exception {  
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/values"));
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isBadRequest());
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetValuesWithWrongKpiKey() throws Exception {  
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/values")
    			.param("kpiKeys", correct_kpi_keys.get(0)+",BAD_KPI_KEY"));

        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isNotFound());
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetValuesWithWrongEntityKey() throws Exception {  

    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/values")
    			.param("kpiKeys", correct_kpi_keys.get(0)+","+correct_kpi_keys.get(1))
    			.param("entityKeys", correct_entity_keys.get(0)+", BAD_ENTITY_KEY"));

        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isNotFound());
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetSerie() throws Exception {  
    	
    	TimeScale timescale = TimeScale.PER_DAY;
    	String dateAsString = "2014-06-01";
    	    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/serie/{kpiKey}", correct_kpi_keys.get(0))
    			.param("timescale", timescale.toString())
    			.param("since", dateAsString)
    			.param("entityKeys", correct_entity_keys.get(0)+","+correct_entity_keys.get(1))
    			.param("color", "#FFEEFF, #34EE12")
    			.param("types", "line, area"));
    	

        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetSerieWithoutOptionalParams() throws Exception {  
    	    	    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/serie/{kpiKey}", correct_kpi_keys.get(0)));
    	
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetSerieWithWrongKpiKey() throws Exception {  
    	    	    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/serie/{kpiKey}", "BAD_KPI_KEY"));
    	
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isNotFound());
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetEvents() throws Exception {
    	
    	EventType type = new EventType(1, "JenkinsBuild", "Jenkins build", Severity.INFO, true, "jenkins build completed", EntityType.PROJECT, ProviderType.CI_BUILD);
		
    	Event e1 = new Event();
		e1.setEventType(type);
		e1.setMessage("Jenkins build performed for Komea Bundle");	
		e1.setDate(DateTime.now().toDate());
		
		Event e2 = new Event();
		e2.setEventType(type);
		e2.setMessage("Jenkins build performed for Cyfe API");
		e2.setDate(DateTime.now().toDate());
		
		Event e3 = new Event();
		e3.setEventType(type);
		e3.setMessage("Jenkins build performed for Komea");
		e3.setDate(DateTime.now().toDate());
		
		Event e4 = new Event();
		e4.setEventType(type);
		e4.setMessage("Jenkins build performed for Komea");
		e4.setDate(DateTime.now().toDate());
    	
    	List<IEvent> events = Lists.newArrayList((IEvent) e1, e2, e3, e4);
    	Mockito.when(eventService.getGlobalActivity()).thenReturn(events); 
    	Mockito.when(eventsFilter.filterEvents(Matchers.any(SearchEventDto.class), Matchers.anyListOf(IEvent.class))).thenReturn(events);
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/events/{entityType}", ExtendedEntityType.PROJECT)
    			.param("severityMin", Severity.INFO.toString())
    			.param("sizeMax", Integer.toString(10))
    			.param("eventKeys", correct_event_keys.get(0)+","+correct_event_keys.get(1))
    			.param("entityKeys", correct_entity_keys.get(2)+","+correct_entity_keys.get(3)));
    	
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetEventsWithoutOptionalParams() throws Exception {
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/events/{entityType}", ExtendedEntityType.PROJECT)
    			.param("severityMin", Severity.INFO.toString())
    			.param("sizeMax", Integer.toString(10))
    			.param("eventKeys", correct_event_keys.get(0)+","+correct_event_keys.get(1)));
    	
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetPie() throws Exception {   	
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/pie/{kpiKey}", correct_kpi_keys.get(0))
    			.param("entityKeys", correct_entity_keys.get(0)+","+correct_entity_keys.get(1))
    			.param("timescale", TimeScale.PER_MONTH.toString())
    			.param("colors", "#FFEEFF, #34EE12"));
    	
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetPieWihtoutOptionalParams() throws Exception {   	
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/pie/{kpiKey}", correct_kpi_keys.get(0)));
    	
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetCohort() throws Exception {   	
    	
    	TimeScale timescale = TimeScale.PER_DAY;
    	String dateAsString = "2014-06-01";
    	Double goal = 95.0;
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/cohort/{kpiKey}", correct_kpi_keys.get(0))
    			.param("entityKeys", correct_entity_keys.get(0)+","+correct_entity_keys.get(1))
    			.param("timescale", timescale.toString())
    			.param("since", dateAsString)
    			.param("goal", goal.toString()));
    	
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
    
    @Test
    @DatabaseSetup("database.xml")
    public void testGetCohortWihtoutOptionalParams() throws Exception {   	
    	    	
    	KpiGoal goal = new KpiGoal();
    	goal.setValue(95.0);
    	
    	Mockito.when(goalService.findKpiGoals(Matchers.any(KpiKey.class))).thenReturn(Lists.newArrayList(goal));
    	
    	final ResultActions httpRequest = mockMvc.perform(MockMvcRequestBuilders.get(PATH+"/cohort/{kpiKey}", correct_kpi_keys.get(0)));
    	
        httpRequest.andDo(MockMvcResultHandlers.print());
        httpRequest.andExpect(MockMvcResultMatchers.status().isOk());
        httpRequest.andExpect(MockMvcResultMatchers.content().contentType(this.CSV_CONTENT_TYPE));
    	
    }
}
