package org.komea.event.messaging;

import org.komea.event.model.impl.KomeaEvent;

public interface IMessageSender {

    void pushEvent(KomeaEvent event);

    void setDestinationName(String destinationName);
}
