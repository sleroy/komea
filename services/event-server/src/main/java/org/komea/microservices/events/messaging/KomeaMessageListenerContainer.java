package org.komea.microservices.events.messaging;

import javax.annotation.PostConstruct;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.komea.event.messaging.IMessageSender;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IEventStorage eventsStorage;

    @PostConstruct
    private void init() {
        setMessageListener(new KomeaMessageListener(eventsStorage));
        setConnectionFactory(new ActiveMQConnectionFactory(brokerUrl));
        setDestinationName(destination);
    }

}
