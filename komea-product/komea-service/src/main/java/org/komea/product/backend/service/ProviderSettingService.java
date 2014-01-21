
package org.komea.product.backend.service;



import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.komea.product.backend.exceptions.DAOException;
import org.komea.product.database.dao.ProviderSettingDao;
import org.komea.product.database.model.ProviderSetting;
import org.komea.product.database.model.ProviderSettingCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class ProviderSettingService implements IProviderSettingService
{
    
    
    @Autowired
    private ProviderSettingDao  settingDAO;
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderSettingService.class);
    
    
    
    @Override
    public ProviderSetting create(
            final int _providerID,
            final String _key,
            final String _value,
            final String _typeName,
            final String _description) {
    
    
        LOGGER.info("Creating provider setting {} with value {}", _key, _value);
        
        final List<ProviderSetting> selectByCriteria =
                settingDAO.selectByCriteria(newSelectOnNameCriteria(_providerID, _key));
        if (selectByCriteria.isEmpty()) { return newSetting(_key, _value, _typeName, _description,
                _providerID); }
        if (selectByCriteria.size() > 1) { throw new DAOException(
                "The setting table should not contain two setting with the same key."); }
        return selectByCriteria.get(0);
    }
    
    
    @Override
    public List<ProviderSetting> getSettings() {
    
    
        return settingDAO.selectByCriteria(new ProviderSettingCriteria());
    }
    
    
    @Override
    public List<ProviderSetting> getSettings(final int _providerID) {
    
    
        final ProviderSettingCriteria providerSettingCriteria = new ProviderSettingCriteria();
        providerSettingCriteria.createCriteria().andIdProviderEqualTo(_providerID);
        return settingDAO.selectByCriteria(providerSettingCriteria);
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
    public void update(final ProviderSetting _setting) {
    
    
        settingDAO.updateByPrimaryKey(_setting);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.settings.service.ISettingService#newSetting(java.lang.String, java.lang.String, java.lang.String)
     */
    private ProviderSetting newSetting(@NotBlank
    final String _key, @NotNull
    final String _value, @NotBlank
    final String _typeName, final String _description, @NotNull
    final Integer _providerID) {
    
    
        final ProviderSetting setting =
                new ProviderSetting(null, _key, _value, _providerID, _typeName, _description);
        settingDAO.insert(setting);
        return setting;
    }
}
