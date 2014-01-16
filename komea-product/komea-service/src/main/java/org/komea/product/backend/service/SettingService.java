
package org.komea.product.backend.service;


import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.komea.product.backend.exceptions.DAOException;
import org.komea.product.database.dao.SettingMapper;
import org.komea.product.database.model.Setting;
import org.komea.product.database.model.SettingCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SettingService implements ISettingService
{
    
    @Autowired
    private SettingMapper settingDAO;
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.settings.service.ISettingService#getOrCreate(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Setting getOrCreate(@NotBlank final String _key, @NotNull final String _value, final @NotBlank String _typeName,
            final String _description) {
    
        final List<Setting> selectByExample = settingDAO.selectByExample(newSelectOnNameCriteria(_key));
        if (selectByExample.isEmpty()) {
            return newSetting(_key, _value, _typeName, _description);
        }
        if (selectByExample.size() > 1) {
            throw new DAOException("The setting table should not contain two setting with the same key.");
        }
        return selectByExample.get(0);
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.settings.service.ISettingService#newSelectOnNameCriteria(java.lang.String)
     */
    @Override
    public SettingCriteria newSelectOnNameCriteria(@NotBlank final String _key) {
    
        final SettingCriteria settingCriteria = new SettingCriteria();
        settingCriteria.createCriteria().andSettingKeyEqualTo(_key);
        return settingCriteria;
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.settings.service.ISettingService#newSetting(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Setting newSetting(@NotBlank final String _key, @NotNull final String _value, @NotBlank final String _typeName,
            final String _description) {
    
        final Setting setting = new Setting(null, _key, _value, _typeName, _description);
        settingDAO.insert(setting);
        return setting;
    }
}
