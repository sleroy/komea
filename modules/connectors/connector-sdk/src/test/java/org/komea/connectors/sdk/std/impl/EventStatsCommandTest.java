package org.komea.connectors.sdk.std.impl;

import javax.ws.rs.ProcessingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.microservices.events.Application;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=9991")
public class EventStatsCommandTest {

	@Test
	public void testRun() throws Exception {
		final EventStatsCommand eventStatsCommand = new EventStatsCommand("new_bug", "_new_bug2");
		eventStatsCommand.setServerURL("http://localhost:9991");
		eventStatsCommand.run();

	}

	@Test(expected = ProcessingException.class)
	public void testRunFail() throws Exception {
		// WHEN I LAUNCH THE ACTION ON A WRONG URL
		final EventStatsCommand eventStatsCommand = new EventStatsCommand("new_bug");
		eventStatsCommand.setServerURL("http://localhost:9992");
		eventStatsCommand.run();

	}
}
