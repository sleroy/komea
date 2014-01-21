
package org.komea.product.backend.service;



import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.komea.product.backend.utils.CollectionUtils;
import org.komea.product.database.dao.ProviderSettingDao;
import org.komea.product.database.model.ProviderSetting;
import org.komea.product.database.model.ProviderSettingCriteria;



/**
 * This class defines a proxy to manipulate a provider setting
 * 
 * @author sleroy
 */
public class ProviderSettingProxy<T> implements ISettingProxy<T>
{
    
    
    private final ProviderSettingDao settingDAO;
    private final int                providerID;
    private final String             key;
    
    
    
    public ProviderSettingProxy(
            final ProviderSettingDao _settingDAO,
            final int _providerID,
            final String _key) {
    
    
        settingDAO = _settingDAO;
        providerID = _providerID;
        key = _key;
        
        
    }
    
    
    @Override
    public T get() {
    
    
        final List<ProviderSetting> selectByCriteria =
                settingDAO.selectByCriteria(newSelectOnNameCriteria(providerID, key));
        final ProviderSetting providerSetting = CollectionUtils.singleOrNull(selectByCriteria);
        if (providerSetting == null) { return null; }
        return providerSetting.getValue();
    }
    
    
    @Override
    public String getKey() {
    
    
        return key;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.settings.service.ISettingService#newSelectOnNameCriteria(java.lang.String)
     */
    @Override
    public ProviderSettingCriteria newSelectOnNameCriteria(final int _providerID, @NotBlank
    final String _key) {
    
    
        final ProviderSettingCriteria settingCriteria = new ProviderSettingCriteria();
        settingCriteria.createCriteria().andProviderSettingKeyEqualTo(_key)
                .andIdProviderEqualTo(_providerID);
        
        return settingCriteria;
    }
    
    
    @Override
    public void setValue(final T _value) {
    
    
        // TODO Auto-generated method stub
        
    }
    
}
