package org.komea.microservices.messaging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.model.beans.FlatEvent;
import org.komea.microservices.messaging.rest.JmsController;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {

    private static FlatEvent newFlatEvent() {
        final FlatEvent flatEvent = new FlatEvent();
        flatEvent.setEventType("new_commit");
        flatEvent.setProvider("GIT");
        return flatEvent;
    }

    @Test
    public void contextLoads() {
        final JmsController controller = new JmsController();
        controller.pushFlatEvent("komeaQueue", newFlatEvent());
        controller.pushFlatEvent("komeaQueue", newFlatEvent());
    }
}
