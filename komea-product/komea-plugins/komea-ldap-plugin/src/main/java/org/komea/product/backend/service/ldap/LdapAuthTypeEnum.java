/**
 * 
 */

package org.komea.product.backend.service.ldap;



/**
 * Defines the type of authentication allowed.
 * 
 * @author sleroy
 */
public enum LdapAuthTypeEnum {
    DIGEST_MD5, SIMPLE, SIMPLE_TLS, TLS_CERTIFICATE
}
