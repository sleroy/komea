
package org.komea.backend.kpi.tests;



import org.junit.Ignore;
import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.esper.test.EsperQueryTester;



/**
 */
public class JenkinsKPITest
{
    
    
    public JenkinsKPITest() {
    
    
        super();
    }
    
    
    @Test
    @Ignore("Not finished")
    public void testMTBFPerProject() {
    
    
        final JenkinsEventFactory jenkinsEventFactory = new JenkinsEventFactory();
        // Last
        EsperQueryTester
                .newTest("KPI_NUMBER_RELEASES_PER_WEEK")
                .withQuery(
                        "every Event(eventType.eventKey='build_complete' AND project.name='SCERTIFY') -> Event(eventType.eventKey IN ('build_failed','build_interrupted') AND project.name='SCERTIFY')")
                .expectRows(1).hasLineResult("count", 1L).dump()
                .sendEvent(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 908, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildComplete("KOMEA", 909, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildFailed("SCERTIFY", 908, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildInterrupted("SCERTIFY", 908, "SPRINT"))
                .runTest();
        
    }
    
    
    @Test
    public void testNumberOfBuildPerMonth() {
    
    
        final JenkinsEventFactory jenkinsEventFactory = new JenkinsEventFactory();
        EsperQueryTester
                .newTest("KPI_NUMBER_RELEASES_PER_MONTH")
                .withQuery(
                        "SELECT COUNT(*) as count FROM Event.win:time(1 month) WHERE eventType.eventKey IN('build_complete', 'build_failed', 'build_interrupted') AND project.name="
                                + "'SCERTIFY'").expectRows(1).hasLineResult("count", 3L).dump()
                .sendEvent(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 908, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildComplete("KOMEA", 909, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildFailed("SCERTIFY", 908, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildInterrupted("SCERTIFY", 908, "SPRINT"))
                .runTest();
        
    }
    
    
    @Test
    public void testNumberOfBuildPerWeek() {
    
    
        final JenkinsEventFactory jenkinsEventFactory = new JenkinsEventFactory();
        EsperQueryTester
                .newTest("KPI_NUMBER_RELEASES_PER_WEEK")
                .withQuery(
                        "SELECT COUNT(*) as count FROM Event.win:time(1 week) WHERE eventType.eventKey IN('build_complete', 'build_failed', 'build_interrupted') AND project.name="
                                + "'SCERTIFY'").expectRows(1).hasLineResult("count", 3L).dump()
                .sendEvent(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 908, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildComplete("KOMEA", 909, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildFailed("SCERTIFY", 908, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildInterrupted("SCERTIFY", 908, "SPRINT"))
                .runTest();
        
    }
    
    
    @Test
    public void testNumberOfReleasesPerMonth() {
    
    
        final JenkinsEventFactory jenkinsEventFactory = new JenkinsEventFactory();
        EsperQueryTester
                .newTest("KPI_NUMBER_RELEASES_PER_MONTH")
                .withQuery(
                        "SELECT COUNT(*) as count FROM Event.win:time(1 month) WHERE eventType.eventKey='build_complete' AND project.name="
                                + "'SCERTIFY'").expectRows(1).hasLineResult("count", 1L).dump()
                .sendEvent(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 908, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildComplete("KOMEA", 909, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildFailed("SCERTIFY", 908, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildInterrupted("SCERTIFY", 908, "SPRINT"))
                .runTest();
    }
    
    
    @Test
    public void testNumberOfReleasesPerWeek() {
    
    
        final JenkinsEventFactory jenkinsEventFactory = new JenkinsEventFactory();
        EsperQueryTester
                .newTest("KPI_NUMBER_RELEASES_PER_WEEK")
                .withQuery(
                        "SELECT COUNT(*) as count FROM Event.win:time(1 week) WHERE eventType.eventKey='build_complete' AND project.name="
                                + "'SCERTIFY'").expectRows(1).hasLineResult("count", 1L).dump()
                .sendEvent(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 908, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildComplete("KOMEA", 909, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildFailed("SCERTIFY", 908, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildInterrupted("SCERTIFY", 908, "SPRINT"))
                .runTest();
        
    }
    
    
    @Test
    public void testNumberOfReleasesPerWeekKpi() {
    
    
        final JenkinsEventFactory jenkinsEventFactory = new JenkinsEventFactory();
        final EsperQueryTester newTest = EsperQueryTester.newTest("KPI_NUMBER_RELEASES_PER_WEEK");
        newTest.withQuery(
                "SELECT project, COUNT(*) as count FROM Event.win:time(1 week) WHERE eventType.eventKey='build_complete' GROUP BY project")
                .dump().sendEvent(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 908, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildComplete("KOMEA", 909, "SPRINT"))
                .sendEvent(jenkinsEventFactory.sendBuildFailed("SCERTIFY", 908, "SPRINT")).dump()
                .sendEvent(jenkinsEventFactory.sendBuildInterrupted("SCERTIFY", 908, "SPRINT"))
                .hasResults(new Object[][] {
                        {
                                newTest.getMockProject("SCERTIFY"), 1L }, {
                                newTest.getMockProject("KOMEA"), 1L } }).runTest();
        
    }
}
