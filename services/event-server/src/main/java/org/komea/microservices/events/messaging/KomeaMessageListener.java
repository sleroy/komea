package org.komea.microservices.events.messaging;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.komea.event.model.KomeaEvent;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class KomeaMessageListener implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaMessageListener.class.getName());

    @Autowired
    private IEventStorage eventStorageService;

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
        LOGGER.debug("onObjectMessage : " + object);
        if (object instanceof KomeaEvent) {
            eventStorageService.storeEvent((KomeaEvent) object);
        } else {
            LOGGER.warn("An object that is not a FlatEvent was received : " + object);
        }
    }
}
