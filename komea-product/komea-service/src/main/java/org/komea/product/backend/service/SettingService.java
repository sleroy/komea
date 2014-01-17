
package org.komea.product.backend.service;



import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.komea.product.backend.exceptions.DAOException;
import org.komea.product.database.dao.SettingMapper;
import org.komea.product.database.model.Setting;
import org.komea.product.database.model.SettingCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class SettingService implements ISettingService
{
    
    
    @Autowired
    private SettingMapper       settingDAO;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderSettingService.class);
    
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.settings.service.ISettingService#getOrCreate(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Setting create(@NotBlank
    final String _key, @NotNull
    final String _value, final @NotBlank
    String _typeName, final String _description) {
    
    
        LOGGER.info("Creating Komea setting {} with value {}", _key, _value);
        
        final List<Setting> selectByExample =
                settingDAO.selectByExample(newSelectOnNameCriteria(_key));
        if (selectByExample.isEmpty()) { return newSetting(_key, _value, _typeName, _description); }
        if (selectByExample.size() > 1) { throw new DAOException(
                "The setting table should not contain two setting with the same key."); }
        return selectByExample.get(0);
    }
    
    
    @Override
    public List<Setting> getSettings() {
    
    
        return settingDAO.selectByExample(new SettingCriteria());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.settings.service.ISettingService#newSelectOnNameCriteria(java.lang.String)
     */
    @Override
    public SettingCriteria newSelectOnNameCriteria(@NotBlank
    final String _key) {
    
    
        final SettingCriteria settingCriteria = new SettingCriteria();
        settingCriteria.createCriteria().andSettingKeyEqualTo(_key);
        return settingCriteria;
    }
    
    
    @Override
    public void update(final Setting _setting) {
    
    
        settingDAO.updateByPrimaryKey(_setting);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.settings.service.ISettingService#newSetting(java.lang.String, java.lang.String, java.lang.String)
     */
    private Setting newSetting(@NotBlank
    final String _key, @NotNull
    final String _value, @NotBlank
    final String _typeName, final String _description) {
    
    
        final Setting setting = new Setting(null, _key, _value, _typeName, _description);
        settingDAO.insert(setting);
        return setting;
    }
}
