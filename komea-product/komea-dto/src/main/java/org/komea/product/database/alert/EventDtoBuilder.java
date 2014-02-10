
package org.komea.product.database.alert;



import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;

import org.komea.product.database.dto.EventSimpleDto;



public class EventDtoBuilder
{
    
    
    public static EventDtoBuilder newAlert() {
    
    
        return new EventDtoBuilder();
    }
    
    
    
    private final EventSimpleDto event;
    
    
    
    private EventDtoBuilder() {
    
    
        super();
        event = new EventSimpleDto();
    }
    
    
    public EventDtoBuilder at(final Date _date) {
    
    
        event.setDate(_date);
        return this;
    }
    
    
    public EventSimpleDto build() {
    
    
        return event;
    }
    
    
    public EventDtoBuilder message(final String _message) {
    
    
        event.setMessage(_message);
        return this;
    }
    
    
    public EventDtoBuilder eventType(final String _eventType) {
    
    
        event.setEventType(_eventType);
        return this;
    }
    
    
    public EventDtoBuilder project(final String _project) {
    
    
        event.setProject(_project);
        return this;
    }
    
    
    public EventDtoBuilder provided(final String _provider) {
    
    
        event.setProvider(_provider);
        return this;
    }
    
    
    public EventDtoBuilder team(final Date _date) {
    
    
        event.setDate(_date);
        return this;
    }
    
    
    public EventDtoBuilder url(final String _url) {
    
    
        try {
            url(new URL(_url));
        } catch (final MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return this;
    }
    
    
    public EventDtoBuilder url(final URL _url) {
    
    
        event.setUrl(_url.toString());
        return this;
    }
    
    
    public EventDtoBuilder value(final double _doubleValue) {
    
    
        event.setValue(_doubleValue);
        return this;
    }
    
    
    public EventDtoBuilder withGroup(final String _personGroup) {
    
    
        event.setPersonGroup(_personGroup);
        return this;
    }
    
    
    public EventDtoBuilder withParam(final String _paramName, final String _value) {
    
    
        event.getProperties().put(_paramName, _value);
        return this;
    }
    
    
    public EventDtoBuilder withUser(final String _userName) {
    
    
        event.getPersons().add(_userName);
        return this;
    }
    
    
}
