package org.komea.microservices.events.messaging.service;

import javax.annotation.PostConstruct;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.komea.event.storage.IEventStorage;
import org.komea.microservices.events.configuration.MQSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class KomeaMessageListenerContainerBean extends SimpleMessageListenerContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaMessageListenerContainerBean.class.getName());
    @Autowired
    private MQSettings mQSettings;

    @Autowired
    private IEventStorage eventsStorage;

    @PostConstruct
    private void init() {
        setMessageListener(new KomeaMessageListener(eventsStorage));
        setConnectionFactory(new ActiveMQConnectionFactory(mQSettings.getBrokerUrl()));
        setDestinationName(mQSettings.getDestination());
    }

}
