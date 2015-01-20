package org.komea.modules.messaging.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.messaging.IMessageSender;
import org.komea.event.model.beans.FlatEvent;
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
        messageSender.pushFlatEvent(newFlatEvent());
    }

    private static FlatEvent newFlatEvent() {
        final FlatEvent flatEvent = new FlatEvent();
        flatEvent.setEventType("new_commit");
        flatEvent.setProvider("GIT");
        return flatEvent;
    }

    @Mock
    private JmsTemplate jmsTemplate;

    @Test
    public void pushFlatEventTest() {
        final JmsMessageSender messageSender = new JmsMessageSender("tcp://localhost:61616");
        messageSender.setDestinationName("myQueue");
        messageSender.setJmsTemplate(jmsTemplate);
        messageSender.pushFlatEvent(newFlatEvent());
        Mockito.verify(jmsTemplate, Mockito.times(1)).send(Mockito.any(MessageCreator.class));
        messageSender.pushFlatEvent(newFlatEvent());
        Mockito.verify(jmsTemplate, Mockito.times(2)).send(Mockito.any(MessageCreator.class));
    }

}
