package org.komea.microservices.messaging;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.microservices.messaging.Application;
import org.komea.microservices.messaging.rest.JmsController;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTest {

    @Test
    public void contextLoads() {
        final JmsController controller = new JmsController();
        controller.sendJsonEvent("komeaQueue", "komeaEvent1");
        controller.sendJsonEvent("komeaQueue", "komeaEvent2");
    }
}
