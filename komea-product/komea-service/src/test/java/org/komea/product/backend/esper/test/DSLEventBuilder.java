
package org.komea.product.backend.esper.test;



import java.util.Map;



public class DSLEventBuilder
{
    
    
    public static DSLEventBuilder newEvent(
            final String _eventName,
            final String _provider,
            final String _eventType) {
    
    
        return new DSLEventBuilder(_eventName, _provider, _eventType);
    }
    
    
    
    private final EventMethodGenerator eventBuilder;
    
    
    
    private DSLEventBuilder(final String _eventName, final String _provider, final String _eventType) {
    
    
        super();
        eventBuilder = new EventMethodGenerator(_eventName, _provider, _eventType);
        
    }
    
    
    public EventMethodGenerator build() {
    
    
        return eventBuilder;
        
    }
    
    
    public DSLEventBuilder fromGroup() {
    
    
        eventBuilder.setGroup(true);
        return this;
    }
    
    
    public DSLEventBuilder fromUser() {
    
    
        eventBuilder.setUsers(true);
        return this;
    }
    
    
    public DSLEventBuilder linkToProject() {
    
    
        eventBuilder.setProject(true);
        return this;
        
    }
    
    
    public DSLEventBuilder predefinedMessage(final String _message) {
    
    
        eventBuilder.setPredefinedMessage(_message);
        return this;
    }
    
    
    public DSLEventBuilder withMessage() {
    
    
        eventBuilder.setMessage(true);
        return this;
    }
    
    
    public DSLEventBuilder withParameter(final String _key, final Class<?> _value) {
    
    
        eventBuilder.addParameter(_key, _value);
        return this;
    }
    
    
    public DSLEventBuilder withParameters(final Map<String, Class<?>> _parameters) {
    
    
        eventBuilder.setParameters(_parameters);
        
        return this;
    }
    
    
    public DSLEventBuilder withURL() {
    
    
        eventBuilder.setUrl(true);
        return this;
    }
    
    
    public DSLEventBuilder withValue() {
    
    
        eventBuilder.setValue(true);
        return this;
    }
}
