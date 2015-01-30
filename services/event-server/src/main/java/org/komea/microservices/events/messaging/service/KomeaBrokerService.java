package org.komea.microservices.events.messaging.service;

import javax.annotation.PostConstruct;
import org.apache.activemq.broker.BrokerService;
import org.komea.microservices.events.configuration.MQSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KomeaBrokerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaBrokerService.class.getName());
    @Autowired
    private MQSettings mQSettings;

    @PostConstruct
    private void start() throws Exception {
        LOGGER.info("---------------------------------------------------------");
        LOGGER.info("Messaging connection to {}", mQSettings.getBrokerUrl());
        final BrokerService broker = new BrokerService();
        broker.addConnector(mQSettings.getBrokerUrl());
        broker.start();
        LOGGER.info("---------------------------------------------------------");
    }
}
