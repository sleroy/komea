
package org.komea.product.backend.service.proxy;



import org.komea.product.backend.service.business.ISettingProxy;
import org.komea.product.backend.utils.SpringUtils;
import org.komea.product.database.dao.ProviderSettingDao;
import org.komea.product.database.model.ProviderSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines a proxy to manipulate a provider setting
 * 
 * @author sleroy
 */

public class ProviderSettingProxy<T> implements ISettingProxy<T>
{
    
    
    private static final Logger      LOGGER = LoggerFactory.getLogger(ProviderSettingProxy.class);
    
    private final ProviderSettingDao settingDAO;
    private final int                key;
    
    
    
    public ProviderSettingProxy(final ProviderSettingDao _settingDAO, final int _key) {
    
    
        settingDAO = _settingDAO;
        key = _key;
        
        
    }
    
    
    @Override
    public T get() {
    
    
        LOGGER.trace("Get provider proxy of setting {}", key);
        
        final ProviderSetting providerSetting = (ProviderSetting) getSetting();
        if (providerSetting == null) { return null; }
        return SpringUtils.reifySetting(providerSetting.getType(), providerSetting.getValue());
    }
    
    
    @Override
    public Object getSetting() {
    
    
        LOGGER.trace("Get Setting {}", key);
        return settingDAO.selectByPrimaryKey(key);
    }
    
    
    @Override
    public String getStringValue() {
    
    
        return ((ProviderSetting) getSetting()).getValue();
    }
    
    
    @Override
    public void setValue(final String _value) {
    
    
        LOGGER.trace("Set the value {} -> {}", key, _value);
        final ProviderSetting providerSetting = (ProviderSetting) getSetting();
        try {
            SpringUtils.reifySetting(providerSetting.getType(), _value);
            providerSetting.setValue(_value);
            this.settingDAO.updateByPrimaryKey(providerSetting);
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
        
    }
    
    
    @Override
    public void setValue(final T _value) {
    
    
        setValue(_value == null ? null : _value.toString());
    }
    
}
