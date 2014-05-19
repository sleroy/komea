package org.komea.product.plugins.kpi.standard;

import org.junit.Test;
import org.komea.event.factory.BugZillaEventFactory;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.cep.tester.CEPQueryTester;

public class EventsCountKpiTest {

	@Test
	public final void test() throws Exception {

		final EventsCountKpi kpi = new EventsCountKpi("closed_status_bugs", BackupDelay.DAY);

		final CEPQueryTester newTest = CEPQueryTester.newTest();
		final BugZillaEventFactory eventFactory = new BugZillaEventFactory();

		newTest.withQuery(kpi).sendEvent(eventFactory.sendEvent("SCERTIFY", 12, "closed_status_bugs"));
		newTest.withQuery(kpi).sendEvent(eventFactory.sendEvent("SCERTIFY", 20, "closed_status_bugs"));
		newTest.withQuery(kpi).sendEvent(eventFactory.sendEvent("KOMEA", 50, "closed_status_bugs"));
		newTest.withQuery(kpi).sendEvent(eventFactory.sendEvent("KOMEA", 30, "ncloc"));
		newTest.withQuery(kpi).sendEvent(eventFactory.sendEvent("CIFLOW", 20, "ncloc"));

		newTest.dump()
		        .hasResults(
		                new Object[][] { { newTest.getMockProject().get("SCERTIFY").getEntityKey(), 2 },
		                        { newTest.getMockProject().get("KOMEA").getEntityKey(), 1 } }).runTest();

	}
}
