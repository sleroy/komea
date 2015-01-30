package org.komea.microservices.events.storage.rest;

import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
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

	@Autowired
	private IEventStorage		eventStorageService;
	private static final Logger	LOGGER	= LoggerFactory.getLogger(StorageController.class);

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{eventType}/clear")
	@ResponseStatus(value = HttpStatus.OK)
	public void clearEvent(@PathVariable final String eventType) {
		if (!this.eventStorageService.existStorage(eventType)) {
			LOGGER.debug(	"Ignored query : Event type {} does not exist.",
							eventType);
			return;
		}
		this.eventStorageService.clearEventsOfType(eventType);

	}

	@Transactional(isolation = Isolation.DEFAULT, readOnly = true)
	@RequestMapping(method = RequestMethod.GET, value = "/{eventType}/count")
	@ResponseStatus(value = HttpStatus.OK)
	public Long countEvent(@PathVariable final String eventType) {
		if (!this.eventStorageService.existStorage(eventType)) {
			LOGGER.debug(	"Ignored query : Event type {} does not exist.",
							eventType);
			return 0L;
		}
		return this.eventStorageService.getEventDB(eventType).count();

	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@RequestMapping(method = RequestMethod.GET, value = "/{eventType}/declare")
	@ResponseStatus(value = HttpStatus.OK)
	public void declareEvent(@PathVariable final String eventType) {
		this.eventStorageService.declareEventType(eventType);

	}

	@Transactional(isolation = Isolation.DEFAULT, readOnly = true)
	@RequestMapping(method = RequestMethod.GET, value = "/{eventType}/last")
	@ResponseStatus(value = HttpStatus.OK)
	public Date getLastEvent(@PathVariable final String eventType) {
		if (!this.eventStorageService.existStorage(eventType)) {
			LOGGER.debug(	"Ignored query : Event type {} does not exist.",
							eventType);
			return null;
		}
		return this.eventStorageService.getEventDB(eventType)
										.getLastEvent()
										.toDate();

	}

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

		this.eventStorageService.storeEvent(komeaEvent);

	}

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
		this.eventStorageService.storeEvent(valueEvent);

	}

	@RequestMapping(method = RequestMethod.POST, value = "/push")
	@ResponseStatus(value = HttpStatus.OK)
	public void pushPostEvent(@RequestBody final KomeaEvent _event) {
		this.eventStorageService.storeEvent(_event);

	}
}
