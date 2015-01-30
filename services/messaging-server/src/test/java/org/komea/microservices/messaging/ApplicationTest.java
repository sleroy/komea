package org.komea.microservices.messaging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.events.dto.KomeaEvent;
import org.komea.microservices.messaging.rest.JmsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {

    @Autowired
    private JmsController controller;

    private static KomeaEvent newEvent() {
        final KomeaEvent event = new KomeaEvent();
        event.setEventType("new_commit");
        event.setProvider("GIT");
        return event;
    }

    @Test
    public void contextLoads() {
        controller.pushEvent("komeaQueue", newEvent());
        controller.pushEvent("komeaQueue", newEvent());
    }
}
