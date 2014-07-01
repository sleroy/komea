package org.komea.product.web.cyfe.rest.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Test;
import org.komea.product.database.alert.Event;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Person;
import org.komea.product.model.timeserie.TimeCoordinate;
import org.komea.product.model.timeserie.TimeSerie;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public class CyfeServiceTest extends AbstractSpringWebIntegrationTestCase {

	@Autowired
	private ICyfeService cyfeService;
	
	private Person getPerson1() {
		Person entity1 = new Person();
		entity1.setId(1);
		entity1.setFirstName("Sylvain");
		entity1.setLastName("Leroy");
		entity1.setLogin("sleroy");
		return entity1;
	}
	
	private Person getPerson2() {
		Person entity2 = new Person();
		entity2.setId(2);
		entity2.setFirstName("jérémie");
		entity2.setLastName("guidoux");
		entity2.setLogin("jguidoux");
		return entity2;
	}
	
	private Kpi getKpi1() {
		Kpi kpi1 = new Kpi();
		kpi1.setId(35);
		kpi1.setKpiKey("COMMIT_MESSAGE_LENGTH");
		kpi1.setName("Commit message length");
		return kpi1;
	}
	
	private Kpi getKpi2() {
		Kpi kpi2 = new Kpi();
		kpi2.setId(36);
		kpi2.setKpiKey("ADDED_LINES");
		kpi2.setName("Added lines");
		return kpi2;
	}
	
	private TimeSerie createTimeSerie(final IEntity _entity, final List<TimeCoordinate> _timeCoordinates) {
		return new TimeSerie() {
			private static final long serialVersionUID = 1L;
			@Override
			public List<TimeCoordinate> getCoordinates() {
				return _timeCoordinates;
			}
			@Override
			public EntityKey getEntityKey() {
				return _entity.getEntityKey();
			}			
		};
	}
	
	@Test
	public void testBuildValue() {
		Kpi kpi = this.getKpi1();
		Person entity = this.getPerson1();
		Double value = 53.00;
		List<String[]> result = cyfeService.buildValue(kpi, entity, value, null).convertToStringList();
		assertEquals(2, result.size());
		assertArrayEquals(new String[] { entity.getDisplayName() }, result.get(0));
		assertArrayEquals(new String[] { value.toString() }, result.get(1));
	}
	
	@Test
	public void testBuildValueWithGoal() {
		Kpi kpi = this.getKpi1();
		Person entity = this.getPerson2();
		Double value = 53.00;
		Double goal = 70.00;
		List<String[]> result = cyfeService.buildValue(kpi, entity, value, goal).convertToStringList();
		assertEquals(2, result.size());
		assertArrayEquals(new String[] { entity.getDisplayName(), "Target" }, result.get(0));
		assertArrayEquals(new String[] { value.toString(), goal.toString() }, result.get(1));
	}
	
	@Test
	public void testBuildValues() {
		
		Map<Kpi, KpiResult> values = Maps.newHashMap();
		Map<EntityKey, Number> result1 = Maps.newHashMap();
		Map<EntityKey, Number> result2 = Maps.newHashMap();
		List<IEntity> entities = Lists.newArrayList((IEntity) this.getPerson1(), this.getPerson2());
		
		Kpi kpi1 = this.getKpi1();	
		Kpi kpi2 = this.getKpi2();
		
		Double[] doubles = new Double[] { 5.0, 10.0, 15.0, 20.0 };
		
		result1.put(entities.get(0).getEntityKey(), doubles[0]);
		result1.put(entities.get(1).getEntityKey(), doubles[1]);
		result2.put(entities.get(0).getEntityKey(), doubles[2]);
		result2.put(entities.get(1).getEntityKey(), doubles[3]);
		
		values.put(kpi1, new KpiResult(result1));
		values.put(kpi2, new KpiResult(result2));
			
		List<String[]> response = cyfeService.buildValues(entities, values).convertToStringList();

		assertEquals(3, response.size());
		assertArrayEquals(new String[] { "Label", entities.get(0).getDisplayName(), entities.get(1).getDisplayName() }, response.get(0));
		assertArrayEquals(new String[] { kpi1.getDisplayName(), doubles[0].toString(), doubles[1].toString() }, response.get(1));
		assertArrayEquals(new String[] { kpi2.getDisplayName(), doubles[2].toString(), doubles[3].toString() }, response.get(2));
		
	}
	
	@Test
	public void testBuildSeries() {
		
		Kpi kpi = this.getKpi1();
		
		List<IEntity> entities = Lists.newArrayList((IEntity) this.getPerson1(), this.getPerson2());
		
		final TimeCoordinate c1 = new TimeCoordinate();
		c1.setValue(5.0);
		c1.setDay(1);
		c1.setMonth(Calendar.JUNE+1);
		c1.setYear(2014);
		
		final TimeCoordinate c2 = new TimeCoordinate();
		c2.setValue(10.0);
		c2.setDay(2);
		c2.setMonth(Calendar.JUNE+1);
		c2.setYear(2014);
		
		final TimeCoordinate c3 = new TimeCoordinate();
		c3.setValue(7.0);
		c3.setDay(1);
		c3.setMonth(Calendar.JUNE+1);
		c3.setYear(2014);
		
		final TimeCoordinate c4 = new TimeCoordinate();
		c4.setValue(12.0);
		c4.setDay(2);
		c4.setMonth(Calendar.JUNE+1);
		c4.setYear(2014);
			
		TimeSerie ts1 = createTimeSerie(entities.get(0), Lists.newArrayList(c1, c2));
		TimeSerie ts2 = createTimeSerie(entities.get(1), Lists.newArrayList(c3, c4));			
		List<TimeSerie> timeSeries = Lists.newArrayList(ts1, ts2);
		
		List<String> colors = Lists.newArrayList("#FFFFFF", "#98EE12");
		List<String> types = Lists.newArrayList("area", "line");
		
		List<String[]> response = cyfeService.buildSerie(kpi, entities, timeSeries, colors, types).convertToStringList();
		
		assertEquals(5, response.size());
		assertArrayEquals(new String[] { "Date", entities.get(0).getDisplayName(), entities.get(1).getDisplayName() }, response.get(0));
		assertArrayEquals(new String[] { "20140601", c1.getValue().toString(), c3.getValue().toString() }, response.get(1));
		assertArrayEquals(new String[] { "20140602", c2.getValue().toString(), c4.getValue().toString() }, response.get(2));
		assertArrayEquals(new String[] { "Color", colors.get(0), colors.get(1) }, response.get(3));
		assertArrayEquals(new String[] { "Type", types.get(0), types.get(1) }, response.get(4));
		
	}
	
	@Test
	public void testBuildEvents() {	
		
		EventType type = new EventType();
		type.setName("Jenkins build");
		
		// Now
		long t0 = DateTime.now().toDate().getTime();	
		// 12min 50s ago
		long t1 = t0 - ( 12 * 60 * 1000 +  50 * 1000 );
		// 1h 58min 24s ago
		long t2 = t0 - ( 1 * 3600 * 1000 + 58 * 60 * 1000 +  24 * 1000 );
		// 1 day 4h ago
		long t3 = t0 - ( 28 * 3600 * 1000 );
		// 2 days 1h ago
		long t4 = t0 - ( 49 * 3600 * 1000 );
		
		Event e1 = new Event();
		e1.setEventType(type);
		e1.setMessage("Jenkins build performed for Komea Bundle");	
		e1.setDate(new Date(t1));
		
		Event e2 = new Event();
		e2.setEventType(type);
		e2.setMessage("Jenkins build performed for Cyfe API");
		e2.setDate(new Date(t2));
		
		Event e3 = new Event();
		e3.setEventType(type);
		e3.setMessage("Jenkins build performed for Komea");
		e3.setDate(new Date(t3));
		
		Event e4 = new Event();
		e4.setEventType(type);
		e4.setMessage("Jenkins build performed for Komea");
		e4.setDate(new Date(t4));
		
		List<IEvent> events = Lists.newArrayList((IEvent) e1, e2, e3, e4);
		
		List<String[]> response = cyfeService.buildEventsTable(events).convertToStringList();
		
		assertEquals(5, response.size());
		assertArrayEquals(new String[] { "Event", "Message", "Time" }, response.get(0));
		assertArrayEquals(new String[] { e1.getEventType().getDisplayName(), e1.getMessage(), "12min 50s ago" }, response.get(1));
		assertArrayEquals(new String[] { e2.getEventType().getDisplayName(), e2.getMessage(), "1h 58min 24s ago" }, response.get(2));
		assertArrayEquals(new String[] { e3.getEventType().getDisplayName(), e3.getMessage(), "1 day ago" }, response.get(3));
		assertArrayEquals(new String[] { e4.getEventType().getDisplayName(), e4.getMessage(), "2 days ago" }, response.get(4));
		
	}
	
	@Test
	public void testBuildPiechart() {
	
		Kpi kpi = this.getKpi1();
		Double val1 = 45.0;
		Double val2 = 55.0;
		
		List<IEntity> entities = Lists.newArrayList((IEntity) this.getPerson1(), this.getPerson2());
		List<String> colors = Lists.newArrayList("#FFFFFF", "#98EE12");
		
		Map<EntityKey, Number> values = Maps.newHashMap();
		values.put(entities.get(0).getEntityKey(), val1);
		values.put(entities.get(1).getEntityKey(), val2);
		
		KpiResult result = new KpiResult(values);
		
		List<String[]> response = cyfeService.buildPiechart(kpi, entities, result, colors).convertToStringList();
		
		assertEquals(3, response.size());
		assertArrayEquals(new String[] { entities.get(0).getDisplayName(), entities.get(1).getDisplayName() }, response.get(0));
		assertArrayEquals(new String[] { val1.toString(), val2.toString() }, response.get(1));
		assertArrayEquals(new String[] { "Color", colors.get(0), colors.get(1) }, response.get(2));
		
	}
	
	@Test
	public void testBuildCohort() {
		
		Double goal = 50.0;
		Kpi kpi = this.getKpi1();
		List<IEntity> entities = Lists.newArrayList((IEntity) this.getPerson1(), this.getPerson2());
		
		final TimeCoordinate c1 = new TimeCoordinate();
		c1.setValue(5.0);
		c1.setDay(1);
		c1.setMonth(Calendar.JUNE+1);
		c1.setYear(2014);
		
		final TimeCoordinate c2 = new TimeCoordinate();
		c2.setValue(10.0);
		c2.setDay(2);
		c2.setMonth(Calendar.JUNE+1);
		c2.setYear(2014);
		
		final TimeCoordinate c3 = new TimeCoordinate();
		c3.setValue(7.0);
		c3.setDay(1);
		c3.setMonth(Calendar.JUNE+1);
		c3.setYear(2014);
		
		final TimeCoordinate c4 = new TimeCoordinate();
		c4.setValue(12.0);
		c4.setDay(2);
		c4.setMonth(Calendar.JUNE+1);
		c4.setYear(2014);
			
		TimeSerie ts1 = createTimeSerie(entities.get(0), Lists.newArrayList(c1, c2)); 	
		TimeSerie ts2 = createTimeSerie(entities.get(1), Lists.newArrayList(c3, c4)); 				
		List<TimeSerie> timeSeries = Lists.newArrayList(ts1, ts2);
		
		List<String[]> response = cyfeService.buildCohort(kpi, entities, timeSeries, goal).convertToStringList();
		
		assertEquals(3, response.size());
		assertArrayEquals(new String[] { "Date", "Target", entities.get(0).getDisplayName(), entities.get(1).getDisplayName() }, response.get(0));
		assertArrayEquals(new String[] { "20140601", goal.toString(), c1.getValue().toString(), c3.getValue().toString() }, response.get(1));
		assertArrayEquals(new String[] { "20140602", goal.toString(), c2.getValue().toString(), c4.getValue().toString() }, response.get(2));
		
	}
	
}
