package org.komea.microservices.events.storage.rest;

import java.util.Date;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.komea.event.model.KomeaEvent;
import org.komea.event.storage.IEventStorage;
import org.komea.microservices.events.database.model.ValueEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private IEventStorage eventStorageService;

    @Transactional
    @RequestMapping(method = RequestMethod.DELETE, value = "/clear/{eventType}")
    @ResponseStatus(value = HttpStatus.OK)
    public void clearEvent(@PathVariable final String eventType) {
        eventStorageService.clearEventsOfType(eventType);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/push/{provider}/{eventName}/{value}")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushGetEvent(@PathVariable final String provider,
            @NotEmpty @PathVariable final String eventName,
            @NotNull @PathVariable final Double value) {

        final ValueEvent valueEvent = new ValueEvent();

        valueEvent.setEventType(eventName);
        valueEvent.setValue(value);
        valueEvent.setProvider(provider);
        valueEvent.setDate(new Date());
        eventStorageService.storeEvent(valueEvent);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/push_flat")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushPostEvent(@RequestBody final KomeaEvent _event) {
        eventStorageService.storeEvent(_event);

    }
}
