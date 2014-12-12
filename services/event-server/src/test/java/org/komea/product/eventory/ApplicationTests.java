package org.komea.product.eventory;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.model.beans.BasicEvent;
import org.komea.event.query.IEventQueryManager;
import org.komea.event.storage.IEventStorage;
import org.komea.microservices.events.Application;
import org.komea.microservices.events.sql.api.ISqlQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {

	@Autowired
	private IEventStorage	    eventStorage;

	@Autowired
	private ISqlQueryRepository	sqlQueryRepository;

	@Autowired
	private IEventQueryManager	eventQueryManager;

	@Test
	public void contextLoads() {

		final BasicEvent simpleEventDto = new BasicEvent();
		simpleEventDto.setEventType("start");
		simpleEventDto.setProvider("jenkins");

		this.eventStorage.storeBasicEvent(simpleEventDto);
		assertEquals(1, this.eventQueryManager.countEventsOfType("start"));

	}
}
