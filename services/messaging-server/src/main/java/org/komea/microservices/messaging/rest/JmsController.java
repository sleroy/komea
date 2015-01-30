package org.komea.microservices.messaging.rest;

import org.komea.event.messaging.IMessageSender;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.modules.messaging.producer.JmsMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/messaging")
public class JmsController {

	private static final Logger		LOGGER			= LoggerFactory.getLogger(JmsController.class.getName());

	private static final String		BROKER_URL		= "vm://localhost?create=false";

	private final IMessageSender	messageSender	= new JmsMessageSender(BROKER_URL);

	@RequestMapping(method = RequestMethod.POST, value = "/pushEvent/{destinationName}")
	@ResponseStatus(value = HttpStatus.OK)
	public void pushEvent(@PathVariable final String destinationName,
			@RequestBody final KomeaEvent event) {
		LOGGER.info("JmsController - pushEvent : " + event);
		this.messageSender.setDestinationName(destinationName);
		this.messageSender.pushEvent(event);
	}

}
