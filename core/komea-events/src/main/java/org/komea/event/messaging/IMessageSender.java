package org.komea.event.messaging;

import org.komea.event.model.beans.FlatEvent;

public interface IMessageSender {

    public static final String DEFAULT_DESTINATION_NAME = "komea-queue";

    void pushFlatEvent(FlatEvent flatEvent);

    void setDestinationName(String destinationName);
}
