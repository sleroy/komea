package org.komea.microservices.messaging;

import javax.annotation.PostConstruct;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KomeaBrokerService {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @PostConstruct
    private void start() throws Exception {
        final BrokerService broker = new BrokerService();
        broker.addConnector(brokerUrl);
        broker.start();
    }
}
