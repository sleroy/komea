package org.komea.product.web.cyfe.rest.service;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Person;
import org.komea.product.model.timeserie.PeriodTimeSerieOptions;
import org.komea.product.model.timeserie.TimeScale;
import org.komea.product.model.timeserie.TimeSerieOptions;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class StatsServiceTest extends AbstractSpringWebIntegrationTestCase {

	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@Autowired
	@InjectMocks
	private IStatsService service;
	
	@Mock
	private IStatisticsAPI statisticsAPI;
	
	@Before
	public void setUp() {
	
	    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	    MockitoAnnotations.initMocks(this); 

	}
	
	private Kpi getKpi1() {
		Kpi kpi = new Kpi();
		kpi.setId(35);
		kpi.setKpiKey("COMMIT_MESSAGE_LENGTH");
		kpi.setName("COMMIT MESSAGE LENGTH");
		kpi.setEntityType(EntityType.PERSON);
		return kpi;
	}
	
	private Kpi getKpi2() {
		Kpi kpi2 = new Kpi();
		kpi2.setId(36);
		kpi2.setKpiKey("ADDED_LINES");
		kpi2.setName("Added lines");
		kpi2.setEntityType(EntityType.PERSON);
		return kpi2;
	}
	
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
	
	private Person getPerson3() {
		Person entity = new Person();
		entity.setId(3);
		entity.setFirstName("Sébastien");
		entity.setLastName("Carreau");
		entity.setLogin("scarreau");
		return entity;
	}
	
	@Test
	public void testEvaluateKpiValue() {
		
		Kpi kpi = getKpi1();
		IEntity entity = getPerson1();
		TimeScale timescale = TimeScale.PER_MONTH;
		
		TimeSerieOptions options = new TimeSerieOptions(kpi);
	    options.setTimeScale(timescale);
	    
	    service.evaluateKpiValue(kpi, entity, timescale);
	    Mockito.verify(statisticsAPI).evaluateKpiValue(options, entity.getEntityKey());
		
	}
	
	@Test
	public void testEvaluateKpiValueWithDate() throws Exception {
		
		Kpi kpi = getKpi1();
		IEntity entity = getPerson1();
		TimeScale timescale = TimeScale.PER_MONTH;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse("2014-01-15");
		
		PeriodTimeSerieOptions options = new PeriodTimeSerieOptions(kpi);
	    options.setTimeScale(timescale);
	    options.setToPeriod(new DateTime(formatter.parse("2014-02-01")));
	    options.setFromPeriod(new DateTime(formatter.parse("2014-01-01")));
	    
		service.evaluateKpiValueWithDate(kpi, entity, timescale, date);		
		Mockito.verify(statisticsAPI).evaluateKpiValueOnPeriod(options, entity.getEntityKey());		
		
	}
	
	@Test
	public void testEvaluateKpiValueWithPerYearTimescale() throws Exception {
		
		Kpi kpi = getKpi1();
		IEntity entity = getPerson1();
		TimeScale timescale = TimeScale.PER_YEAR;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse("2014-09-15");
		
		PeriodTimeSerieOptions options = new PeriodTimeSerieOptions(kpi);
	    options.setTimeScale(timescale);
	    options.setToPeriod(new DateTime(formatter.parse("2014-12-31")));
	    options.setFromPeriod(new DateTime(formatter.parse("2014-01-01")));
	    
		service.evaluateKpiValueWithDate(kpi, entity, timescale, date);		
		Mockito.verify(statisticsAPI).evaluateKpiValueOnPeriod(options, entity.getEntityKey());		
		
	}
	
	@Test
	public void testEvaluateKpiValues() throws Exception {
		
		Kpi kpi = getKpi1();
		List<IEntity> entities = Lists.newArrayList((IEntity) getPerson1(), getPerson2());
		TimeScale timescale = TimeScale.PER_DAY;
		
		TimeSerieOptions options = new TimeSerieOptions(kpi);
        options.setTimeScale(timescale);
		
        Map<EntityKey, Number> expectedMap = Maps.newHashMap();
        expectedMap.put(entities.get(0).getEntityKey(), 10.0);
        expectedMap.put(entities.get(1).getEntityKey(), 15.0);
        
        Map<EntityKey, Number> returnedMap = Maps.newHashMap();
        returnedMap.putAll(expectedMap);
        returnedMap.put(getPerson3().getEntityKey(), 20.0);
        
        Mockito.when(statisticsAPI.evaluateKpiValues(options)).thenReturn(new KpiResult(returnedMap));
        
		KpiResult result = service.evaluateKpiValues(kpi, entities, timescale);
		
		Mockito.verify(statisticsAPI).evaluateKpiValues(options);
		
		assertEquals(expectedMap, result.getMap());
		
	}
	
	@Test
	public void testEvaluateKpiValuesWithDate() throws Exception {
		
		Kpi kpi = getKpi1();
		List<IEntity> entities = Lists.newArrayList((IEntity) getPerson1(), getPerson2());
		TimeScale timescale = TimeScale.PER_MONTH;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse("2014-01-15");
		
		PeriodTimeSerieOptions options = new PeriodTimeSerieOptions(kpi);
        options.setTimeScale(timescale);
        options.setToPeriod(new DateTime(formatter.parse("2014-02-01")));
	    options.setFromPeriod(new DateTime(formatter.parse("2014-01-01")));
		
        Map<EntityKey, Number> expectedMap = Maps.newHashMap();
        expectedMap.put(entities.get(0).getEntityKey(), 10.0);
        expectedMap.put(entities.get(1).getEntityKey(), 15.0);
        
        Map<EntityKey, Number> returnedMap = Maps.newHashMap();
        returnedMap.putAll(expectedMap);
        returnedMap.put(getPerson3().getEntityKey(), 20.0);
        
        Mockito.when(statisticsAPI.evaluateKpiValuesOnPeriod(options)).thenReturn(new KpiResult(returnedMap));
        
		KpiResult result = service.evaluateKpiValues(kpi, entities, timescale, date);
		
		Mockito.verify(statisticsAPI).evaluateKpiValuesOnPeriod(options);
		
		assertEquals(expectedMap, result.getMap());
		
	}
	
	@Test
	public void testEvaluateMultipleKpisValues() throws Exception {
		
		List<Kpi> kpis = Lists.newArrayList(getKpi1(), getKpi2());		
		List<IEntity> entities = Lists.newArrayList((IEntity) getPerson1(), getPerson2());
		TimeScale timescale = TimeScale.PER_MONTH;
		
		Map<Kpi, KpiResult> result = service.evaluateKpiValues(kpis, entities, timescale, null);
		
		Mockito.verify(statisticsAPI, Mockito.times(2)).evaluateKpiValues(Matchers.any(TimeSerieOptions.class));		
		
	}
	
	@Test
	public void testEvaluateMultipleKpisValuesWithDate() throws Exception {
		
		List<Kpi> kpis = Lists.newArrayList(getKpi1(), getKpi2());		
		List<IEntity> entities = Lists.newArrayList((IEntity) getPerson1(), getPerson2());
		TimeScale timescale = TimeScale.PER_MONTH;
		Date date = DateTime.now().toDate();
		
		service.evaluateKpiValues(kpis, entities, timescale, date);
		
		Mockito.verify(statisticsAPI, Mockito.times(2)).evaluateKpiValuesOnPeriod(Matchers.any(PeriodTimeSerieOptions.class));
		
	}
	
	@Test
	public void testBuildTimeSerie() throws Exception {
		
		Kpi kpi = getKpi1();
		IEntity entity = getPerson1();
		TimeScale timescale = TimeScale.PER_DAY;
		
		TimeSerieOptions options = new TimeSerieOptions(kpi);
        options.setTimeScale(timescale);
		
		service.buildTimeSerie(kpi, entity, timescale, null);
		
		Mockito.verify(statisticsAPI).buildTimeSeries(options, entity.getEntityKey());
		
	}
	
	@Test
	public void testBuildTimeSerieWithDate() throws Exception {
		
		Kpi kpi = getKpi1();
		IEntity entity = getPerson1();
		TimeScale timescale = TimeScale.PER_MONTH;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse("2014-01-15");
		
		PeriodTimeSerieOptions options = new PeriodTimeSerieOptions(kpi);
        options.setTimeScale(timescale);
        options.setToPeriod(DateTime.now());
	    options.setFromPeriod(new DateTime(date));
		
		service.buildTimeSerie(kpi, entity, timescale, date);
		
		Mockito.verify(statisticsAPI).buildPeriodTimeSeries(options, entity.getEntityKey());
		
	}
	
	@Test
	public void testBuildTimeSeries() throws Exception {
		
		Kpi kpi = getKpi1();
		List<IEntity> entities = Lists.newArrayList((IEntity) getPerson1(), getPerson2());
		TimeScale timescale = TimeScale.PER_MONTH;
		
		service.buildTimeSeries(kpi, entities, timescale, null);
		
		Mockito.verify(statisticsAPI, Mockito.times(2)).buildTimeSeries(Matchers.any(TimeSerieOptions.class), Matchers.any(EntityKey.class));
		
	}
	
	@Test
	public void testBuildTimeSeriesWithDate() throws Exception {
		
		Kpi kpi = getKpi1();
		List<IEntity> entities = Lists.newArrayList((IEntity) getPerson1(), getPerson2());
		TimeScale timescale = TimeScale.PER_MONTH;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse("2014-01-15");
		
		service.buildTimeSeries(kpi, entities, timescale, date);
		
		Mockito.verify(statisticsAPI, Mockito.times(2)).buildPeriodTimeSeries(Matchers.any(PeriodTimeSerieOptions.class), Matchers.any(EntityKey.class));
		
	}
	
}
