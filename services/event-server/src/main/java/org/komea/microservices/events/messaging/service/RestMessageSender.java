package org.komea.microservices.events.messaging.service;

import org.komea.event.messaging.IMessageSender;
import org.komea.event.model.impl.KomeaEvent;
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
		this(host, IMessageSender.DEFAULT_DESTINATION);
	}

	public RestMessageSender(final String host, final String destinationName) {
		this.baseUrl = host + BASE_REST_URL;
		this.destinationName = destinationName;
	}

	@Override
	public void pushEvent(final KomeaEvent event) {
		LOGGER.trace("RestMessageSender - pushEvent : " + event);
		this.post("pushEvent", event);
	}

	@Override
	public void setDestinationName(final String destinationName) {
		this.destinationName = destinationName;
	}

	public void setRestTemplate(final RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	private String getUrl(final String methodName) {
		return this.baseUrl + "/" + methodName + "/" + this.destinationName;
	}

	private void post(final String methodName, final Object object) {
		final String url = this.getUrl(methodName);
		this.restTemplate.postForObject(url, object, Void.class);
	}

}
