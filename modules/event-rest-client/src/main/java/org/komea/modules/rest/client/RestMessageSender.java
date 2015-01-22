package org.komea.modules.rest.client;

import org.komea.event.messaging.IMessageSender;
import org.komea.event.model.KomeaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class RestMessageSender implements IMessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestMessageSender.class.getName());
    private static final String BASE_REST_URL = "/rest/messaging";

    private RestTemplate restTemplate = new RestTemplate();
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
    public void pushFlatEvent(final KomeaEvent flatEvent) {
        LOGGER.debug("RestMessageSender - pushFlatEvent : " + flatEvent);
        post("pushFlatEvent", flatEvent);
    }

    private void post(final String methodName, final Object object) {
        final String url = getUrl(methodName);
        restTemplate.postForObject(url, object, Void.class);
    }

    private String getUrl(final String methodName) {
        return baseUrl + "/" + methodName + "/" + destinationName;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
