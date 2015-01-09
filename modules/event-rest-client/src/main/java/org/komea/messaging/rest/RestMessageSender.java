package org.komea.messaging.rest;

import org.komea.event.messaging.IMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class RestMessageSender implements IMessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestMessageSender.class.getName());
    private static final String BASE_REST_URL = "/rest/messaging";

    private final RestTemplate restTemplate = new RestTemplate();
    private final String baseUrl;
    private String destinationName;

    public RestMessageSender(final String host) {
        this(host, DEFAULT_DESTINATION_NAME);
    }

    public RestMessageSender(final String host, final String destinationName) {
        this.baseUrl = host + BASE_REST_URL;
        this.destinationName = destinationName;
    }

    @Override
    public void setDestinationName(final String destinationName) {
        this.destinationName = destinationName;
    }

    @Override
    public void sendJsonEvent(final String jsonEvent) {
        LOGGER.info("RestMessageSender - sendJsonEvent : " + jsonEvent);
        post("sendJsonEvent", jsonEvent);
    }

    private void post(final String methodName, final Object object) {
        final String url = getUrl(methodName);
        restTemplate.postForObject(url, object, Void.class);
    }

    private String getUrl(final String methodName) {
        return baseUrl + "/" + methodName + "/" + destinationName;
    }
}
