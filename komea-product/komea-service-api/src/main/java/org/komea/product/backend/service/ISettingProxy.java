
package org.komea.product.backend.service;



/**
 * Returns a proxy over a setting or a provider setting
 * 
 * @author sleroy
 */
public interface ISettingProxy<T>
{
    
    
    /**
     * Returns the vamie
     * 
     * @return the value
     */
    T get();
    
    
    /**
     * Returns the provider setting key.
     * 
     * @return the key
     */
    String getKey();
    
    
    /**
     * Sets the value
     * 
     * @param _value
     *            the value
     */
    void setValue(T _value);
    
    
}
