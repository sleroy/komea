package org.komea.modules.messaging.producer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.komea.event.messaging.IMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JmsMessageSender implements IMessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsMessageSender.class.getName());

    private JmsTemplate jmsTemplate;

    public JmsMessageSender(final String brokerUrl) {
        this(brokerUrl, DEFAULT_DESTINATION_NAME);
    }

    public JmsMessageSender(final String brokerUrl, final String destinationName) {
        jmsTemplate = new JmsTemplate(new ActiveMQConnectionFactory(brokerUrl));
        jmsTemplate.setDefaultDestinationName(destinationName);
    }

    @Override
    public void setDestinationName(final String destinationName) {
        jmsTemplate.setDefaultDestinationName(destinationName);
    }

    @Override
    public void sendJsonEvent(final String jsonEvent) {
        LOGGER.debug("JmsMessageSender - sendJsonEvent : " + jsonEvent);
        sendTextMessage(jsonEvent);
    }

    private void sendTextMessage(final String text) {
        final MessageCreator messageCreator = createTextMessageCreator(text);
        sendMessage(messageCreator);
    }

    private MessageCreator createTextMessageCreator(final String text) {
        return new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                return session.createTextMessage(text);
            }
        };
    }

    private void sendMessage(final MessageCreator messageCreator) {
        jmsTemplate.send(messageCreator);
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}
