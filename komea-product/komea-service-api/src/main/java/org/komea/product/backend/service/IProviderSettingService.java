
package org.komea.product.backend.service;



import java.util.List;

import org.komea.product.database.model.ProviderSetting;
import org.komea.product.database.model.ProviderSettingCriteria;



public interface IProviderSettingService
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
    public ProviderSetting create(
            int _providerID,
            String _key,
            String _value,
            String _typeName,
            String _description);
    
    
    /**
     * Returns the list of settings for a provider
     * 
     * @param _providerID
     *            the provider
     * @return the list of settings.
     */
    public List<ProviderSetting> getSettings();
    
    
    /**
     * Returns the list of settings for a provider
     * 
     * @param _providerID
     *            the provider
     * @return the list of settings.
     */
    public List<ProviderSetting> getSettings(int _providerID);
    
    
    /**
     * UPdates a setting
     * 
     * @param _providerSetting
     *            the provider setting.
     */
    public void update(ProviderSetting _providerSetting);

    /**
     * Returns the setting
     * @param <T> the type of provider
     * @param _key the setting key
     * @return  the value if found
     */
    public <T> T getSettingValue(String _key);
    

    /**
     * Returns the setting
     * @param <T> the type of provider
     * @param _key the setting key
     * @return  the value if found
     */
    public ProviderSetting getSetting(String _key);

    
    public ProviderSettingCriteria newSelectOnNameCriteria(int _providerID, String _key);
    

}
