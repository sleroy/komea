package org.komea.microservices.messaging.rest;

import org.komea.events.jms.IMessageSender;
import org.komea.events.dto.KomeaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsController.class.getName());

    @Autowired
    private IMessageSender messageSender;

    @RequestMapping(method = RequestMethod.POST, value = "/pushEvent/{destinationName}")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushEvent(@PathVariable String destinationName, @RequestBody final KomeaEvent event) {
        LOGGER.info("JmsController - pushEvent : " + destinationName + " - " + event);
        messageSender.setDestinationName(destinationName);
        messageSender.pushEvent(event);
    }

}
