package org.komea.modules.messaging.producer;

import java.io.Serializable;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.komea.event.messaging.IMessageSender;
import org.komea.event.model.KomeaEvent;
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
    public void pushEvent(final KomeaEvent event) {
        LOGGER.info("JmsMessageSender - pushEvent : " + event);
        sendObjectMessage(event);
    }

    private void sendObjectMessage(final Object object) {
        final MessageCreator messageCreator = createObjectMessageCreator(object);
        sendMessage(messageCreator);
    }

    private MessageCreator createObjectMessageCreator(final Object object) {
        return new MessageCreator() {
            @Override
            public Message createMessage(final Session session) throws JMSException {
                return session.createObjectMessage((Serializable) object);
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
