package org.komea.modules.messaging.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.messaging.IMessageSender;
import org.komea.event.model.impl.KomeaEvent;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

@RunWith(MockitoJUnitRunner.class)
public class JmsMessageSenderTest {

    public static void main(final String[] args) {
        final IMessageSender messageSender = new JmsMessageSender("tcp://localhost:61616");
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
        final JmsMessageSender messageSender = new JmsMessageSender("tcp://localhost:61616");
        messageSender.setDestinationName("myQueue");
        messageSender.setJmsTemplate(this.jmsTemplate);
        messageSender.pushEvent(newFlatEvent());
        Mockito.verify(this.jmsTemplate, Mockito.times(1))
                .send(Matchers.any(MessageCreator.class));
        messageSender.pushEvent(newFlatEvent());
        Mockito.verify(this.jmsTemplate, Mockito.times(2))
                .send(Matchers.any(MessageCreator.class));
    }

}
