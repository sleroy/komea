/**
 * 
 */

package org.komea.product.backend.service;



import org.komea.product.database.model.Setting;



/**
 * @author sleroy
 */
public interface ISettingListener
{
    
    
    /**
     * Notify property has changed
     * 
     * @param _propertyName
     *            the property name
     * @param _oldValue
     *            the old value
     * @param _newValue
     *            the new value.
     */
    void notifyPropertyChanged(Setting _setting);
}
