package org.komea.product.database.dto;

import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;

public class ProviderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Provider provider;
    @NotNull
    @Size(min = 1)
    private List<EventType> eventTypes;
    @NotNull
    private List<PropertyDTO> properties;

    public ProviderDto() {

        super();
    }

    public ProviderDto(final Provider provider, final List<EventType> eventTypes) {

        this.provider = provider;
        this.eventTypes = eventTypes;
        properties = Lists.newArrayList();
    }

    /**
     * @param _provider
     * @param _eventTypes
     * @param _properties
     */
    public ProviderDto(final Provider _provider, final List<EventType> _eventTypes, final List<PropertyDTO> _properties) {

        super();
        provider = _provider;
        eventTypes = _eventTypes;
        properties = _properties;
    }

    public List<EventType> getEventTypes() {

        return eventTypes;
    }

    public List<PropertyDTO> getProperties() {

        return properties;
    }

    @NotNull
    public Provider getProvider() {

        return provider;
    }

    public void setEventTypes(final List<EventType> eventTypes) {

        this.eventTypes = eventTypes;
    }

    public void setProperties(final List<PropertyDTO> _properties) {

        properties = _properties;
    }

    public void setProvider(final Provider provider) {

        this.provider = provider;
    }

}
