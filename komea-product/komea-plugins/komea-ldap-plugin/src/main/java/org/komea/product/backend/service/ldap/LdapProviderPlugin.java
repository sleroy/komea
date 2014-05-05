
package org.komea.product.backend.service.ldap;


import javax.annotation.PostConstruct;

import org.komea.product.api.service.errors.userinterface.LdapPage;
import org.komea.product.api.service.ldap.ILdapProviderPlugin;
import org.komea.product.api.service.ldap.ILdapService;
import org.komea.product.backend.api.PluginAdminPages;
import org.komea.product.backend.api.PluginMountPage;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.database.enums.ProviderType;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;

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
public class LdapProviderPlugin implements ILdapProviderPlugin {
    
    private static final Logger  LOGGER = LoggerFactory.getLogger("ldap-plugin");
    
    @Autowired
    private ICronRegistryService registryService;
    
    @Autowired
    private ILdapService         ldapService;
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.plugin.api.PostSettingRegistration#afterSettingInitialisation()
     */
    @PostConstruct
    public void init() {
    
        LOGGER.info("LDAP - User service");
        try {
            ldapService.initConnection();
            
            final JobDataMap properties = initializeDataForCron();
            LOGGER.info("Initialization of lDAP Cron.");
            registryService.removeCronTask(LDAP_CRON_REFRESH);
            registryService.registerCronTask(LDAP_CRON_REFRESH, CRON_LDAP, LdapCronRefreshJob.class, properties);
            registryService.forceNow(LDAP_CRON_REFRESH);
        } catch (final Exception e) {
            
            throw new BeanCreationException(e.getMessage(), e);
        }
        
    }
    
    private JobDataMap initializeDataForCron() {
    
        final JobDataMap properties = new JobDataMap();
        
        return properties;
    }
    
}
