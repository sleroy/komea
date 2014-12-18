package org.komea.experimental;


public class KomeaConfiguration
{
    
    private final String processDbUrl;
    private final String eventsDbUrl;
    
    public KomeaConfiguration(final String processDbUrl, final String eventsDbUrl) {
    
        super();
        this.processDbUrl = processDbUrl;
        this.eventsDbUrl = eventsDbUrl;
    }

    
    public String getProcessDbUrl() {
    
        return this.processDbUrl;
    }

    
    public String getEventsDbUrl() {
    
        return this.eventsDbUrl;
    }
    
    
}
