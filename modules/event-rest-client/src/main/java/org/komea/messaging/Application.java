package org.komea.messaging;

import org.komea.event.messaging.IMessageSender;
import org.komea.messaging.rest.RestMessageSender;

public class Application {

    public static void main(String[] args) {
        final IMessageSender restClient = new RestMessageSender("http://localhost:8080");
        final String jsonEvent = "alalalal";
        restClient.sendJsonEvent(jsonEvent);
    }

}
