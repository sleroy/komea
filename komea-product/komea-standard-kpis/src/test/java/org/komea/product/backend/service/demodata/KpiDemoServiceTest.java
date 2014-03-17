/**
 *
 */

package org.komea.product.backend.service.demodata;



/**
 * @author sleroy
 */
public class KpiDemoServiceTest
{
    //
    // private static final JenkinsEventFactory jenkinsEventFactory = new JenkinsEventFactory();
    //
    // private static final EsperQueryTester newTest = EsperQueryTester
    // .newTest("BUILDER");
    //
    // private static final SonarEventFactory sonarEventFactory = new SonarEventFactory();
    // @Autowired
    // private IEventEngineService engine;
    //
    // @Autowired
    // private KpiDemoService kpiDemoService;
    //
    // @Autowired
    // private IKPIService kpiService;
    //
    // @Before
    // public void setupBefore() {
    //
    // engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildStarted("SYSTEM", 1.0,
    // "dev")));
    // engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildComplete("SYSTEM", 1.0,
    // "dev")));
    // engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildStarted("SYSTEM", 1.0,
    // "dev")));
    // engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildComplete("SYSTEM", 1.0,
    // "dev")));
    // engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildStarted("SCERTIFY", 1.0,
    // "dev")));
    // engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 1.0,
    // "dev")));
    // engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildStarted("SCERTIFY", 1.0,
    // "dev")));
    // engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildFailed("SCERTIFY", 1.0,
    // "dev")));
    // engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildStarted("SCERTIFY", 1.0,
    // "dev")));
    // engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildFailed("SCERTIFY", 1.0,
    // "dev")));
    // engine.sendEvent(newTest.convertDto(sonarEventFactory.sendMetricValue("SCERTIFY", 12,
    // "line_coverage")));
    //
    // }
    //
    // /**
    // * Test method for
    // * {@link org.komea.product.backend.service.demodata.KpiDemoService#numberSuccessBuildPerWeek()}.
    // *
    // * @throws KPINotFoundException
    // */
    // @Test
    // public final void testActualCoverage() throws KPINotFoundException {
    //
    // final Kpi kpi = kpiDemoService.actualLineCoverage();
    // final KPIValueTable<Project> valueTable = kpiService.getRealTimeValues(KpiKey.ofKpi(kpi));
    // valueTable.dump();
    // kpiService.storeValueInHistory(KpiKey.ofKpi(kpi));
    // Assert.assertEquals(1, valueTable.getNumberOfRecords());
    // }
    //
    // /**
    // * Test method for
    // * {@link org.komea.product.backend.service.demodata.KpiDemoService#numberBuildPerDay()}.
    // *
    // * @throws KPINotFoundException
    // */
    // @Test
    // public final void testNumberBuildPerDay() throws KPINotFoundException {
    //
    // final Kpi kpi = kpiDemoService.numberBuildPerDay();
    // final KPIValueTable<Project> valueTable = kpiService.getRealTimeValues(KpiKey.ofKpi(kpi));
    // valueTable.dump();
    // kpiService.storeValueInHistory(KpiKey.ofKpi(kpi));
    //
    // Assert.assertEquals(2, valueTable.getNumberOfRecords());
    //
    // }
    //
    // /**
    // * Test method for
    // * {@link org.komea.product.backend.service.demodata.KpiDemoService#numberBuildPerMonth()}.
    // *
    // * @throws KPINotFoundException
    // */
    // @Test
    // public final void testNumberBuildPerMonth() throws KPINotFoundException {
    //
    // final Kpi kpi = kpiDemoService.numberBuildPerMonth();
    // final KPIValueTable<Project> valueTable = kpiService.getRealTimeValues(KpiKey.ofKpi(kpi));
    // valueTable.dump();
    // kpiService.storeValueInHistory(KpiKey.ofKpi(kpi));
    // Assert.assertEquals(2, valueTable.getNumberOfRecords());
    // }
    //
    // /**
    // * Test method for
    // * {@link org.komea.product.backend.service.demodata.KpiDemoService#numberSuccessBuildPerDay()}.
    // *
    // * @throws KPINotFoundException
    // */
    // @Test
    // public final void testNumberSuccessBuildPerDay() throws KPINotFoundException {
    //
    // final Kpi kpi = kpiDemoService.numberSuccessBuildPerDay();
    // final KPIValueTable<Project> valueTable = kpiService.getRealTimeValues(KpiKey.ofKpi(kpi));
    // valueTable.dump();
    // kpiService.storeValueInHistory(KpiKey.ofKpi(kpi));
    // Assert.assertEquals(2, valueTable.getNumberOfRecords());
    // }
    //
    // /**
    // * Test method for
    // * {@link org.komea.product.backend.service.demodata.KpiDemoService#numberSuccessBuildPerWeek()}.
    // *
    // * @throws KPINotFoundException
    // */
    // @Test
    // public final void testNumberSuccessBuildPerWeek() throws KPINotFoundException {
    //
    // final Kpi kpi = kpiDemoService.numberSuccessBuildPerWeek();
    // final KPIValueTable<Project> valueTable = kpiService.getRealTimeValues(KpiKey.ofKpi(kpi));
    // valueTable.dump();
    // kpiService.storeValueInHistory(KpiKey.ofKpi(kpi));
    // Assert.assertEquals(2, valueTable.getNumberOfRecords());
    // }
    //
    // /**
    // * Test method for
    // * {@link org.komea.product.backend.service.demodata.KpiDemoService#numberSuccessBuildPerWeek()}.
    // *
    // * @throws KPINotFoundException
    // */
    // @Test
    // public final void testSuccessRateBuildPerWeek() throws KPINotFoundException {
    //
    // final Project mockProject = newTest.getMockProject("SCERTIFY");
    // final Double successScertify
    // = kpiService.getRealTimeValues(
    // KpiKey.ofKpi(kpiDemoService.numberSuccessBuildPerDay())).getValueOfEntity(
    // mockProject);
    // final Double nbBuildScertify
    // = kpiService.getRealTimeValues(KpiKey.ofKpi(kpiDemoService.numberBuildPerDay()))
    // .getValueOfEntity(mockProject);
    //
    // final Kpi kpi = kpiDemoService.successRateJenkinsPerWeekKpi();
    // final KPIValueTable<Project> valueTable = kpiService.getRealTimeValues(KpiKey.ofKpi(kpi));
    // valueTable.dump();
    // kpiService.storeValueInHistory(KpiKey.ofKpi(kpi));
    // Assert.assertEquals(100 * successScertify / nbBuildScertify,
    // valueTable.getValueOfEntity(mockProject), 0.1d);
    // }
    
}
