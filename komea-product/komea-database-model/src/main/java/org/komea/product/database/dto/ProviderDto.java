package org.komea.product.database.dto;

import java.io.Serializable;
import java.util.List;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;

public class ProviderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Provider provider;
    private List<EventType> eventTypes;

    public ProviderDto(Provider provider, List<EventType> eventTypes) {
        this.provider = provider;
        this.eventTypes = eventTypes;
    }

    public ProviderDto() {
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public List<EventType> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }

}
