
package org.komea.product.backend.service.ldap;


import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.komea.product.api.service.errors.KomeaLdapConfigurationException;
import org.komea.product.api.service.ldap.ILdapConnector;
import org.komea.product.api.service.ldap.ILdapService;
import org.komea.product.api.service.ldap.LdapUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.DefaultTlsDirContextAuthenticationStrategy;
import org.springframework.ldap.core.support.DigestMd5DirContextAuthenticationStrategy;
import org.springframework.ldap.core.support.ExternalTlsDirContextAuthenticationStrategy;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

/**
 * ILdapUserService Implementation Requires three properties to work (ldap url
 * ,userDN, password)
 * <property name="userDn" value="cn=manager,dc=example,dc=com" /> <property
 * name="password" value="password" />
 *
 * @author JavaChap
 * @author sleroy
 */
@Component
public class LdapConnector implements Closeable, ILdapConnector {
    
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
    
    private static final long serialVersionUID = 4889152297004460837L;
    
    static final Logger       LOGGER           = LoggerFactory.getLogger("ldap-connector");
    
    private LdapTemplate      ldapTemplate;
    
    private boolean           init             = false;
    
    @Autowired
    private ILdapService      ldapService;
    
    @Override
    public boolean isInit() {
    
        return init;
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.plugin.api.PostSettingRegistration#afterSettingInitialisation()
     */
    @Override
    public void initConnection() {
    
        LOGGER.info("LDAP - LDAP");
        try {
            /**
             * Initialisation of the connexion to the ldap server
             */
            LOGGER.info("Creation of the LDAP connection.");
            LdapServer ldapServer = ldapService.load();
            final String ldapUrl = ldapServer.getLdapUrl();
            
            if (Strings.isNullOrEmpty(ldapUrl)) {
                return;
            }
            
            final String ldapUserDN = ldapServer.getLdapUserDN();
            final String password = ldapServer.getLdapPassword();
            final String ldapBase = ldapServer.getLdapBase();
            final LdapAuthTypeEnum ldapAuthTypeEnum = ldapServer.getLdapAuthTypeEnum();
            LOGGER.info("LDAP Url : {}", ldapUrl);
            LOGGER.info("LDAP Base : {}", ldapBase);
            LOGGER.info("LDAP UserDN {}", ldapUserDN);
            
            final DefaultSpringSecurityContextSource contextSource = new org.springframework.security.ldap.DefaultSpringSecurityContextSource(
                    ldapUrl);
            LOGGER.info("Authentication strategy employed to connect on LDAP Server : {} ", ldapAuthTypeEnum);
            initializeAuthenticationStrategy(ldapUserDN, ldapAuthTypeEnum, contextSource);
            
            // LDAP Anonymous access
            if (Strings.isNullOrEmpty(ldapUserDN) && Strings.isNullOrEmpty(password)) {
                
                contextSource.setAnonymousReadOnly(true);
            }
            contextSource.setUserDn(ldapUserDN);
            contextSource.setPassword(password);
            contextSource.setBase(ldapBase);
            contextSource.afterPropertiesSet();
            
            ldapTemplate = new LdapTemplate(contextSource);
            ldapTemplate.afterPropertiesSet();
            
        } catch (final Exception e) {
            LOGGER.error("----------LDAP ERROR-------------");
            LOGGER.error(e.getMessage(), e);
            LOGGER.error("----------LDAP ERROR-------------");
            throw new KomeaLdapConfigurationException(e.getMessage(), e);
        }
        init = true;
    }
    /*
     * (non-Javadoc)
     * @see java.io.Closeable#close()
     */
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.ldap.ILdapConnector#authenticate(java.lang.String, java.lang.String)
     */
    @Override
    public boolean authenticate(final String userName, final String password) {
    
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter("uid", userName));
        return ldapTemplate.authenticate(DistinguishedName.EMPTY_PATH, filter.toString(), password);
    }
    
    @Override
    public void close() throws IOException {
    
        if (ldapTemplate != null) {
            //
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.ldap.ILdapConnector#getUser(java.lang.String)
     */
    @Override
    public LdapUser getUser(final String userName) {
    
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person")).and(new EqualsFilter("uid", userName));
        final List<LdapUser> users = ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), new UserAttributesMapper());
        if (!users.isEmpty()) {
            return users.get(0);
        }
        return null;
    }
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.ldap.ILdapConnector#getUsers(java.lang.String)
     */
    @Override
    public List<LdapUser> getUsers(final String pattern) {
    
        final AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectclass", "person"));
        if (pattern != null) {
            filter.and(new LikeFilter("uid", pattern));
        }
        final List<LdapUser> users = ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), new UserAttributesMapper());
        return users;
    }
    
    public void setLdapTemplate(final LdapTemplate ldapTemplate) {
    
        this.ldapTemplate = ldapTemplate;
    }
    
    /**
     * Initialize the authentication strategy.
     *
     * @param ldapUserDN
     *            the user
     * @param ldapAuthTypeEnum
     *            the type of auth.
     * @param contextSource
     *            the context source.
     */
    private void initializeAuthenticationStrategy(final String ldapUserDN, final LdapAuthTypeEnum ldapAuthTypeEnum,
            final DefaultSpringSecurityContextSource contextSource) {
    
        switch (ldapAuthTypeEnum) {
            case SIMPLE:
            default:
                contextSource.setAuthenticationStrategy(new SimpleAuthenticationStrategy(ldapUserDN));
                break;
            case DIGEST_MD5:
                contextSource.setAuthenticationStrategy(new DigestMd5DirContextAuthenticationStrategy());
                break;
            case SIMPLE_TLS:
                contextSource.setAuthenticationStrategy(new DefaultTlsDirContextAuthenticationStrategy());
                break;
            case TLS_CERTIFICATE:
                contextSource.setAuthenticationStrategy(new ExternalTlsDirContextAuthenticationStrategy());
                break;
        }
    }
    
}
