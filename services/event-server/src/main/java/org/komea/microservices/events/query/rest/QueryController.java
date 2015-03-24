package org.komea.microservices.events.query.rest;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.komea.event.model.impl.DateInterval;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.event.queries.executor.EventsFilter;
import org.komea.event.queries.executor.EventsQuery;
import org.komea.event.storage.IEventStorage;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

@RestController
@RequestMapping(method = RequestMethod.GET, value = "/query")
@Transactional
public class QueryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryController.class);

	@Autowired
	private IEventStorage	eventStorageService;

	@Transactional(isolation = Isolation.DEFAULT, readOnly = true)
	@RequestMapping(method = RequestMethod.GET, value = "/countAllEvents")
	@ResponseBody
	public long countAllEvents() {
		return this.eventStorageService.countAllEvents();
	}


	/**
	 * Count number of events from a given type.
	 *
	 * @param className
	 *            the class name
	 * @return the list of events of a given type.
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/count/{className}")
	public long countElements(@PathVariable final String className) {

		return this.eventStorageService.countEventsOfType(className);

	}


	@Transactional(isolation = Isolation.DEFAULT, readOnly = true)
	@RequestMapping(method = RequestMethod.GET, value = "/{eventType}/count")
	@ResponseStatus(value = HttpStatus.OK)
	public Long countEvent(@PathVariable final String eventType) {
		if (!this.eventStorageService.existStorage(eventType)) {
			LOGGER.debug("Ignored query : Event type {} does not exist.",
			             eventType);
			return 0L;
		}
		return this.eventStorageService.getEventDB(eventType).count();

	}

	@Transactional(isolation = Isolation.DEFAULT, readOnly = true)
	@RequestMapping(method = RequestMethod.POST, value = "/executeQuery",
	consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Map<String, Number> executeQuery(@RequestBody @Valid final EventsQuery eventsQuery) {
		return this.eventStorageService.executeQuery(eventsQuery);
	}

	@Transactional(isolation = Isolation.DEFAULT, readOnly = true)
	@RequestMapping(method = RequestMethod.POST, value = "/eventsOnPeriod/{limit}",
	consumes = "application/json", produces = "application/json")
	@ResponseBody
	public List<KomeaEvent> getAllEventsOnPeriod(
	                                             @PathVariable final int limit, final DateInterval interval) {
		return this.eventStorageService.getAllEventsOnPeriod(interval, limit);
	}

	@Transactional(isolation = Isolation.DEFAULT, readOnly = true)
	@RequestMapping(method = RequestMethod.POST, value = "/eventsByFilter",
	consumes = "application/json", produces = "application/json")
	@ResponseBody
	public List<KomeaEvent> getEventsByFilter(@RequestBody @Valid final EventsFilter filter) {
		return this.eventStorageService.getEventsByFilter(filter);
	}

	@Transactional(isolation = Isolation.DEFAULT, readOnly = true)
	@RequestMapping(method = RequestMethod.GET, value = "/eventsOfType/{eventType}",
	produces = "application/json")
	@ResponseBody
	public List<KomeaEvent> getEventsOfType(@PathVariable final String eventType) {
		return Lists.newArrayList(this.eventStorageService.loadEventsOfType(eventType));
	}
	@Transactional(isolation = Isolation.DEFAULT, readOnly = true)
	@RequestMapping(method = RequestMethod.POST, value = "/eventsOfTypeOnPeriod/{eventType}/{limit}",
	consumes = "application/json", produces = "application/json")
	@ResponseBody
	public List<KomeaEvent> getEventsOfTypeOnPeriod(@PathVariable final String eventType,
	                                                @PathVariable final int limit, @RequestBody @Valid final DateInterval interval) {
		return Lists.newArrayList(this.eventStorageService.loadEventsOfTypeOnPeriod(eventType, interval, limit));
	}

	/**
	 * @return the eventStorageService
	 */
	public IEventStorage getEventStorageService() {
		return this.eventStorageService;
	}

	@Transactional(isolation = Isolation.DEFAULT, readOnly = true)
	@RequestMapping(method = RequestMethod.GET, value = "/eventTypes")
	@ResponseBody
	public List<String> getEventTypes() {
		return this.eventStorageService.getEventTypes();
	}


	@Transactional(isolation = Isolation.DEFAULT, readOnly = true)
	@RequestMapping(method = RequestMethod.GET, value = "/{eventType}/last")
	@ResponseStatus(value = HttpStatus.OK)
	public Date getLastEvent(@PathVariable final String eventType) {
		if (!this.eventStorageService.existStorage(eventType)) {
			LOGGER.debug("Ignored query : Event type {} does not exist.",
			             eventType);
			return null;
		}
		return this.eventStorageService.getEventDB(eventType)
				.getLastEvent()
				.toDate();

	}


	/**
	 * @param _eventStorageService the eventStorageService to set
	 */
	public void setEventStorageService(final IEventStorage _eventStorageService) {
		this.eventStorageService = _eventStorageService;
	}

}
