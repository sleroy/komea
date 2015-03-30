package org.komea.modules.rest.client;

import org.komea.microservices.events.messaging.service.RestMessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.messaging.IMessageSender;
import org.komea.event.model.impl.KomeaEvent;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class RestMessageSenderTest {

    public static void main(String[] args) {
        final IMessageSender restClient = new RestMessageSender("http://localhost:8080");
        restClient.pushEvent(newFlatEvent());
    }

    private static KomeaEvent newFlatEvent() {
        final KomeaEvent flatEvent = new KomeaEvent();
        flatEvent.setEventType("new_commit");
        flatEvent.setProvider("GIT");
        return flatEvent;
    }

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void pushFlatEventTest() {
        final RestMessageSender messageSender = new RestMessageSender("http://localhost:8080");
        messageSender.setDestinationName("myQueue");
        messageSender.setRestTemplate(restTemplate);
        KomeaEvent flatEvent = newFlatEvent();
        messageSender.pushEvent(flatEvent);
        Mockito.verify(restTemplate, Mockito.times(1)).postForObject(
                Mockito.anyString(), Mockito.eq(flatEvent), Mockito.eq(Void.class));
        flatEvent = newFlatEvent();
        messageSender.pushEvent(flatEvent);
        Mockito.verify(restTemplate, Mockito.times(1)).postForObject(
                Mockito.anyString(), Mockito.eq(flatEvent), Mockito.eq(Void.class));
        Mockito.verify(restTemplate, Mockito.times(2)).postForObject(Mockito.anyString(), Mockito.any(KomeaEvent.class), Mockito.eq(Void.class));
    }

}
