package org.komea.microservices.events.configuration;

import org.komea.event.messaging.IMessageSender;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author scarreau
 */
@ConfigurationProperties(prefix = "komea.messaging")
public class MQSettings {

    private String brokerUrl;
    private String destination = IMessageSender.DEFAULT_DESTINATION;
    private int retryDelayInSec = 60 * 10;

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

    public int getRetryDelayInSec() {
        return retryDelayInSec;
    }

    public void setRetryDelayInSec(int retryDelayInSec) {
        this.retryDelayInSec = retryDelayInSec;
    }

}
