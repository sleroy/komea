
package org.komea.product.backend.service.ldap;



import javax.annotation.PostConstruct;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.komea.product.api.service.errors.KomeaLdapConfigurationException;
import org.komea.product.api.service.ldap.ILdapConnector;
import org.komea.product.api.service.ldap.ILdapUserService;
import org.komea.product.api.service.ldap.LdapUser;
import org.komea.product.backend.plugin.api.Properties;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.database.enums.ProviderType;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;



/**
 * ILdapUserService Implementation
 * Requires three properties to work (ldap url ,userDN, password)
 * <property name="userDn" value="cn=manager,dc=example,dc=com" /> <property
 * name="password" value="password" />
 * 
 * @author JavaChap
 * @author sleroy
 */
@ProviderPlugin(
    name = "LDAP Plugin",
    icon = "ldap",
    eventTypes = {},
    type = ProviderType.LDAP,
    url = "/ldap")
@Properties(
    value =
        {
                @Property(
                    key = LdapUserService.LDAP_SERVER,
                    value = "",
                    type = String.class,
                    description = "Specify the location of the LDAP Server"),
                    @Property(
                        key = LdapUserService.LDAP_PASSWORD,
                        value = "",
                        type = String.class,
                        description = "Specify the LDAP Server password required to authenticate"),
                    @Property(
                        key = LdapUserService.LDAP_USER_DN,
                        value = "",
                        type = String.class,
                        description = "Specify the LDAP userDn"),
                    @Property(
                        key = LdapUserService.LDAP_BASE,
                        value = "dc=company,dc=com",
                        type = String.class,
                        description = "Specify the LDAP Base url"),
                    @Property(
                        key = ILdapUserService.LDAP_AUTH_TYPE,
                        value = "SIMPLE",
                        type = LdapAuthTypeEnum.class,
                        description = "Specify the type of authentication used (DIGEST_MD5, SIMPLE, SIMPLE_TLS, TLS_CERTIFICATE)") })
public class LdapUserService implements ILdapUserService
{
    
    
    private static class UserAttributesMapper implements AttributesMapper
    {
        
        
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
    private ISettingService      settingService;
    
    
    
    public ICronRegistryService getRegistryService() {
    
    
        return registryService;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        LOGGER.info("LDAP - LDAP");
        try {
            
            final JobDataMap properties = initializeDataForCron();
            LOGGER.info("Initialization of lDAP Cron.");
            registryService.removeCronTask(LDAP_CRON_REFRESH);
            registryService.registerCronTask(LDAP_CRON_REFRESH, CRON_LDAP,
                    LdapCronRefreshJob.class, properties);
            registryService.forceNow(LDAP_CRON_REFRESH);
        } catch (final Exception e) {
            
            throw new BeanCreationException(e.getMessage(), e);
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.api.service.ldap.ILdapUserService#newConnector()
     */
    @Override
    public ILdapConnector newConnector() throws KomeaLdapConfigurationException {
    
    
        final LdapConnector ldapConnector = new LdapConnector();
        ldapConnector.setSettingService(settingService);
        ldapConnector.initConnector();
        return ldapConnector;
    }
    
    
    public void setRegistryService(final ICronRegistryService _registryService) {
    
    
        registryService = _registryService;
    }
    
    
    private JobDataMap initializeDataForCron() {
    
    
        final JobDataMap properties = new JobDataMap();
        
        return properties;
    }
    
}
