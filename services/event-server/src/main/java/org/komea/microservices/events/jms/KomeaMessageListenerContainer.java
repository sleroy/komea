package org.komea.microservices.events.jms;

import javax.annotation.PostConstruct;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.komea.event.messaging.IMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class KomeaMessageListenerContainer extends SimpleMessageListenerContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaMessageListenerContainer.class.getName());

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.destination:" + IMessageSender.DEFAULT_DESTINATION_NAME + "}")
    private String destination;

    @PostConstruct
    private void init() {
        setMessageListener(new KomeaMessageListener());
        setConnectionFactory(new ActiveMQConnectionFactory(brokerUrl));
        setDestinationName(destination);
    }

}
