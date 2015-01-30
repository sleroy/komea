package org.komea.events.jms;

import org.komea.events.dto.KomeaEvent;

public interface IMessageSender {

    public static final String DEFAULT_DESTINATION_NAME = "komea-queue";

    void pushEvent(KomeaEvent event);

    void setDestinationName(String destinationName);
}
