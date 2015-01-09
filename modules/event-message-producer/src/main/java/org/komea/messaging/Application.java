package org.komea.messaging;

import org.komea.event.messaging.IMessageSender;
import org.komea.messaging.jms.JmsMessageSender;

public class Application {

    public static void main(String[] args) {
        final IMessageSender messageSender = new JmsMessageSender(
                "tcp://localhost:61616", IMessageSender.DEFAULT_DESTINATION_NAME);
        final String jsonEvent = "lol";
        messageSender.sendJsonEvent(jsonEvent);
    }

}
