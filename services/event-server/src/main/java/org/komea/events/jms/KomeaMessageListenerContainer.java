package org.komea.events.jms;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.komea.events.service.IEventsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class KomeaMessageListenerContainer extends SimpleMessageListenerContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaMessageListenerContainer.class.getName());
    private static final int DEFAULT_DELAY_IN_SEC = 60 * 10;

    private int delayInSec = DEFAULT_DELAY_IN_SEC;

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.destination:" + IMessageSender.DEFAULT_DESTINATION_NAME + "}")
    private String destination;

    @Autowired
    private IEventsService eventsService;

    @PostConstruct
    public void init() {
        this.setDestinationName(destination);
        this.setMessageListener(new KomeaMessageListener(eventsService));
        this.setAutoStartup(false);
        this.setExceptionListener(new ExceptionListener() {

            @Override
            public void onException(JMSException exception) {
                onJmsException(exception);
            }
        });
        restart();
    }

    private final void onJmsException(final JMSException ex) {
        LOGGER.error("JMS Exception occured, try to reconnect in " + delayInSec + " seconds.", ex);
        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {

            @Override
            public void run() {
                restart();
            }
        }, delayInSec, TimeUnit.SECONDS);
    }

    private final void restart() {
        try {
            this.setConnectionFactory(new ActiveMQConnectionFactory(brokerUrl));
            this.doStart();
            LOGGER.info("JMS connection to " + brokerUrl + " is active.");
        } catch (JMSException ex) {
            onJmsException(ex);
        }
    }

    public void setDelayInSec(final int delayInSec) {
        this.delayInSec = delayInSec;
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public void setBrokerUrl(final String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public void setEventsService(final IEventsService eventsService) {
        this.eventsService = eventsService;
    }

}
