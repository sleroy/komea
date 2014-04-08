
package org.komea.product.wicket;



import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;



/**
 * Defines the wicket session.
 * 
 * @author sleroy
 */
public class SecureWicketAuthenticatedWebSession extends AuthenticatedWebSession
{
    
    
    private static final Logger   logger           =
                                                           LoggerFactory
                                                                   .getLogger(SecureWicketAuthenticatedWebSession.class);
    
    private static final long     serialVersionUID = 3355101222374558750L;
    
    @SpringBean(name = "authenticationManager")
    private AuthenticationManager authenticationManager;
    
    
    
    public SecureWicketAuthenticatedWebSession(final Request request) {
    
    
        super(request);
        injectDependencies();
        ensureDependenciesNotNull();
    }
    
    
    @Override
    public boolean authenticate(final String username, final String password) {
    
    
        boolean authenticated = false;
        try {
            final Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            authenticated = authentication.isAuthenticated();
        } catch (final AuthenticationException e) {
            logger.warn(String.format("User &#039;%s&#039; failed to login. Reason: %s", username,
                    e.getMessage()));
            authenticated = false;
        }
        return authenticated;
    }
    
    
    @Override
    public Roles getRoles() {
    
    
        final Roles roles = new Roles();
        getRolesIfSignedIn(roles);
        return roles;
    }
    
    
    private void addRolesFromAuthentication(final Roles roles, final Authentication authentication) {
    
    
        for (final GrantedAuthority authority : authentication.getAuthorities()) {
            roles.add(authority.getAuthority());
        }
    }
    
    
    private void ensureDependenciesNotNull() {
    
    
        if (authenticationManager == null) { throw new IllegalStateException(
                "An authenticationManager is required."); }
    }
    
    
    private void getRolesIfSignedIn(final Roles roles) {
    
    
        if (isSignedIn()) {
            final Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            addRolesFromAuthentication(roles, authentication);
        }
    }
    
    
    private void injectDependencies() {
    
    
        Injector.get().inject(this);
    }
}
