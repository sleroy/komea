
package org.komea.product.backend.service.ldap;


import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.komea.product.api.service.errors.KomeaLdapConfigurationException;
import org.komea.product.api.service.errors.userinterface.LdapPage;
import org.komea.product.api.service.ldap.ILdapConnector;
import org.komea.product.api.service.ldap.ILdapService;
import org.komea.product.api.service.ldap.ILdapUserService;
import org.komea.product.api.service.ldap.LdapUser;
import org.komea.product.backend.api.PluginAdminPages;
import org.komea.product.backend.api.PluginMountPage;
import org.komea.product.backend.plugin.api.PostSettingRegistration;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.plugins.IEventTypeService;
import org.komea.product.database.enums.ProviderType;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;

/**
 * ILdapUserService Implementation Requires three properties to work (ldap url
 * ,userDN, password)
 * <property name="userDn" value="cn=manager,dc=example,dc=com" /> <property
 * name="password" value="password" />
 *
 * @author JavaChap
 * @author sleroy
 */
@ProviderPlugin(name = "LDAP Plugin", icon = "ldap", eventTypes = {}, type = ProviderType.LDAP, url = "/ldap")
@PluginAdminPages(@PluginMountPage(pluginName = "LDAP Plugin", page = LdapPage.class))
public class LdapUserService implements ILdapUserService, PostSettingRegistration {
    
    private static class UserAttributesMapper implements AttributesMapper {
        
        @Override
        public Object mapFromAttributes(final Attributes attrs) throws NamingException {
        
            final LdapUser user = new LdapUserImpl();
            if (attrs.get("uid") != null) {
                user.setUserName((String) attrs.get("uid").get());
            }
            if (attrs.get("cn") != null) {
                user.setFirstName((String) attrs.get("cn").get());
            }
            if (attrs.get("sn") != null) {
                user.setLastName((String) attrs.get("sn").get());
            }
            if (attrs.get("mail") != null) {
                user.setEmail((String) attrs.get("mail").get());
            }
            return user;
        }
        
    }
    
    private static final Logger  LOGGER           = LoggerFactory.getLogger("ldap-plugin");
    
    private static final long    serialVersionUID = 4889152297004460837L;
    
    @Autowired
    private ICronRegistryService registryService;
    
    @Autowired
    private ILdapService         ldapService;
    
    @Autowired
    private IEventTypeService    evenTypeService;
    
    @Autowired
    private LdapConnector        ldapConnector;
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.plugin.api.PostSettingRegistration#afterSettingInitialisation()
     */
    @Override
    public void afterSettingInitialisation() {
    
        LOGGER.info("LDAP - User service");
        try {
            
            final JobDataMap properties = initializeDataForCron();
            LOGGER.info("Initialization of lDAP Cron.");
            registryService.removeCronTask(LDAP_CRON_REFRESH);
            registryService.registerCronTask(LDAP_CRON_REFRESH, CRON_LDAP, LdapCronRefreshJob.class, properties);
            registryService.forceNow(LDAP_CRON_REFRESH);
        } catch (final Exception e) {
            
            throw new BeanCreationException(e.getMessage(), e);
        }
        
    }
    
    public ICronRegistryService getRegistryService() {
    
        return registryService;
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.api.service.ldap.ILdapUserService#newConnector()
     */
    @Override
    public ILdapConnector newConnector() throws KomeaLdapConfigurationException {
    
        ldapConnector.initConnection();
        return ldapConnector;
    }
    
    public void setRegistryService(final ICronRegistryService _registryService) {
    
        registryService = _registryService;
    }
    
    private JobDataMap initializeDataForCron() {
    
        final JobDataMap properties = new JobDataMap();
        
        return properties;
    }
    
    /**
     * @param _ldapService
     *            the bugZillaConfiguration to set
     */
    public void setBugZillaConfiguration(final ILdapService _ldapService) {
    
        ldapService = _ldapService;
    }
    
    /**
     * @param _evenTypeService
     *            the evenTypeService to set
     */
    public void setEvenTypeService(final IEventTypeService _evenTypeService) {
    
        evenTypeService = _evenTypeService;
    }
    
}
