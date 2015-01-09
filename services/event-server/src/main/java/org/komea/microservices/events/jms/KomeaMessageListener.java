package org.komea.microservices.events.jms;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KomeaMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaMessageListener.class.getName());

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                onTextMessage((TextMessage) message);
            } else if (message instanceof ObjectMessage) {
                onObjectMessage((ObjectMessage) message);
            }
        } catch (JMSException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void onObjectMessage(final ObjectMessage objectMessage) throws JMSException {
        final Serializable object = objectMessage.getObject();
        LOGGER.info("onObjectMessage : " + object.toString());
    }

    private void onTextMessage(final TextMessage textMessage) throws JMSException {
        final String text = textMessage.getText();
        LOGGER.info("onTextMessage : " + text);
    }
}
