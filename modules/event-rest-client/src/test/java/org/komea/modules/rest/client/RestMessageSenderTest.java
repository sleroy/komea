package org.komea.modules.rest.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.messaging.IMessageSender;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class RestMessageSenderTest {

    public static void main(String[] args) {
        final IMessageSender restClient = new RestMessageSender("http://localhost:8080");
        final String jsonEvent = "komeaEvent";
        restClient.sendJsonEvent(jsonEvent);
    }

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void sendMessageTest() {
        final RestMessageSender messageSender = new RestMessageSender("http://localhost:8080");
        messageSender.setDestinationName("myQueue");
        messageSender.setRestTemplate(restTemplate);
        messageSender.sendJsonEvent("komeaEvent1");
        Mockito.verify(restTemplate, Mockito.times(1)).postForObject(
                Mockito.anyString(), Mockito.eq("komeaEvent1"), Mockito.eq(Void.class));
        messageSender.sendJsonEvent("komeaEvent2");
        Mockito.verify(restTemplate, Mockito.times(1)).postForObject(
                Mockito.anyString(), Mockito.eq("komeaEvent2"), Mockito.eq(Void.class));
    }

}
