
package org.komea.product.backend.service.settings;



import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.komea.product.backend.exceptions.DAOException;
import org.komea.product.backend.plugin.api.InjectSetting;
import org.komea.product.backend.plugin.api.PostSettingRegistration;
import org.komea.product.backend.plugin.api.Properties;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.service.ISettingListener;
import org.komea.product.backend.service.ISettingProxy;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.backend.service.proxy.SettingProxy;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.dao.SettingDao;
import org.komea.product.database.model.Setting;
import org.komea.product.database.model.SettingCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 */

@Service
@Transactional
public class SettingService implements ISettingService, BeanPostProcessor
{
    
    
    private static final Logger                 LOGGER                   =
                                                                                 LoggerFactory
                                                                                         .getLogger("server_settings");
    @Autowired
    private SettingDao                          settingDAO;
    
    private final SettingListenerGroupContainer settingListenerContainer =
                                                                                 new SettingListenerGroupContainer();
    
    
    
    /**
     * Method create.
     * 
     * @param _key
     *            String
     * @param _value
     *            String
     * @param _typeName
     *            String
     * @param _description
     *            String
     * @return Setting
     * @see org.komea.product.backend.service.ISettingService#create(String, String, String, String)
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
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.ISettingService#existProperty(java.lang.String)
     */
    @Override
    public boolean existProperty(final String _propertyName) {
    
    
        final SettingCriteria settingCriteria = new SettingCriteria();
        settingCriteria.createCriteria().andSettingKeyEqualTo(_propertyName);
        final Setting setting =
                CollectionUtil.singleOrNull(settingDAO.selectByCriteria(settingCriteria));
        return setting != null;
    }
    
    
    /**
     * Method getProxy.
     * 
     * @param _key
     *            Integer
     * @return ISettingProxy<T>
     * @see org.komea.product.backend.service.ISettingService#getProxy(Integer)
     */
    @Override
    public <T> ISettingProxy<T> getProxy(final Integer _key) {
    
    
        return new SettingProxy<T>(this, _key);
    }
    
    
    /**
     * Method getProxy.
     * 
     * @param _key
     *            String
     * @return ISettingProxy<T>
     * @see org.komea.product.backend.service.ISettingService#getProxy(String)
     */
    @Override
    public <T> ISettingProxy<T> getProxy(final String _key) {
    
    
        final SettingCriteria criteria = new SettingCriteria();
        criteria.createCriteria().andSettingKeyEqualTo(_key);
        final List<Setting> selectSettingsByCriteria = settingDAO.selectByCriteria(criteria);
        final Setting settingFound = CollectionUtil.singleOrNull(selectSettingsByCriteria);
        if (settingFound == null) { return null; }
        return new SettingProxy<T>(this, settingFound.getId());
    }
    
    
    public SettingDao getSettingDAO() {
    
    
        return settingDAO;
    }
    
    
    /**
     * Method getSettings.
     * 
     * @return List<Setting>
     * @see org.komea.product.backend.service.ISettingService#getSettings()
     */
    @Override
    public List<Setting> getSettings() {
    
    
        return settingDAO.selectByCriteria(new SettingCriteria());
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.ISettingService#getValueOrNull(java.lang.String)
     */
    @Override
    public <T> T getValueOrNull(final String _settingKey) {
    
    
        final ISettingProxy<T> proxy = getProxy(_settingKey);
        if (proxy == null) { return null; }
        return proxy.getValue();
    }
    
    
    /**
     * Injects settings fields
     * 
     * @param _bean
     *            Object
     */
    
    public void injectSettings(final Object _bean) {
    
    
        final PropertyDescriptor[] propertyDescriptors =
                BeanUtils.getPropertyDescriptors(_bean.getClass());
        
        for (final PropertyDescriptor descriptor : propertyDescriptors) {
            if (descriptor.getReadMethod() != null
                    && descriptor.getReadMethod().isAnnotationPresent(InjectSetting.class)) {
                LOGGER.info("Weaving setting proxy on property descriptor {} of bean {}",
                        descriptor, _bean.getClass());
                final Method writeMethod = descriptor.getWriteMethod();
                try {
                    writeMethod.invoke(_bean, getProxy(descriptor.getName()));
                    
                } catch (final Exception e) {
                    throw new IllegalArgumentException(e);
                }
                
            }
            
        }
        
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
    
    
    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessAfterInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessAfterInitialization(final Object _bean, final String _beanName)
            throws BeansException {
    
    
        final Properties annotation =
                AnnotationUtils.findAnnotation(_bean.getClass(), Properties.class);
        if (annotation != null) {
            LOGGER.info("Bean {} defines custom server settings", _beanName);
            for (final Property property : annotation.value()) {
                create(property.key(), property.value(), property.type().getName(),
                        property.description());
            }
            
            injectSettings(_bean);
            triggerPostSettingMethod(_bean, _beanName);
            
            LOGGER.info("-----------------------------------------------------------------------");
        }
        
        
        return _bean;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.springframework.beans.factory.config.BeanPostProcessor#postProcessBeforeInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessBeforeInitialization(final Object _bean, final String _beanName)
            throws BeansException {
    
    
        return _bean;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.ISettingService#registerListener(java.lang.String,
     * org.komea.product.backend.service.ISettingListener)
     */
    @Override
    public void registerListener(final String _propertyName, final ISettingListener _listener) {
    
    
        settingListenerContainer.register(_propertyName, _listener);
        
    }
    
    
    /**
     * Method setSettingDAO.
     * 
     * @param _settingDAO
     *            SettingDao
     */
    public void setSettingDAO(final SettingDao _settingDAO) {
    
    
        settingDAO = _settingDAO;
    }
    
    
    /**
     * Method update.
     * 
     * @param _setting
     *            Setting
     * @see org.komea.product.backend.service.ISettingService#update(Setting)
     */
    @Override
    public void update(final Setting _setting) {
    
    
        LOGGER.debug("Updated setting {}", _setting);
        
        settingDAO.updateByPrimaryKey(_setting);
        settingListenerContainer.notifyUpdate(_setting);
        
        
    }
    
    
    /**
     * Method newSetting.
     * 
     * @param _key
     *            String
     * @param _value
     *            String
     * @param _typeName
     *            String
     * @param _description
     *            String
     * @return Setting
     */
    private Setting newSetting(@NotBlank
    final String _key, @NotNull
    final String _value, @NotBlank
    final String _typeName, final String _description) {
    
    
        LOGGER.info("Registering server setting {}={} #{}", _key, _value, _description);
        
        final Setting setting = new Setting(null, _key, _value, _typeName, _description);
        
        settingDAO.insert(setting);
        settingListenerContainer.notifyUpdate(setting);
        return setting;
        
        
    }
    
    
    private void triggerPostSettingMethod(final Object _bean, final String _beanName) {
    
    
        for (final Method method : _bean.getClass().getMethods()) {
            if (AnnotationUtils.findAnnotation(method, PostSettingRegistration.class) != null) {
                LOGGER.info(
                        "Bean  {} requires additional step of initialization once settings are registered",
                        _beanName);
                try {
                    method.invoke(_bean);
                } catch (final Exception e) {
                    throw new FatalBeanException("Could not perform initialization of the bean "
                            + _beanName + " once the settings are registered", e);
                }
                
            }
        }
    }
    
}
