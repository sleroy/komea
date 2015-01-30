package org.komea.modules.messaging.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.events.dto.KomeaEvent;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@RunWith(MockitoJUnitRunner.class)
public class JmsMessageSenderTest {

    public static void main(String[] args) {
        final JmsMessageSender messageSender = new JmsMessageSender();
        messageSender.setBrokerUrl("tcp://localhost:61616");
        messageSender.pushEvent(newFlatEvent());
    }

    private static KomeaEvent newFlatEvent() {
        final KomeaEvent flatEvent = new KomeaEvent();
        flatEvent.setEventType("new_commit");
        flatEvent.setProvider("GIT");
        return flatEvent;
    }

    @Mock
    private JmsTemplate jmsTemplate;

    @Test
    public void pushFlatEventTest() {
        final JmsMessageSender messageSender = new JmsMessageSender();
        messageSender.setBrokerUrl("tcp://localhost:61616");
        messageSender.setDestinationName("myQueue");
        messageSender.setJmsTemplate(jmsTemplate);
        messageSender.pushEvent(newFlatEvent());
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(Mockito.any(MessageCreator.class));
        messageSender.pushEvent(newFlatEvent());
        Mockito.verify(jmsTemplate, Mockito.times(2)).send(Mockito.any(MessageCreator.class));
    }

}
