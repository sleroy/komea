
package org.komea.product.backend.service.proxy;



import org.komea.product.backend.service.ISettingProxy;
import org.komea.product.backend.service.settings.SettingService;
import org.komea.product.backend.utils.SpringUtils;
import org.komea.product.database.model.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines a proxy to manipulate a provider setting
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */

public class SettingProxy<T> implements ISettingProxy<T>
{
    
    
    private static final Logger  LOGGER = LoggerFactory.getLogger(SettingProxy.class);
    
    private final Integer        key;
    
    private final SettingService settingService;
    
    
    
    /**
     * Constructor for SettingProxy.
     * 
     * @param _settingService
     *            SettingDao
     * @param _key
     *            Integer
     */
    public SettingProxy(final SettingService _settingService, final Integer _key) {
    
    
        settingService = _settingService;
        key = _key;
    }
    
    
    /**
     * Method getSetting.
     * 
     * @return Object
     * @see org.komea.product.backend.service.ISettingProxy#getSetting()
     */
    @Override
    public Object getSetting() {
    
    
        LOGGER.trace("Get setting {}", key);
        
        return settingService.getSettingDAO().selectByPrimaryKey(key);
        
        
    }
    
    
    /**
     * Method getStringValue.
     * 
     * @return String
     * @see org.komea.product.backend.service.ISettingProxy#getStringValue()
     */
    @Override
    public String getStringValue() {
    
    
        return ((Setting) getSetting()).getValue();
    }
    
    
    /**
     * Method get.
     * 
     * @return T
     * @see org.komea.product.backend.service.ISettingProxy#getValue()
     */
    @Override
    public T getValue() {
    
    
        LOGGER.trace("Get property {}", key);
        
        final Setting providerSetting = (Setting) getSetting();
        if (providerSetting == null) { return null; }
        return SpringUtils.reifySetting(providerSetting.getType(), providerSetting.getValue());
    }
    
    
    /**
     * Method setValue.
     * 
     * @param _value
     *            String
     * @see org.komea.product.backend.service.ISettingProxy#setStringValue(String)
     */
    @Override
    public void setStringValue(final String _value) {
    
    
        LOGGER.trace("Set property {} and value {}", key, _value);
        
        final Setting providerSetting = (Setting) getSetting();
        try {
            SpringUtils.reifySetting(providerSetting.getType(), _value);
            providerSetting.setValue(_value);
            this.settingService.update(providerSetting);
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
        
    }
    
    
    /**
     * Method setValue.
     * 
     * @param _value
     *            T
     * @see org.komea.product.backend.service.ISettingProxy#setValue(T)
     */
    @Override
    public void setValue(final T _value) {
    
    
        setStringValue(_value == null ? null : _value.toString());
    }
    
}
