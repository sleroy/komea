package org.komea.microservices.events.messaging.service;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.komea.event.storage.IEventStorage;
import org.komea.microservices.events.configuration.MQSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class KomeaMessageListenerContainer extends SimpleMessageListenerContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaMessageListenerContainer.class.getName());

    @Autowired
    private MQSettings mQSettings;
    @Autowired
    private IEventStorage eventStorage;

    @PostConstruct
    public void init() {
        setDestinationName(mQSettings.getDestination());
        setMessageListener(new KomeaMessageListener(eventStorage));
        this.setAutoStartup(false);
        this.setExceptionListener(new ExceptionListener() {

            @Override
            public void onException(JMSException exception) {
                onJmsException(exception);
            }
        });
        restart();
    }

    private void onJmsException(final JMSException ex) {
        final int delayInSec = mQSettings.getRetryDelayInSec();
        LOGGER.error("JMS Exception occured, try to reconnect in " + delayInSec + " seconds.", ex);
        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {

            @Override
            public void run() {
                restart();
            }
        }, delayInSec, TimeUnit.SECONDS);
    }

    private void restart() {
        try {
            final String brokerUrl = mQSettings.getBrokerUrl();
            this.setConnectionFactory(new ActiveMQConnectionFactory(brokerUrl));
            this.doStart();
            LOGGER.info("JMS connection to {} is active.", brokerUrl);
        } catch (JMSException ex) {
            onJmsException(ex);
        }
    }

}
