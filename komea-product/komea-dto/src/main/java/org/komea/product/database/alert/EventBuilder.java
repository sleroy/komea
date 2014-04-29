
package org.komea.product.database.alert;



import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Project;
import org.komea.product.database.model.Provider;



public class EventBuilder
{
    
    
    public static EventBuilder newAlert() {
    
    
        return new EventBuilder();
    }
    
    
    
    private final Event event;
    
    
    
    private EventBuilder() {
    
    
        super();
        event = new Event();
    }
    
    
    public EventBuilder at(final Date _date) {
    
    
        event.setDate(_date);
        return this;
    }
    
    
    public Event build() {
    
    
        return event;
    }
    
    
    public EventBuilder eventType(final EventType _eventType) {
    
    
        event.setEventType(_eventType);
        return this;
    }
    
    
    public EventBuilder message(final String _message) {
    
    
        event.setMessage(_message);
        return this;
    }
    
    
    public EventBuilder project(final Project _project) {
    
    
        event.setProject(_project);
        return this;
    }
    
    
    public EventBuilder provided(final Provider _provider) {
    
    
        event.setProvider(_provider);
        return this;
    }
    
    
    public EventBuilder team(final Date _date) {
    
    
        event.setDate(_date);
        return this;
    }
    
    
    public EventBuilder url(final String _url) {
    
    
        try {
            url(new URL(_url));
        } catch (final MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return this;
    }
    
    
    public EventBuilder url(final URL _url) {
    
    
        event.setUrl(_url.toString());
        return this;
    }
    
    
    public EventBuilder value(final double _doubleValue) {
    
    
        event.setValue(_doubleValue);
        return this;
    }
    
    
    public EventBuilder withGroup(final PersonGroup _personGroup) {
    
    
        event.setPersonGroup(_personGroup);
        return this;
    }
    
    
    public EventBuilder withParam(final String _paramName, final String _value) {
    
    
        event.getProperties().put(_paramName, _value);
        return this;
    }
    
    
    public EventBuilder withUser(final Person _userName) {
    
    
        event.setPerson(_userName);
        return this;
    }
    
    
}
