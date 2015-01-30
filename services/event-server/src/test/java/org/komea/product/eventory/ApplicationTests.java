package org.komea.product.eventory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.events.dto.KomeaEvent;
import org.komea.events.Application;
import org.komea.events.service.IEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=" + ApplicationTests.PORT)
public class ApplicationTests {

    public static final int PORT = 9991;

    @Autowired
    private IEventsService eventStorage;

    private static final String EVENT_TYPE = "start";

    @Test
    public void contextLoads() {

        final KomeaEvent simpleEventDto = new KomeaEvent();
        simpleEventDto.setEventType(EVENT_TYPE);
        simpleEventDto.setProvider("jenkins");
        this.eventStorage.declareEventType(EVENT_TYPE);
        this.eventStorage.storeEvent(simpleEventDto);

    }
}
