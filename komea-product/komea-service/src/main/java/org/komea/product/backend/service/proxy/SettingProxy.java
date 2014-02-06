
package org.komea.product.backend.service.proxy;



import org.komea.product.backend.service.business.ISettingProxy;
import org.komea.product.backend.utils.SpringUtils;
import org.komea.product.database.dao.SettingDao;
import org.komea.product.database.model.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines a proxy to manipulate a provider setting
 * 
 * @author sleroy
 */

public class SettingProxy<T> implements ISettingProxy<T>
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingProxy.class);
    
    private final SettingDao    settingDAO;
    
    private final Integer       key;
    
    
    
    public SettingProxy(final SettingDao _settingDAO, final Integer _key) {
    
    
        settingDAO = _settingDAO;
        key = _key;
    }
    
    
    @Override
    public T get() {
    
    
        LOGGER.trace("Get property {}", key);
        
        final Setting providerSetting = (Setting) getSetting();
        if (providerSetting == null) { return null; }
        return SpringUtils.reifySetting(providerSetting.getType(), providerSetting.getValue());
    }
    
    
    @Override
    public Object getSetting() {
    
    
        LOGGER.trace("Get setting {}", key);
        
        return settingDAO.selectByPrimaryKey(key);
        
        
    }
    
    
    @Override
    public String getStringValue() {
    
    
        return ((Setting) getSetting()).getValue();
    }
    
    
    @Override
    public void setValue(final String _value) {
    
    
        LOGGER.trace("Set property {} and value {}", key, _value);
        
        final Setting providerSetting = (Setting) getSetting();
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
