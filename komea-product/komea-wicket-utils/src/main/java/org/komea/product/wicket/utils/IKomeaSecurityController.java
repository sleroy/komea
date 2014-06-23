/**
 * 
 */

package org.komea.product.wicket.utils;



import java.security.Principal;

import org.springframework.security.core.userdetails.UserDetails;



/**
 * @author sleroy
 *
 */
public interface IKomeaSecurityController
{
    
    
    /**
     * Returns the principal's name, as obtained from the <code>SecurityContextHolder</code>. Properly handles
     * both <code>String</code>-based and <code>UserDetails</code>-based principals.
     *
     * @return the username or <code>null</code> if unavailable
     */
    
    public abstract String getRemoteUser();
    
    
    public abstract UserDetails getUserDetails();
    
    
    /**
     * Returns the <code>Authentication</code> (which is a subclass of <code>Principal</code>), or <code>null</code> if unavailable.
     *
     * @return the <code>Authentication</code>, or <code>null</code>
     */
    
    public abstract Principal getUserPrincipal();
    
    
    /**
     * Simple searches for an exactly matching {@link org.springframework.security.core.GrantedAuthority#getAuthority()}.
     * <p>
     * Will always return <code>false</code> if the <code>SecurityContextHolder</code> contains an <code>Authentication</code> with
     * <code>null</code><code>principal</code> and/or <code>GrantedAuthority[]</code> objects.
     *
     * @param role
     *            the <code>GrantedAuthority</code><code>String</code> representation to check for
     * @return <code>true</code> if an <b>exact</b> (case sensitive) matching granted authority is located, <code>false</code> otherwise
     */
    
    public abstract boolean isUserInRole(String role);
    
    
    public abstract String toString();
    
}
