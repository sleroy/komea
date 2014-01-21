
package org.komea.product.backend.service;



import java.util.List;

import org.komea.product.database.dao.SettingDao;
import org.komea.product.database.model.Setting;
import org.komea.product.database.model.SettingCriteria;



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
     * Returns the list of settings for a provider
     * 
     * @param _providerID
     *            the provider
     * @return the list of settings.
     */
    public List<Setting> getSettings();
    
    
    /**
     * Builds a new criteria that select on name.
     * 
     * @param _key
     * @return
     */
    public SettingCriteria newSelectOnNameCriteria(String _key);
    
    
    /**
     * Update a setting
     * 
     * @param _setting
     *            the setting to update
     */
    public void update(Setting _setting);
    
    
    /**
     * Returns a provider proxy
     * 
     * @param _key
     *            the key
     * @return the provider setting
     */
    <T> ISettingProxy<T> getProxy(String _key);
    
    
    SettingDao getSettingDAO();
    
}
