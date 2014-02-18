
package org.komea.product.backend.service.business;



/**
 * Returns a proxy over a setting or a provider setting
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public interface ISettingProxy<T>
{
    
    
    /**
     * Returns the vamie
     * 
    
     * @return the value */
    T get();
    
    
    /**
     * Returns the setting.
     * 
    
     * @return the setting. */
    Object getSetting();
    
    
    /**
     * Returns the String value
     * 
    
     * @return the String value. */
    String getStringValue();
    
    
    /**
     * Sets the value
     * 
     * @param _value
     *            the value
     */
    void setValue(String _value);
    
    
    /**
     * Sets the value (converted with toString() method)
     * 
     * @param _value
     *            the value
     */
    void setValue(T _value);
    
    
}
