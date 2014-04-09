/**
 * 
 */
package org.komea.product.backend.service.ldap;

import java.util.Hashtable;

import org.springframework.ldap.core.support.AbstractContextSource;
import org.springframework.ldap.core.support.SimpleDirContextAuthenticationStrategy;

/**
 * @author sleroy
 *
 */
final class SimpleAuthenticationStrategy extends
        SimpleDirContextAuthenticationStrategy
{
    
    
    /**
     * 
     */
    private final String ldapUserDN;
    
    
    
    /**
     * @param _ldapUserDN
     */
    SimpleAuthenticationStrategy(String _ldapUserDN) {
    
    
        ldapUserDN = _ldapUserDN;
    }
    
    
    @Override
    @SuppressWarnings("rawtypes")
    public void setupEnvironment(
            final Hashtable env,
            final String dn,
            final String password) {
    
    
        super.setupEnvironment(env, dn, password);
        // Remove the pooling flag unless we are authenticating as the 'manager' user.
        if (!ldapUserDN.equals(dn)
                && env.containsKey(AbstractContextSource.SUN_LDAP_POOLING_FLAG)) {
            LdapConnector.LOGGER.debug("Removing pooling flag for user " + dn);
            env.remove(AbstractContextSource.SUN_LDAP_POOLING_FLAG);
        }
    }
}