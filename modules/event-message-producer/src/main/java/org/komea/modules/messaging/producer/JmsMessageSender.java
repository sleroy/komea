package org.komea.modules.messaging.producer;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.komea.events.jms.IMessageSender;
import org.komea.events.dto.KomeaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class JmsMessageSender implements IMessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsMessageSender.class.getName());

    private JmsTemplate jmsTemplate;

    @Value("${komea.broker.url}")
    private String brokerUrl;

    @Value("${komea.broker.destination:" + DEFAULT_DESTINATION_NAME + "}")
    private String destinationName;

    @PostConstruct
    public void init() {
        jmsTemplate = new JmsTemplate(new ActiveMQConnectionFactory(brokerUrl));
        jmsTemplate.setDefaultDestinationName(destinationName);
    }

    public void setBrokerUrl(final String brokerUrl) {
        this.brokerUrl = brokerUrl;
        init();
    }

    @Override
    public void setDestinationName(final String destinationName) {
        this.destinationName = destinationName;
        init();
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
