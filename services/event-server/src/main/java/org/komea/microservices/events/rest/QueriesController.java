package org.komea.microservices.events.rest;

import java.util.Map;
import javax.validation.Valid;
import org.komea.event.queries.executor.EventsQuery;
import org.komea.event.queries.executor.EventsQueryExecutor;
import org.komea.event.storage.IEventDB;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/queries")
public class QueriesController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueriesController.class.getName());

    @Autowired
    private IEventStorage eventStorage;

    @RequestMapping(method = RequestMethod.POST, value = "/execute")
    @ResponseBody
    public Map<String, Number> execute(@RequestBody @Valid final EventsQuery eventsQuery) throws Exception {
        LOGGER.debug("QueriesController - execute : " + eventsQuery);
        final String eventType = eventsQuery.getEventType();
        eventStorage.declareEventType(eventType);
        final IEventDB eventDB = eventStorage.getEventDB(eventType);
        final EventsQueryExecutor eventsQueryExecutor = new EventsQueryExecutor(eventDB, eventsQuery);
        return eventsQueryExecutor.execute();
    }

    public void setEventStorage(final IEventStorage eventStorage) {
        this.eventStorage = eventStorage;
    }

}
