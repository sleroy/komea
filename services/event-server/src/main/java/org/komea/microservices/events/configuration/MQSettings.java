package org.komea.microservices.events.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author scarreau
 */
@ConfigurationProperties(prefix = "komea.messaging")
public class MQSettings {

    private String brokerUrl;
    private String destination = "komea-queue";

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
