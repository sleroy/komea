package org.komea.microservices.events.messaging.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KomeaMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaMessageListener.class.getName());

    private final IEventStorage eventStorage;

    public KomeaMessageListener(final IEventStorage eventStorage) {
        this.eventStorage = eventStorage;
    }

    @Override
    public void onMessage(final Message message) {
        try {
            if (message instanceof ObjectMessage) {
                onObjectMessage((ObjectMessage) message);
            }
        } catch (JMSException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void onObjectMessage(final ObjectMessage objectMessage) throws JMSException {
        final Object object = objectMessage.getObject();
        LOGGER.info("onObjectMessage : " + object);
        if (object instanceof KomeaEvent) {
            eventStorage.storeEvent((KomeaEvent) object);
        } else {
            LOGGER.warn("An object that is not a FlatEvent was received : " + object);
        }
    }
}
