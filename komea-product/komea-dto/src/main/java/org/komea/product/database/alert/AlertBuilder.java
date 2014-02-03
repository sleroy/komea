
package org.komea.product.database.alert;



import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;

import org.komea.product.database.alert.enums.Criticity;



public class AlertBuilder
{
    
    
    public static AlertBuilder newAlert() {
    
    
        return new AlertBuilder();
    }
    
    
    
    private final Alert alert;
    
    
    
    private AlertBuilder() {
    
    
        super();
        alert = new Alert();
    }
    
    
    public AlertBuilder at(final Date _date) {
    
    
        alert.setDate(_date);
        return this;
    }
    
    
    public AlertBuilder category(final String _category) {
    
    
        alert.setCategory(_category);
        return this;
    }
    
    
    public AlertBuilder criticity(final Criticity _criticity) {
    
    
        alert.setCriticity(_criticity);
        return this;
    }
    
    
    public AlertBuilder fullMessage(final String _message) {
    
    
        alert.setFullMessage(_message);
        return this;
    }
    
    
    public Alert getAlert() {
    
    
        return alert;
    }
    
    
    public AlertBuilder value(final double _doubleValue) {
    
    
        alert.setValue(_doubleValue);
        return this;
    }
    
    
    public AlertBuilder icon(final String _icon) {
    
    
        alert.setIcon(_icon);
        ;
        return this;
    }
    
    
    public AlertBuilder message(final String _message) {
    
    
        alert.setMessage(_message);
        return this;
    }
    
    
    public AlertBuilder project(final String _project) {
    
    
        alert.setProject(_project);
        return this;
    }
    
    
    public AlertBuilder provided(final String _provider) {
    
    
        alert.setProvider(_provider);
        return this;
    }
    
    
    public AlertBuilder team(final Date _date) {
    
    
        alert.setDate(_date);
        return this;
    }
    
    
    public AlertBuilder type(final String _type) {
    
    
        alert.setType(_type);
        return this;
    }
    
    
    public AlertBuilder url(final String _url) {
    
    
        try {
            url(new URL(_url));
        } catch (final MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return this;
    }
    
    
    public AlertBuilder url(final URL _url) {
    
    
        alert.setURL(_url.toString());
        return this;
    }
    
    
    public AlertBuilder withParam(final String _paramName, final Object _value) {
    
    
        alert.getParams().put(_paramName, _value);
        return this;
    }
    
    
    public AlertBuilder withUser(final String _userName) {
    
    
        alert.getUsers().add(_userName);
        return this;
    }
    
    
}
