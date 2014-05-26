package org.komea.product.plugins.kpi.standard.jenkins;

import org.junit.Test;
import org.komea.event.factory.JenkinsEventsFactory;
import org.komea.product.cep.tester.CEPQueryTester;
import org.komea.product.database.dto.EventSimpleDto;

public class BuildPerDayTest {

    @Test
    public final void testBuildPerDay() throws Exception {

        final BuildPerDay buildPerDay = new BuildPerDay();
        final CEPQueryTester newTest = CEPQueryTester.newTest();

        final EventSimpleDto sendBuildStarted
                = JenkinsEventsFactory.sendBuildComplete("SCERTIFY", 1, "TRUC");

        newTest.withQuery(buildPerDay).sendEvent(sendBuildStarted);

        newTest.withQuery(buildPerDay).sendEvent(
                JenkinsEventsFactory.sendBuildComplete("KOMEA", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                JenkinsEventsFactory.sendBuildComplete("ALBRAND", 1, "TRUC"));
        newTest.withQuery(buildPerDay).sendEvent(
                JenkinsEventsFactory.sendBuildComplete("KOMEA", 1, "TRUC"));
        newTest.dump().hasResults(new Object[][]{
            {
                newTest.getMockProject().get("SCERTIFY").getEntityKey(),
                1},
            {
                newTest.getMockProject().get("KOMEA").getEntityKey(),
                2},
            {
                newTest.getMockProject().get("ALBRAND").getEntityKey(),
                1}}
        ).runTest();

    }
}
