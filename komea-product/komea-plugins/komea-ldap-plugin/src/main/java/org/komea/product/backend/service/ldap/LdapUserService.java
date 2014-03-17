
package org.komea.product.backend.service.ldap;



import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.komea.product.api.service.ldap.ILdapUserService;
import org.komea.product.api.service.ldap.LdapUser;
import org.komea.product.backend.plugin.api.PostSettingRegistration;
import org.komea.product.backend.plugin.api.Properties;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.service.ISettingListener;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.model.Setting;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;



/**
 * ILdapUserService Implementation
 * Requires three properties to work (ldap url ,userDN, password)
 * <property name="userDn" value="cn=manager,dc=example,dc=com" /> <property
 * name="password" value="password" />
 * 
 * @author JavaChap
 * @author sleroy
 */
@Service
@Properties(value = {
        @Property(
                key = LdapUserService.LDAP_SERVER,
                value = "ldap://ares.tocea:389",
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
                value = "dc=jbcpcalendar,dc=com",
                type = String.class,
                description = "Specify the LDAP Base url") })
public class LdapUserService implements ILdapUserService, ISettingListener
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
    
    
    
    public static final String    LDAP_PASSWORD     = "ldap_password";
    
    
    public static final String    LDAP_USER_DN      = "ldap_userDn";
    
    
    private static final String   CRON_LDAP         = "0 0/5 * * * ?";
    
    
    private static final String   LDAP_CRON_REFRESH = "LDAP-CRON-REFRESH";
    
    
    private static final Logger   LOGGER            = LoggerFactory
                                                            .getLogger(LdapUserService.class);
    
    private static final long     serialVersionUID  = 4889152297004460837L;
    
    
    /**
     * 
     */
    protected static final String LDAP_BASE         = "ldap_base";
    
    /**
     * 
     */
    protected static final String LDAP_SERVER       = "ldap-server";
    
    
    @Autowired
    private IPersonGroupService   groupService;
    
    
    private LdapTemplate          ldapTemplate;
    
    
    @Autowired
    private IPersonService        personService;
    
    @Autowired
    private ICronRegistryService  registryService;
    
    
    @Autowired
    private ISettingService       settingService;
    
    
    
    @Override
    public boolean authenticate(final String userName, final String password) {
    
    
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person"))
                .and(new EqualsFilter("uid", userName));
        return ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
    }
    
    
    /**
     * Return the group service
     * 
     * @return the group service.
     */
    public IPersonGroupService getGroupService() {
    
    
        return groupService;
    }
    
    
    public IPersonService getPersonService() {
    
    
        return personService;
    }
    
    
    public ICronRegistryService getRegistryService() {
    
    
        return registryService;
    }
    
    
    @Override
    public LdapUser getUser(final String userName) {
    
    
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person"))
                .and(new EqualsFilter("uid", userName));
        final List<LdapUser> users =
                ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(),
                        new UserAttributesMapper());
        if (!users.isEmpty()) { return users.get(0); }
        return null;
    }
    
    
    @Override
    public List<LdapUser> getUsers(final String pattern) {
    
    
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person"));
        if (pattern != null) {
            filter.and(new LikeFilter("uid", pattern));
        }
        final List<LdapUser> users =
                ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(),
                        new UserAttributesMapper());
        return users;
    }
    
    
    @PostSettingRegistration
    public void initializeCron() throws Exception {
    
    
        LOGGER.info("LDAP - LDAP");
        /**
         * Initialisation of the connexion to the ldap server
         */
        LOGGER.info("Creation of the LDAP connection.");
        final String ldapUrl = settingService.getProxy(LDAP_SERVER).getStringValue();
        final String ldapUserDN = settingService.getProxy(LDAP_USER_DN).getStringValue();
        final String password = settingService.getProxy(LDAP_PASSWORD).getStringValue();
        final String ldapBase = settingService.getProxy(LDAP_BASE).getStringValue();
        
        LOGGER.info("LDAP Url : {}", ldapUrl);
        LOGGER.info("LDAP Base : {}", ldapBase);
        LOGGER.info("LDAP UserDN {}", ldapUserDN);
        
        final DefaultSpringSecurityContextSource contextSource =
                new org.springframework.security.ldap.DefaultSpringSecurityContextSource(ldapUrl);
        
        
        // LDAP Anonymous access
        if (Strings.isNullOrEmpty(ldapUserDN) && Strings.isNullOrEmpty(password)) {
            
            contextSource.setAnonymousReadOnly(true);
        }
        contextSource.setUserDn(ldapUserDN);
        contextSource.setPassword(password);
        contextSource.setBase(ldapBase);
        contextSource.afterPropertiesSet();
        
        ldapTemplate = new LdapTemplate(contextSource);
        
        final JobDataMap properties = initializeDataForCron();
        LOGGER.info("Initialization of lDAP Cron.");
        registryService.removeCronTask(LDAP_CRON_REFRESH);
        registryService.registerCronTask(LDAP_CRON_REFRESH, CRON_LDAP, LdapCronRefreshJob.class,
                properties);
        
        
        settingService.registerListener(LDAP_SERVER, this);
        settingService.registerListener(LDAP_PASSWORD, this);
        settingService.registerListener(LDAP_USER_DN, this);
        settingService.registerListener(LDAP_BASE, this);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.ISettingListener#notifyPropertyChanged(org.komea.product.database.model.Setting)
     */
    @Override
    public void notifyPropertyChanged(final Setting _setting) {
    
    
        try {
            initializeCron();
        } catch (final Exception e) {
            throw new IllegalArgumentException("Could not initialize the ldap connection", e);
        }
        
        
    }
    
    
    public void setGroupService(final IPersonGroupService _groupService) {
    
    
        groupService = _groupService;
    }
    
    
    public void setLdapTemplate(final LdapTemplate ldapTemplate) {
    
    
        this.ldapTemplate = ldapTemplate;
    }
    
    
    public void setPersonService(final IPersonService _personService) {
    
    
        personService = _personService;
    }
    
    
    public void setRegistryService(final ICronRegistryService _registryService) {
    
    
        registryService = _registryService;
    }
    
    
    private JobDataMap initializeDataForCron() {
    
    
        final JobDataMap properties = new JobDataMap();
        properties.put("ldap", this);
        properties.put("person", personService);
        properties.put("group", groupService);
        return properties;
    }
    
}
