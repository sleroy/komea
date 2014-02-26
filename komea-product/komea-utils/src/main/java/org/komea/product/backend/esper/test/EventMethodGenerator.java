
package org.komea.product.backend.esper.test;



import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;



/**
 */
public class EventMethodGenerator
{
    
    
    private String                provider;
    private String                eventType;
    private boolean               group;
    private boolean               users;
    private boolean               project;
    private boolean               message;
    private boolean               url;
    private boolean               value;
    
    
    private final String          eventFactoryMethodName;
    
    
    private Map<String, Class<?>> parameters;
    
    
    private String                predefMessage;
    
    
    
    /**
     * Constructor for EventMethodGenerator.
     * @param _eventMethodName String
     * @param _provider String
     * @param _eventType String
     */
    public EventMethodGenerator(
            final String _eventMethodName,
            final String _provider,
            final String _eventType) {
    
    
        super();
        eventFactoryMethodName = _eventMethodName;
        provider = _provider;
        eventType = _eventType;
    }
    
    
    /**
     * Adds a parameter
     * 
     * @param _key
     *            the key
     * @param _value
     *            the value;
     */
    public void addParameter(final String _key, final Class<?> _value) {
    
    
        if (parameters == null) {
            parameters = new HashMap<String, Class<?>>();
        }
        parameters.put(_key, _value);
        
    }
    
    
    /**
     * Method build.
     * @return String
     */
    public String build() {
    
    
        final StringBuilder sb = new StringBuilder();
        buildHeader(sb);
        buildBody(sb);
        sb.append("}");
        return sb.toString();
        
    }
    
    
    /**
     * @param _eventType
     *            the eventType to set
     */
    public void setEventType(final String _eventType) {
    
    
        eventType = _eventType;
    }
    
    
    /**
     * @param _group
     *            the group to set
     */
    public void setGroup(final boolean _group) {
    
    
        group = _group;
    }
    
    
    /**
     * @param _message
     *            the message to set
     */
    public void setMessage(final boolean _message) {
    
    
        message = _message;
    }
    
    
    /**
     * Method setParameters.
     * @param _parameters Map<String,Class<?>>
     */
    public void setParameters(final Map<String, Class<?>> _parameters) {
    
    
        parameters = _parameters;
        
        
    }
    
    
    /**
     * Method setPredefinedMessage.
     * @param _predefMessage String
     */
    public void setPredefinedMessage(final String _predefMessage) {
    
    
        predefMessage = _predefMessage;
        
        
    }
    
    
    /**
     * @param _project
     *            the project to set
     */
    public void setProject(final boolean _project) {
    
    
        project = _project;
    }
    
    
    /**
     * @param _provider
     *            the provider to set
     */
    public void setProvider(final String _provider) {
    
    
        provider = _provider;
    }
    
    
    /**
     * @param _url
     *            the url to set
     */
    public void setUrl(final boolean _url) {
    
    
        url = _url;
    }
    
    
    /**
     * @param _users
     *            the users to set
     */
    public void setUsers(final boolean _users) {
    
    
        users = _users;
    }
    
    
    /**
     * @param _value
     *            the value to set
     */
    public void setValue(final boolean _value) {
    
    
        value = _value;
    }
    
    
    /**
     * Method buildArguments.
     * @param _sb StringBuilder
     */
    private void buildArguments(final StringBuilder _sb) {
    
    
        final StringBuilder args = new StringBuilder();
        if (project) {
            args.append("String _projectName");
        }
        if (group) {
            if (args.length() > 0) {
                args.append(",");
            }
            args.append("String _groupName");
        }
        if (message) {
            if (args.length() > 0) {
                args.append(",");
            }
            args.append("String _message");
        }
        if (value) {
            if (args.length() > 0) {
                args.append(",");
            }
            args.append("double _value");
        }
        if (url) {
            if (args.length() > 0) {
                args.append(",");
            }
            args.append("URL _url");
        }
        if (users) {
            if (args.length() > 0) {
                args.append(",");
            }
            args.append("List<String> _userNames");
        }
        if (parameters != null) {
            
            
            final Set<Entry<String, Class<?>>> entrySet = parameters.entrySet();
            for (final Entry<String, Class<?>> parameter : entrySet) {
                if (args.length() > 0) {
                    args.append(",");
                }
                args.append(parameter.getValue().getName()).append(' ')
                        .append("_" + parameter.getKey());
            }
        }
        _sb.append(args);
        
    }
    
    
    /**
     * Method buildBody.
     * @param _sb StringBuilder
     */
    private void buildBody(final StringBuilder _sb) {
    
    
        _sb.append("EventSimpleDto event = new EventSimpleDto();\n");
        _sb.append("event.setEventType(\"" + eventType + "\");\n");
        _sb.append("event.setProvider(\"" + provider + "\");\n");
        if (project) {
            _sb.append("event.setProject(_projectName);\n");
        }
        if (group) {
            _sb.append("event.setUserGroup(_groupName);\n");
        }
        if (predefMessage != null) {
            _sb.append("event.setMessage(\"" + predefMessage + "\");\n");
        } else if (message) {
            _sb.append("event.setMessage(_message);\n");
        }
        if (value) {
            _sb.append("event.setValue(_value);\n");
        }
        
        if (url) {
            _sb.append("event.setURL(_url.toString());\n");
        }
        if (users) {
            _sb.append("event.setPersons(_users);\n");
        }
        if (parameters != null) {
            _sb.append("event.setProperties(new java.util.HashMap());\n");
            final Set<Entry<String, Class<?>>> entrySet = parameters.entrySet();
            for (final Entry<String, Class<?>> parameter : entrySet) {
                _sb.append("event.getProperties().put(\"").append(parameter.getKey()).append("\",")
                        .append("_" + parameter.getKey()).append(".toString());\n");
                
            }
        }
        _sb.append("return event;\n");
        
    }
    
    
    /**
     * Method buildHeader.
     * @param _sb StringBuilder
     */
    private void buildHeader(final StringBuilder _sb) {
    
    
        _sb.append("public EventSimpleDto " + eventFactoryMethodName + "(");
        buildArguments(_sb);
        _sb.append(") {");
        
        
    }
}
