
package org.komea.backend.kpi.tests;



import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.utils.MapPopulation;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.cep.api.IEventFilter;
import org.komea.product.cep.cache.CacheConfigurationBuilder;
import org.komea.product.cep.filter.ELFormulaFilter;
import org.komea.product.cep.filter.EventFilterBuilder;
import org.komea.product.cep.filter.NoEventFilter;
import org.komea.product.cep.formula.CountFormula;
import org.komea.product.cep.formula.ElNumericalFormula;
import org.komea.product.cep.query.CEPQueryBuilder;
import org.komea.product.cep.query.CEPQueryTest;
import org.komea.product.database.alert.IEvent;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;



/**
 * Defines a test of a jenkins kpi .
 * http://docs.spring.io/spring/docs/3.0.0.M3/reference/html/ch07s02.html
 */
@BenchmarkOptions(benchmarkRounds = 200, warmupRounds = 30)
public class CEPJenkinsKPITest
{
    
    
    private static IEvent              a1;
    
    
    private static IEvent              a2;
    private static IEvent              a3;
    private static IEvent              a4;
    private static JenkinsEventFactory jenkinsEventFactory;
    private static CEPQueryTest        newTest;
    
    
    
    @BeforeClass
    public static void initialize() {
    
    
        newTest = CEPQueryTester.newTest("bla");
        jenkinsEventFactory = new JenkinsEventFactory();
        a1 = newTest.convertDto(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 908, "SPRINT"));
        a2 = newTest.convertDto(jenkinsEventFactory.sendBuildComplete("KOMEA", 909, "SPRINT"));
        a3 = newTest.convertDto(jenkinsEventFactory.sendBuildFailed("SCERTIFY", 910, "SPRINT"));
        a4 =
                newTest.convertDto(jenkinsEventFactory.sendBuildInterrupted("SCERTIFY", 911,
                        "SPRINT"));
        
    }
    
    
    
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();
    
    
    
    public CEPJenkinsKPITest() {
    
    
        super();
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
    @Ignore
    public void testMTBFPerProject() {
    
    
        // "every Event(eventType.eventKey='build_complete' AND project.name='SCERTIFY') -> Event(eventType.eventKey IN ('build_failed','build_interrupted') AND project.name='SCERTIFY')")
        
        //
        // final IEventFilter eventFilter1 =
        // new ELFormulaFilter(
        // "project.projectKey=='SCERTIFY' && eventType.eventKey='build_complete'");
        // final IEventFilter eventFilter2 =
        // new ELFormulaFilter(
        // "project.projectKey=='SCERTIFY' && eventType.eventKey='build_failed'");
        // final IEventFilter eventFilter3 =
        // new ELFormulaFilter(
        // "project.projectKey=='SCERTIFY' && eventType.eventKey='build_interrupted'");
        // final ICEPQuery query =
        // CEPQueryBuilder
        // .create(new CountFormula())
        // .defineFilterAndTransformer(
        // EventFilterBuilder.create().onlyIEvents(),
        // SequenceTransformer.build(eventFilter1,
        // FilterOperator.or(eventFilter2, eventFilter3)),
        // CacheConfigurationBuilder.create()
        // .expirationTime(30, TimeUnit.DAYS).build()).build();
        //
        // query.notifyEvent(a1);
        // query.notifyEvent(a2);
        // query.notifyEvent(a3);
        // query.notifyEvent(a4);
        // Assert.assertEquals(Double.valueOf(1.0d), query.getResult().asNumber());
    }
    
    
    @Test
    public void testNumberOfBuildPerMonth() {
    
    
        final IEventFilter<?> filter =
                EventFilterBuilder.create().onlyIEvents()
                        .chain(new ELFormulaFilter("project.projectKey=='SCERTIFY'")).build();
        final ICEPQuery query =
                CEPQueryBuilder
                        .create(new ElNumericalFormula("previous + 1"))
                        .defineFilter(
                                filter,
                                CacheConfigurationBuilder.create()
                                        .expirationTime(30, TimeUnit.DAYS).build()).build();
        
        query.notifyEvent(a1);
        query.notifyEvent(a2);
        query.notifyEvent(a3);
        query.notifyEvent(a4);
        Assert.assertEquals(Double.valueOf(3.0d), query.getResult().asNumber());
        
        // "SELECT COUNT(*) as count FROM Event.win:time(1 month) WHERE eventType.eventKey IN('build_complete', 'build_failed', 'build_interrupted') AND project.name="
        // + "'SCERTIFY'").expectRows(1).hasLineResult("count", 3L).dump()
        
    }
    
    
    @Test
    public void testNumberOfBuildPerMonthWithCountFormula() {
    
    
        final IEventFilter<?> filter =
                EventFilterBuilder.create().onlyIEvents()
                        .chain(new ELFormulaFilter("project.projectKey=='SCERTIFY'")).build();
        final ICEPQuery query =
                CEPQueryBuilder
                        .create(new CountFormula())
                        .defineFilter(filter,
                                CacheConfigurationBuilder.expirationTimeCache(30, TimeUnit.DAYS))
                        .build();
        
        query.notifyEvent(a1);
        query.notifyEvent(a2);
        query.notifyEvent(a3);
        query.notifyEvent(a4);
        Assert.assertEquals(Integer.valueOf(3), query.getResult().asNumber());
        
        // "SELECT COUNT(*) as count FROM Event.win:time(1 month) WHERE eventType.eventKey IN('build_complete', 'build_failed', 'build_interrupted') AND project.name="
        // + "'SCERTIFY'").expectRows(1).hasLineResult("count", 3L).dump()
        
    }
    
    
    @Test
    public void testNumberOfBuildPerWeek() {
    
    
        final Map<Object, Object> parameters =
                MapPopulation
                        .create()
                        .addEntry("project", "SCERTIFY")
                        .addEntry(
                                "expectedEvents",
                                Arrays.asList("build_complete", "build_failed", "build_interrupted"))
                        .build();
        final IEventFilter<?> filter =
                EventFilterBuilder
                        .create()
                        .onlyIEvents()
                        .chain(new ELFormulaFilter(
                                "project.projectKey==#project &&  #expectedEvents.contains(eventType.eventKey)",
                                parameters)).build();
        final ICEPQuery query =
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
        Assert.assertEquals(Integer.valueOf(3), query.getResult().asNumber());
        
        // "SELECT COUNT(*) as count FROM Event.win:time(1 month) WHERE eventType.eventKey IN('build_complete', 'build_failed', 'build_interrupted') AND project.name="
        // + "'SCERTIFY'").expectRows(1).hasLineResult("count", 3L).dump()
        
    }
    
    
    @Test
    public void testNumberOfBuildPerWeekFullJava() {
    
    
        final List<String> eventList =
                Arrays.asList("build_complete", "build_failed", "build_interrupted");
        
        final IEventFilter<?> filter =
                EventFilterBuilder.create().onlyIEvents().chain(new IEventFilter<IEvent>()
                {
                    
                    
                    @Override
                    public boolean isFiltered(final IEvent _event) {
                    
                    
                        return "SCERTIFY".equals(_event.getProject().getProjectKey())
                                && eventList.contains(_event.getEventType().getEventKey());
                        
                    }
                }).build();
        
        final ICEPQuery query =
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
        Assert.assertEquals(Integer.valueOf(3), query.getResult().asNumber());
        
        // "SELECT COUNT(*) as count FROM Event.win:time(1 month) WHERE eventType.eventKey IN('build_complete', 'build_failed', 'build_interrupted') AND project.name="
        // + "'SCERTIFY'").expectRows(1).hasLineResult("count", 3L).dump()
        
    }
    
    
    @Test
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
