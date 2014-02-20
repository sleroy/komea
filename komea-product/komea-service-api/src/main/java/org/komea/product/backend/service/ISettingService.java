
package org.komea.product.backend.service;



import java.util.List;

import org.komea.product.database.model.Setting;
import org.komea.product.database.model.SettingCriteria;



/**
 */
public interface ISettingService
{
    
    
    /**
     * Create a setting, if the setting is existing, the creation is simply ignored.
     * 
     * @param _key
     *            the key
     * @param _value
     *            the value
     * @param _typeName
     *            the type name.
     * @param _description
     *            the description
     * @return the Settings instance
     */
    public Setting create(String _key, String _value, String _typeName, String _description);
    
    
    /**
     * Return the proxy
     * 
     * @param _key
     *            the key
     * @return the proxy
     */
    public <T> ISettingProxy<T> getProxy(Integer _key);
    
    
    /**
     * Returns a provider proxy
     * 
     * @param _key
     *            the key
     * @return the provider setting
     */
    public <T> ISettingProxy<T> getProxy(String _key);
    
    
    /**
     * Returns the list of settings for a provider
     * 
     * @return the list of settings.
     */
    public List<Setting> getSettings();
    
    
    /**
     * Returns the setting or null.
     * 
     * @param _settingKey
     *            the setting key
     * @return the value.
     */
    public <T> T getValueOrNull(String _settingKey);
    
    
    /**
     * Builds a new criteria that select on name.
     * 
     * @param _key
     * @return SettingCriteria
     */
    public SettingCriteria newSelectOnNameCriteria(String _key);
    
    
    /**
     * Register a listener on property change
     * 
     * @param _propertyName
     *            the property name
     * @param _listener
     *            the listener.
     */
    public void registerListener(String _propertyName, ISettingListener _listener);
    
    
    /**
     * Update a setting
     * 
     * @param _setting
     *            the setting to update
     */
    public void update(Setting _setting);
    
}
