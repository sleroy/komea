
package org.komea.product.backend.esper.test;



import java.util.Map;



/**
 */
public class DSLEventBuilder
{
    
    
    /**
     * Method newEvent.
     * @param _eventName String
     * @param _provider String
     * @param _eventType String
     * @return DSLEventBuilder
     */
    public static DSLEventBuilder newEvent(
            final String _eventName,
            final String _provider,
            final String _eventType) {
    
    
        return new DSLEventBuilder(_eventName, _provider, _eventType);
    }
    
    
    
    private final EventMethodGenerator eventBuilder;
    
    
    
    /**
     * Constructor for DSLEventBuilder.
     * @param _eventName String
     * @param _provider String
     * @param _eventType String
     */
    private DSLEventBuilder(final String _eventName, final String _provider, final String _eventType) {
    
    
        super();
        eventBuilder = new EventMethodGenerator(_eventName, _provider, _eventType);
        
    }
    
    
    /**
     * Method build.
     * @return EventMethodGenerator
     */
    public EventMethodGenerator build() {
    
    
        return eventBuilder;
        
    }
    
    
    /**
     * Method fromGroup.
     * @return DSLEventBuilder
     */
    public DSLEventBuilder fromGroup() {
    
    
        eventBuilder.setGroup(true);
        return this;
    }
    
    
    /**
     * Method fromUser.
     * @return DSLEventBuilder
     */
    public DSLEventBuilder fromUser() {
    
    
        eventBuilder.setUsers(true);
        return this;
    }
    
    
    /**
     * Method linkToProject.
     * @return DSLEventBuilder
     */
    public DSLEventBuilder linkToProject() {
    
    
        eventBuilder.setProject(true);
        return this;
        
    }
    
    
    /**
     * Method predefinedMessage.
     * @param _message String
     * @return DSLEventBuilder
     */
    public DSLEventBuilder predefinedMessage(final String _message) {
    
    
        eventBuilder.setPredefinedMessage(_message);
        return this;
    }
    
    
    /**
     * Method withMessage.
     * @return DSLEventBuilder
     */
    public DSLEventBuilder withMessage() {
    
    
        eventBuilder.setMessage(true);
        return this;
    }
    
    
    /**
     * Method withParameter.
     * @param _key String
     * @param _value Class<?>
     * @return DSLEventBuilder
     */
    public DSLEventBuilder withParameter(final String _key, final Class<?> _value) {
    
    
        eventBuilder.addParameter(_key, _value);
        return this;
    }
    
    
    /**
     * Method withParameters.
     * @param _parameters Map<String,Class<?>>
     * @return DSLEventBuilder
     */
    public DSLEventBuilder withParameters(final Map<String, Class<?>> _parameters) {
    
    
        eventBuilder.setParameters(_parameters);
        
        return this;
    }
    
    
    /**
     * Method withURL.
     * @return DSLEventBuilder
     */
    public DSLEventBuilder withURL() {
    
    
        eventBuilder.setUrl(true);
        return this;
    }
    
    
    /**
     * Method withValue.
     * @return DSLEventBuilder
     */
    public DSLEventBuilder withValue() {
    
    
        eventBuilder.setValue(true);
        return this;
    }
}
