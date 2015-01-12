package org.komea.microservices.events.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KomeaMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaMessageListener.class.getName());

    @Override
    public void onMessage(final Message message) {
        try {
            if (message instanceof TextMessage) {
                onTextMessage((TextMessage) message);
            }
        } catch (JMSException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void onTextMessage(final TextMessage textMessage) throws JMSException {
        final String text = textMessage.getText();
        LOGGER.debug("onTextMessage : " + text);
    }
}
