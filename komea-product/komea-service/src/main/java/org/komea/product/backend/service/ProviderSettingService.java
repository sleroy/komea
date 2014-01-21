package org.komea.product.backend.service;

import java.util.List;
import java.util.logging.Level;
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
public class ProviderSettingService implements IProviderSettingService {

    @Autowired
    private ProviderSettingDao settingDAO;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProviderSettingService.class);

    @Override
    public ProviderSetting create(
            final int _providerID,
            final String _key,
            final String _value,
            final String _typeName,
            final String _description) {

        LOGGER.info("Creating provider setting {} with value {}", _key, _value);

        final List<ProviderSetting> selectByExample
                = settingDAO.selectByCriteria(newSelectOnNameCriteria(_key));
        if (selectByExample.isEmpty()) {
            return newSetting(_key, _value, _typeName, _description,
                    _providerID);
        }
        if (selectByExample.size() > 1) {
            throw new DAOException(
                    "The setting table should not contain two setting with the same key.");
        }
        return selectByExample.get(0);
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
    public ProviderSettingCriteria newSelectOnNameCriteria(@NotBlank
            final String _key) {

        final ProviderSettingCriteria settingCriteria = new ProviderSettingCriteria();
        settingCriteria.createCriteria().andProviderSettingKeyEqualTo(_key);
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

        final ProviderSetting setting
                = new ProviderSetting(null, _key, _value, _providerID, _typeName, _description);
        settingDAO.insert(setting);
        return setting;
    }

    @Override
    public <T> T getSettingValue(String _key) {
        ProviderSetting setting = getSetting(_key);
        if (setting != null) {
            try {
                return (T) Thread.currentThread().getContextClassLoader().loadClass(setting.getType()).getConstructor(String.class).newInstance(setting.getValue());
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(ProviderSettingService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public ProviderSetting getSetting(String _key) {
        final ProviderSettingCriteria providerSettingCriteria = new ProviderSettingCriteria();
        providerSettingCriteria.createCriteria().andProviderSettingKeyEqualTo(_key);
        final List<ProviderSetting> selectByCriteria = settingDAO.selectByCriteria(providerSettingCriteria);
        if (selectByCriteria.isEmpty()) return null;
    return  selectByCriteria.get(0);
    }
}
