package org.komea.modules.messaging.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.messaging.IMessageSender;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@RunWith(MockitoJUnitRunner.class)
public class JmsMessageSenderTest {

    public static void main(String[] args) {
        final IMessageSender messageSender = new JmsMessageSender(
                "tcp://localhost:61616", IMessageSender.DEFAULT_DESTINATION_NAME);
        final String jsonEvent = "komea-event";
        messageSender.sendJsonEvent(jsonEvent);
    }

    @Mock
    private JmsTemplate jmsTemplate;

    @Test
    public void sendMessageTest() {
        final JmsMessageSender messageSender = new JmsMessageSender("tcp://localhost:61616");
        messageSender.setDestinationName("myQueue");
        messageSender.setJmsTemplate(jmsTemplate);
        messageSender.sendJsonEvent("komeaEvent1");
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(Mockito.any(MessageCreator.class));
        messageSender.sendJsonEvent("komeaEvent2");
        Mockito.verify(jmsTemplate, Mockito.times(2)).send(Mockito.any(MessageCreator.class));
    }

}
