
package org.komea.product.backend.service;



import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.komea.product.backend.exceptions.DAOException;
import org.komea.product.backend.plugin.api.Properties;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.service.business.ISettingProxy;
import org.komea.product.backend.service.proxy.SettingProxy;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.dao.SettingDao;
import org.komea.product.database.model.Setting;
import org.komea.product.database.model.SettingCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Properties(@Property(
        key = "logfile_path",
        description = "Specify the path to access logs",
        type = String.class,
        value = "komea.log"))
@Service
public class SettingService implements ISettingService
{
    
    
    @Autowired
    private SettingDao          settingDAO;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SettingService.class);
    
    
    
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
        
        final List<Setting> selectByCriteria =
                settingDAO.selectByCriteria(newSelectOnNameCriteria(_key));
        if (selectByCriteria.isEmpty()) { return newSetting(_key, _value, _typeName, _description); }
        if (selectByCriteria.size() > 1) { throw new DAOException(
                "The setting table should not contain two setting with the same key."); }
        return selectByCriteria.get(0);
    }
    
    
    @Override
    public <T> ISettingProxy<T> getProxy(final Integer _key) {
    
    
        return new SettingProxy<T>(settingDAO, _key);
    }
    
    
    @Override
    public <T> ISettingProxy<T> getProxy(final String _key) {
    
    
        final SettingCriteria criteria = new SettingCriteria();
        criteria.createCriteria().andSettingKeyEqualTo(_key);
        final List<Setting> selectSettingsByCriteria = settingDAO.selectByCriteria(criteria);
        final Setting settingFound = CollectionUtil.singleOrNull(selectSettingsByCriteria);
        if (settingFound == null) { return null; }
        return new SettingProxy<T>(settingDAO, settingFound.getId());
    }
    
    
    @Override
    public SettingDao getSettingDAO() {
    
    
        return settingDAO;
    }
    
    
    @Override
    public List<Setting> getSettings() {
    
    
        return settingDAO.selectByCriteria(new SettingCriteria());
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
    
    
    public void setSettingDAO(final SettingDao _settingDAO) {
    
    
        settingDAO = _settingDAO;
    }
    
    
    @Override
    public void update(final Setting _setting) {
    
    
        LOGGER.debug("Updated setting {}", _setting);
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
