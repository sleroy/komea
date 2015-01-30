package org.komea.events.rest;

import org.komea.events.dto.KomeaEvent;
import org.komea.events.jms.IMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestMessageSender extends AbstractRestClient implements IMessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestMessageSender.class.getName());
    private static final String BASE_REST_URL = "/rest/messaging";

    private String destinationName;

    public RestMessageSender(final String host) {
        this(host, DEFAULT_DESTINATION_NAME);
    }

    public RestMessageSender(final String host, final String destinationName) {
        super(host + BASE_REST_URL);
        this.destinationName = destinationName;
    }

    @Override
    public void setDestinationName(final String destinationName) {
        this.destinationName = destinationName;
    }

    @Override
    public void pushEvent(final KomeaEvent event) {
        LOGGER.info("RestMessageSender - pushEvent : " + event);
        post("pushEvent/" + destinationName, event, Void.class);
    }

}
