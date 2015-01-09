package org.komea.event.messaging;

public interface IMessageSender {

    public static final String DEFAULT_DESTINATION_NAME = "komea-queue";

    void sendJsonEvent(String jsonEvent);

    void setDestinationName(String destinationName);
}
