package org.komea.microservices.events.storage.rest;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.komea.event.messaging.IMessageSender;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.storage.IEventStorage;
import org.komea.microservices.events.database.model.ValueEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(method = RequestMethod.GET, value = "/storage")
public class StorageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StorageController.class);

	@Autowired
	private IEventStorage eventStorageService;
	@Autowired
	private IMessageSender messageSender;

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(method = RequestMethod.DELETE, value = "/clearAllEvents")
	@ResponseStatus(HttpStatus.OK)
	public void clearAllEvents() {
		this.eventStorageService.clearAllEvents();
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{eventType}/clear")
	@ResponseStatus(value = HttpStatus.OK)
	public void clearEvent(@PathVariable final String eventType) {
		if (!this.eventStorageService.existStorage(eventType)) {
			LOGGER.debug("Ignored query : Event type {} does not exist.",
			             eventType);
			return;
		}
		this.eventStorageService.clearEventsOfType(eventType);

	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(method = RequestMethod.GET, value = "/{eventType}/declare")
	@ResponseStatus(value = HttpStatus.OK)
	public void declareEvent(@PathVariable final String eventType) {
		this.eventStorageService.declareEventType(eventType);

	}



	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(method = RequestMethod.POST, value = "/push/post")
	@ResponseStatus(value = HttpStatus.OK)
	public void pushGetEvent(final HttpServletRequest _request) {
		final KomeaEvent komeaEvent = new KomeaEvent();
		final Enumeration<String> attributeNames = _request.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			String attr;
			attr = attributeNames.nextElement();
			komeaEvent.put(attr, _request.getAttribute(attr));
		}
		final Enumeration<String> parameterNames = _request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String attr;
			attr = parameterNames.nextElement();
			komeaEvent.put(attr, _request.getAttribute(attr));
		}
		this.pushEvent(komeaEvent);
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(method = RequestMethod.GET, value = "/push/{provider}/{eventName}/{value}")
	@ResponseStatus(value = HttpStatus.OK)
	public void pushGetEvent(@NotEmpty @PathVariable final String provider,
			@NotEmpty @PathVariable final String eventName,
			@NotNull @PathVariable final Double value) {

		final ValueEvent valueEvent = new ValueEvent();

		valueEvent.setEventType(eventName);
		valueEvent.setValue(value);
		valueEvent.setProvider(provider);
		valueEvent.setDate(new Date());
		this.pushEvent(valueEvent);
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(method = RequestMethod.POST, value = "/push")
	@ResponseStatus(value = HttpStatus.OK)
	public void pushPostEvent(@RequestBody final KomeaEvent _event) {
		this.pushEvent(_event);
	}

	public void setEventStorageService(final IEventStorage eventStorageService) {
		this.eventStorageService = eventStorageService;
	}

	public void setMessageSender(final IMessageSender messageSender) {
		this.messageSender = messageSender;
	}

	private void pushEvent(final KomeaEvent event) {
		if (this.messageSender == null) {
			LOGGER.warn("JMS message sender not initialized, event sending directly to storage.");
			this.eventStorageService.storeEvent(event);
		} else {
			this.messageSender.pushEvent(event);
		}
	}

}
