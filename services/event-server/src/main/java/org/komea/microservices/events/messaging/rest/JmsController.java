package org.komea.microservices.events.messaging.rest;

import javax.annotation.PostConstruct;
import org.komea.event.messaging.IMessageSender;
import org.komea.event.model.impl.KomeaEvent;
import org.komea.microservices.events.configuration.MQSettings;
import org.komea.modules.messaging.producer.JmsMessageSender;
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
@RequestMapping(value = "/messaging")
public class JmsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsController.class.getName());

    @Autowired
    private MQSettings mQSettings;

    private IMessageSender messageSender;

    @PostConstruct
    public void init() {
        messageSender = new JmsMessageSender(mQSettings.getBrokerUrl());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pushEvent/{destinationName}")
    @ResponseStatus(value = HttpStatus.OK)
    public void pushEvent(@PathVariable final String destinationName,
            @RequestBody final KomeaEvent event) {
        LOGGER.info("JmsController - pushEvent : " + event);
        this.messageSender.setDestinationName(destinationName);
        this.messageSender.pushEvent(event);
    }

}
