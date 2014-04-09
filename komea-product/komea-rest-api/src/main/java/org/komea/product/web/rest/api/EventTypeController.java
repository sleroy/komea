package org.komea.product.web.rest.api;

import java.util.List;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/eventtypes")
public class EventTypeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventTypeController.class);

    @Autowired
    private IEventTypeService eventTypeService;

    @RequestMapping(method = RequestMethod.POST, value = "/all")
    @ResponseBody
    public List<EventType> allEventTypes(@RequestBody EntityType entityType) {
        return eventTypeService.getEventTypes(entityType);
    }

}
