package org.komea.microservices.events.query.rest;

import java.util.Date;
import java.util.Map;

import org.komea.event.queries.executor.EventsFilter;
import org.komea.event.queries.executor.EventsQuery;
import org.komea.event.queries.formulas.FormulaDto;
import org.komea.event.queries.formulas.FormulaType;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(method = RequestMethod.GET, value = "/query")
@Transactional
public class QueryShortcutController {
	private static final Logger	LOGGER	= LoggerFactory.getLogger(QueryShortcutController.class);

	@Autowired
	private IEventStorage		eventStorageService;

	@Transactional(isolation = Isolation.DEFAULT, readOnly = true)
	@RequestMapping(method = RequestMethod.GET, value = "/execute", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public Map<String, Number> executeQuery(@RequestParam final String eventFilter,
	                                        @RequestParam final Date periodBegin, @RequestParam final Date periodEnd,
	                                        @RequestParam final String periodField, @RequestParam final String groupBy,
	                                        @RequestParam final String aggregateField, @RequestParam final String formula) {



		final EventsQuery eventsQuery = new EventsQuery();
		eventsQuery.setGroupBy(groupBy);
		eventsQuery.setFormula(new FormulaDto(FormulaType.valueOf(formula), aggregateField));
		final EventsFilter filter = new EventsFilter();
		filter.setEventType(eventFilter);
		filter.setInterval(periodBegin, periodEnd);

		eventsQuery.setFilter(filter);
		return this.eventStorageService.executeQuery(eventsQuery);
	}

	/**
	 * @return the eventStorageService
	 */
	public IEventStorage getEventStorageService() {
		return this.eventStorageService;
	}

	/**
	 * @param _eventStorageService
	 *            the eventStorageService to set
	 */
	public void setEventStorageService(final IEventStorage _eventStorageService) {
		this.eventStorageService = _eventStorageService;
	}

}
