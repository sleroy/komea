package org.komea.microservices.events.jms;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.event.model.DateInterval;
import org.komea.event.model.KomeaEvent;
import org.komea.event.storage.IEventStorage;
import org.komea.microservices.events.messaging.KomeaMessageListener;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class KomeaMessageListenerTest {

    @Mock
    private IEventStorage eventStorage;

    @Test
    public void onMessageTest() throws Exception {
        final KomeaMessageListener messageListener = new KomeaMessageListener(eventStorage);
        final ActiveMQObjectMessage message = new ActiveMQObjectMessage();
        final KomeaEvent event = new KomeaEvent();
        message.setObject(event);
        messageListener.onMessage(message);
        Mockito.verify(eventStorage, Mockito.times(1)).storeEvent(event);
        message.setObject(new DateInterval());
        messageListener.onMessage(message);
        Mockito.verify(eventStorage, Mockito.times(1)).storeEvent(event);
    }

    @Test()
    public void onMessageFailureTest() throws Exception {
        final ObjectMessage message = Mockito.mock(ObjectMessage.class);
        Mockito.when(message.getObject()).thenThrow(JMSException.class);
        final KomeaMessageListener messageListener = new KomeaMessageListener(eventStorage);
        messageListener.onMessage(message);
    }
}
