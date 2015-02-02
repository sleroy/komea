package org.komea.event.messaging;

import org.komea.event.model.impl.KomeaEvent;

public interface IMessageSender {

    static String DEFAULT_DESTINATION = "komea-queue";

    void pushEvent(KomeaEvent event);

    void setDestinationName(String destinationName);
}
