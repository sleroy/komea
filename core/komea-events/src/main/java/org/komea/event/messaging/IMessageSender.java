package org.komea.event.messaging;

import org.komea.event.model.KomeaEvent;

public interface IMessageSender {

    public static final String DEFAULT_DESTINATION_NAME = "komea-queue";

    void pushFlatEvent(KomeaEvent flatEvent);

    void setDestinationName(String destinationName);
}
