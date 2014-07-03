package org.komea.product.backend.utils;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.dto.BaseEntityDto;
import org.komea.product.database.dto.SearchEventDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ExtendedEntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Project;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.common.collect.Lists;


public class EventsFilterTest {

	@InjectMocks
	private EventsFilter filter = new EventsFilter();
	
	@Mock
	private IEventViewerService eventService;
    
    @Mock
    private IEntityService entityService;
    
    private List<Project> projects;
    
    @Before
	public void setUp() {
	
	    MockitoAnnotations.initMocks(this); 

	    Project p1 = new Project();
	    p1.setProjectKey("P1");
	    
	    Project p2 = new Project();
	    p2.setProjectKey("P2");
	    
	    projects = Lists.newArrayList(p1, p2);
	    
	}
    
    private List<IEvent> getGlobalActivity() {
    	
    	EventType type1 = new EventType(1, "event1", "Jenkins build 1", Severity.INFO, true, "jenkins build completed", EntityType.PROJECT, ProviderType.CI_BUILD);
    	EventType type2 = new EventType(1, "event2", "Jenkins build 2", Severity.INFO, true, "jenkins build completed", EntityType.PROJECT, ProviderType.CI_BUILD);
		  	
    	Event e1 = new Event();
		e1.setEventType(type1);
		e1.setMessage("Jenkins build performed for Komea Bundle");	
		e1.setDate(DateTime.now().toDate());
		e1.setProject(projects.get(0));
		
		Event e2 = new Event();
		e2.setEventType(type1);
		e2.setMessage("Jenkins build performed for Cyfe API");
		e2.setDate(DateTime.now().toDate());
		e2.setProject(projects.get(1));
		
		Event e3 = new Event();
		e3.setEventType(type2);
		e3.setMessage("Jenkins build performed for Komea");
		e3.setDate(DateTime.now().toDate());
		e3.setProject(projects.get(0));
		
		Event e4 = new Event();
		e4.setEventType(type2);
		e4.setMessage("Jenkins build performed for Komea");
		e4.setDate(DateTime.now().toDate());
		e4.setProject(projects.get(1));
    	
    	return Lists.newArrayList((IEvent) e1, e2, e3, e4);
    }
    
    @Test
    public void testFilterEvents() {
    	
    	List<IEvent> events = getGlobalActivity();
    	
    	Project project = projects.get(0);
    	ExtendedEntityType entityType = ExtendedEntityType.PROJECT;
    	Severity severityMin = Severity.INFO;
    	int sizeMax = 10;
    	List<String> entityKeys = Lists.newArrayList(project.getKey());
    	List<String> eventKeys = Lists.newArrayList(events.get(0).getEventType().getKey());
    	
    	SearchEventDto search = new SearchEventDto();
		search.setEntityType(entityType);
		search.setSeverityMin(severityMin);
		search.setMaxEvents(sizeMax);
		search.setEntityKeys(entityKeys);
		search.setEventTypeKeys(eventKeys);
		
		List<BaseEntityDto> baseEntities = Lists.newArrayList(new BaseEntityDto(entityType.getKpiType(), 1, project.getKey(), project.getName(), project.getDescription()));
		
		Mockito.when(entityService.getSubEntities(Matchers.any(ExtendedEntityType.class), Matchers.any(List.class))).thenReturn(baseEntities);
		Mockito.when(eventService.getEntityKey(entityType.getKpiType(), events.get(0))).thenReturn(project.getKey());		
		
		List<IEvent> result = filter.filterEvents(search, events);
		
		assertEquals(Lists.newArrayList(events.get(0)), result);
    	
    }
	
}
