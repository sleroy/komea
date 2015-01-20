package org.komea.microservices.events.jms;

import org.komea.microservices.events.messaging.KomeaMessageListener;
import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import javax.jms.TextMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.mockito.Mockito;

public class KomeaMessageListenerTest {

    @Test
    public void onMessageTest() throws MessageNotWriteableException {
        final KomeaMessageListener messageListener = new KomeaMessageListener();
        final ActiveMQTextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("komeaEvent1");
        messageListener.onMessage(textMessage);
    }

    @Test()
    public void onMessageFailureTest() throws MessageNotWriteableException, JMSException {
        final TextMessage textMessage = Mockito.mock(TextMessage.class);
        Mockito.when(textMessage.getText()).thenThrow(JMSException.class);
        final KomeaMessageListener messageListener = new KomeaMessageListener();
        messageListener.onMessage(textMessage);
    }
}
