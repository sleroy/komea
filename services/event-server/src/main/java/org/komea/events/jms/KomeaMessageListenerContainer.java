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

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.destination:" + IMessageSender.DEFAULT_DESTINATION_NAME + "}")
    private String destination;

    @Autowired
    private IEventsService eventsStorage;

    @PostConstruct
    private void init() {
        this.setDestinationName(destination);
        this.setMessageListener(new KomeaMessageListener(eventsStorage));
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
        LOGGER.error("JMS Exception occured, try to reconnect in 10 minutes.", ex);
        Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {

            @Override
            public void run() {
                restart();
            }
        }, 10, TimeUnit.MINUTES);
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

}
