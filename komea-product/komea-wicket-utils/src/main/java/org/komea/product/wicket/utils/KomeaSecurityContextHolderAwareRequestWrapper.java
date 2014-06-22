/*
 * Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.komea.product.wicket.utils;



import java.security.Principal;
import java.util.Collection;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.xml.ws.RequestWrapper;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.util.Assert;



/**
 * A Spring Security-aware <code>RequestWrapper</code>, which uses the <code>SecurityContext</code>-defined <code>Authentication</code>
 * object to implement the servlet API security
 * methods:
 * <ul>
 * <li>{@link #getUserPrincipal()}</li>
 * <li>{@link KomeaSecurityContextHolderAwareRequestWrapper#isUserInRole(String)}</li>
 * <li>{@link RequestWrapper#getRemoteUser()}.</li>
 * </ul>
 *
 * @see SecurityContextHolderAwareRequestFilter
 * @author Orlando Garcia Carmona
 * @author Ben Alex
 * @author Luke Taylor
 * @author Rob Winch
 */
public class KomeaSecurityContextHolderAwareRequestWrapper extends ServletRequestWrapper
{


    // ~ Instance fields ================================================================================================

    /**
     * The prefix passed by the filter. It will be prepended to any supplied role values before
     * comparing it with the roles obtained from the security context.
     */
    private final String                      rolePrefix;

    private final AuthenticationTrustResolver trustResolver;



    // ~ Constructors ===================================================================================================

    /**
     * Creates a new instance
     *
     * @param request
     *            the original {@link Request}
     * @param trustResolver
     *            the {@link AuthenticationTrustResolver} to use. Cannot be
     *            null.
     * @param rolePrefix
     *            The prefix to be added to {@link #isUserInRole(String)} or null if no prefix.
     */
    public KomeaSecurityContextHolderAwareRequestWrapper(
            final ServletRequest request,
            final AuthenticationTrustResolver trustResolver,
            final String rolePrefix) {


        super(request);
        Assert.notNull(trustResolver, "trustResolver cannot be null");
        this.rolePrefix = rolePrefix;
        this.trustResolver = trustResolver;
    }


    /**
     * Creates a new instance with {@link AuthenticationTrustResolverImpl}.
     *
     * @param request
     * @param rolePrefix
     */
    public KomeaSecurityContextHolderAwareRequestWrapper(
            final ServletRequest request,
            final String rolePrefix) {


        this(request, new AuthenticationTrustResolverImpl(), rolePrefix);
    }


    // ~ Methods ========================================================================================================

    /**
     * Returns the principal's name, as obtained from the <code>SecurityContextHolder</code>. Properly handles
     * both <code>String</code>-based and <code>UserDetails</code>-based principals.
     *
     * @return the username or <code>null</code> if unavailable
     */

    public String getRemoteUser() {


        final Authentication auth = getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            return null;
        }

        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        }

        return auth.getPrincipal().toString();
    }


    public UserDetails getUserDetails() {


        final Authentication auth = getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            return null;
        }

        if (auth.getPrincipal() instanceof UserDetails) {
            return (UserDetails) auth.getPrincipal();
        }

        return null;
    }


    /**
     * Returns the <code>Authentication</code> (which is a subclass of <code>Principal</code>), or <code>null</code> if unavailable.
     *
     * @return the <code>Authentication</code>, or <code>null</code>
     */

    public Principal getUserPrincipal() {


        final Authentication auth = getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            return null;
        }

        return auth;
    }


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

    public boolean isUserInRole(final String role) {


        return isGranted(role);
    }


    @Override
    public String toString() {


        return "SecurityContextHolderAwareRequestWrapper[ " + getRequest() + "]";
    }


    /**
     * Obtain the current active <code>Authentication</code>
     *
     * @return the authentication object or <code>null</code>
     */
    private Authentication getAuthentication() {


        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!trustResolver.isAnonymous(auth)) {
            return auth;
        }

        return null;
    }


    private boolean isGranted(String role) {


        final Authentication auth = getAuthentication();

        if (rolePrefix != null) {
            role = rolePrefix + role;
        }

        if (auth == null || auth.getPrincipal() == null) {
            return false;
        }

        final Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        if (authorities == null) {
            return false;
        }


        for (final GrantedAuthority grantedAuthority : authorities) {
            if (role.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }

        return false;
    }
}
