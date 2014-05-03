
package org.komea.backend.kpi.tests;



import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.komea.event.factory.JenkinsEventsFactory;
import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.filters.IEventFilter;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.cache.guava.GoogleCacheStorage;
import org.komea.eventory.filter.EventFilterBuilder;
import org.komea.eventory.filter.NoEventFilter;
import org.komea.eventory.formula.CountFormula;
import org.komea.eventory.query.CEPQueryBuilder;
import org.komea.eventory.utils.PluginUtils;
import org.komea.product.backend.utils.MapPopulation;
import org.komea.product.cep.filter.ElEventFilter;
import org.komea.product.cep.filter.OnlyEventFilter;
import org.komea.product.cep.tester.CEPQueryTester;
import org.komea.product.database.alert.IEvent;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;



/**
 * Defines a test of a jenkins kpi .
 * http://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch07s02.html
 */
@BenchmarkOptions(
    benchmarkRounds = 200,
    warmupRounds = 30)
public class CEPJenkinsKPITest
{
    
    
    private static IEvent         a1;
    
    
    private static IEvent         a2;
    private static IEvent         a3;
    private static IEvent         a4;
    
    private static CEPQueryTester newTest;
    
    
    
    @BeforeClass
    public static void initialize() {
    
    
        newTest = CEPQueryTester.newTest();
        
        a1 =
                newTest.convertDto(JenkinsEventsFactory.createStartEvent(new Date().getTime(), 1,
                        "SCERTIFY Build", "http://", "SCERTIFY", "BRANCH1"));
        a2 =
                newTest.convertDto(JenkinsEventsFactory.createStartEvent(new Date().getTime(), 1,
                        "SCERTIFY Build", "http://", "KOMEA", "BRANCH1"));
        a3 =
                newTest.convertDto(JenkinsEventsFactory.createStartEvent(new Date().getTime(), 1,
                        "SCERTIFY Build", "http://", "SCERTIFY", "BRANCH1"));
        a4 =
                newTest.convertDto(JenkinsEventsFactory.createStartEvent(new Date().getTime(), 1,
                        "SCERTIFY Build", "http://", "SCERTIFY", "BRANCH1"));
        
    }
    
    
    
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();
    
    
    
    public CEPJenkinsKPITest() {
    
    
        super();
    }
    
    
    @Before
    public void before() {
    
    
        PluginUtils.setCacheStorageFactory(new ICacheStorageFactory()
        {
            
            
            @Override
            public ICacheStorage newCacheStorage(final ICacheConfiguration _arg0) {
            
            
                return new GoogleCacheStorage<Serializable>(_arg0);
            }
        });
    }
    
    
    @Test
    public void testCreation() {
    
    
        CEPQueryBuilder
                .create(new CountFormula())
                .defineFilter(new NoEventFilter(),
                        CacheConfigurationBuilder.create().expirationTime(7, TimeUnit.DAYS).build())
                .build();
        
        // "SELECT COUNT(*) as count FROM Event.win:time(1 month) WHERE eventType.eventKey IN('build_complete', 'build_failed', 'build_interrupted') AND project.name="
        // + "'SCERTIFY'").expectRows(1).hasLineResult("count", 3L).dump()
        
    }
    
    
    @Test
    public void testNumberOfBuildPerMonthWithCountFormula() {
    
    
        final IEventFilter<?> filter =
                EventFilterBuilder.create().chain(new OnlyEventFilter())
                        .chain(new ElEventFilter("project.projectKey=='SCERTIFY'")).build();
        final ICEPQuery<Serializable, Integer> query =
                CEPQueryBuilder
                        .create(new CountFormula())
                        .defineFilter(filter,
                                CacheConfigurationBuilder.expirationTimeCache(30, TimeUnit.DAYS))
                        .build();
        query.notifyEvent(a1);
        query.notifyEvent(a2);
        query.notifyEvent(a3);
        query.notifyEvent(a4);
        Assert.assertEquals(Integer.valueOf(3), query.getResult());
        
        // "SELECT COUNT(*) as count FROM Event.win:time(1 month) WHERE eventType.eventKey IN('build_complete', 'build_failed', 'build_interrupted') AND project.name="
        // + "'SCERTIFY'").expectRows(1).hasLineResult("count", 3L).dump()
        
    }
    
    
    @Test
    public void testNumberOfBuildPerWeek() {
    
    
        final Map<Object, Object> parameters =
                MapPopulation
                        .create()
                        .addEntry("project", "SCERTIFY")
                        .addEntry("expectedEvents",
                                Arrays.asList("build_started", "build_failed", "build_interrupted"))
                        .build();
        final IEventFilter<?> filter =
                EventFilterBuilder
                        .create()
                        .chain(new OnlyEventFilter())
                        .chain(new ElEventFilter(
                                "project.projectKey==#project &&  #expectedEvents.contains(eventType.eventKey)",
                                parameters)).build();
        final ICEPQuery<Serializable, Integer> query =
                CEPQueryBuilder
                        .create(new CountFormula())
                        .defineFilter(
                                filter,
                                CacheConfigurationBuilder.create().expirationTime(7, TimeUnit.DAYS)
                                        .build()).build();
        
        query.notifyEvent(a1);
        query.notifyEvent(a2);
        query.notifyEvent(a3);
        query.notifyEvent(a4);
        Assert.assertEquals(Integer.valueOf(3), query.getResult());
        
        // "SELECT COUNT(*) as count FROM Event.win:time(1 month) WHERE eventType.eventKey IN('build_complete', 'build_failed', 'build_interrupted') AND project.name="
        // + "'SCERTIFY'").expectRows(1).hasLineResult("count", 3L).dump()
        
    }
    
    
    @Test
    public void testNumberOfBuildPerWeekFullJava() {
    
    
        final List<String> eventList =
                Arrays.asList("build_started", "build_failed", "build_interrupted");
        
        final IEventFilter<?> filter =
                EventFilterBuilder.create().chain(new OnlyEventFilter())
                        .chain(new IEventFilter<IEvent>()
                        {
                            
                            
                            @Override
                            public boolean isFiltered(final IEvent _event) {
                            
                            
                                return "SCERTIFY".equals(_event.getProject().getProjectKey())
                                        && eventList.contains(_event.getEventType().getEventKey());
                                
                            }
                        }).build();
        
        final ICEPQuery<Serializable, Integer> query =
                CEPQueryBuilder
                        .create(new CountFormula())
                        .defineFilter(
                                filter,
                                CacheConfigurationBuilder.create().expirationTime(7, TimeUnit.DAYS)
                                        .build()).build();
        
        query.notifyEvent(a1);
        query.notifyEvent(a2);
        query.notifyEvent(a3);
        query.notifyEvent(a4);
        Assert.assertEquals(Integer.valueOf(3), query.getResult());
        
        // "SELECT COUNT(*) as count FROM Event.win:time(1 month) WHERE eventType.eventKey IN('build_complete', 'build_failed', 'build_interrupted') AND project.name="
        // + "'SCERTIFY'").expectRows(1).hasLineResult("count", 3L).dump()
        
    }
    
    
    @Test(
        timeout = 1000 * 5)
    public void testSendingEvents() {
    
    
        final ICEPQuery query =
                CEPQueryBuilder
                        .create(new CountFormula())
                        .defineFilter(
                                new NoEventFilter(),
                                CacheConfigurationBuilder.create().expirationTime(7, TimeUnit.DAYS)
                                        .build()).build();
        for (int i = 0; i < 10000; ++i) {
            query.notifyEvent(a1);
            query.notifyEvent(a2);
            query.notifyEvent(a3);
            query.notifyEvent(a4);
        }
        
        // "SELECT COUNT(*) as count FROM Event.win:time(1 month) WHERE eventType.eventKey IN('build_complete', 'build_failed', 'build_interrupted') AND project.name="
        // + "'SCERTIFY'").expectRows(1).hasLineResult("count", 3L).dump()
        
    }
    
}
