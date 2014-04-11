/**
 * 
 */

package org.komea.product.backend.auth;



import java.util.Collections;

import org.junit.Test;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.enums.UserBdd;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonCriteria;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.PersonRoleCriteria;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class UserAuthenticationServiceTest
{
    
    
    /**
     * Test method for {@link org.komea.product.backend.auth.UserAuthenticationService#UserAuthenticationService()}.
     */
    @Test @Ignore(expected = UsernameNotFoundException.class)
    public void testUserAuthenticationService() throws Exception {
    
    
        final UserAuthenticationService userAuthenticationService = new UserAuthenticationService();
        final IPersonService personDAO =
                Mockito.mock(IPersonService.class, Mockito.withSettings().verboseLogging());
        userAuthenticationService.setPersonDAO(personDAO);
        final IPersonRoleService personRoleDAO =
                Mockito.mock(IPersonRoleService.class, Mockito.withSettings().verboseLogging());
        userAuthenticationService.setPersonRoleDAO(personRoleDAO);
        userAuthenticationService.loadUserByUsername("sleroy");
        verify(personDAO, times(1)).selectByCriteria(Matchers.any(PersonCriteria.class));
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.auth.UserAuthenticationService#UserAuthenticationService()}.
     */
    @Test @Ignore()
    public void testUserAuthenticationService2() throws Exception {
    
    
        final UserAuthenticationService userAuthenticationService = new UserAuthenticationService();
        final IPersonService personDAO =
                Mockito.mock(IPersonService.class, Mockito.withSettings().verboseLogging());
        userAuthenticationService.setPersonDAO(personDAO);
        final IPersonRoleService personRoleDAO =
                Mockito.mock(IPersonRoleService.class, Mockito.withSettings().verboseLogging());
        
        
        final Person demoPerson = new Person();
        demoPerson.setLogin("sleroy");
        demoPerson.setId(1);
        demoPerson.setIdPersonRole(2);
        demoPerson.setPassword("demoPassword");
        demoPerson.setUserBdd(UserBdd.KOMEA);
        
        final PersonRole personRole = new PersonRole();
        personRole.setRoleKey("DEMO");
        personRole.setName("Demo Role");
        
        
        when(personDAO.selectByCriteria(Matchers.any(PersonCriteria.class))).thenReturn(
                Collections.singletonList(demoPerson));
        
        when(personRoleDAO.selectByCriteria(Matchers.any(PersonRoleCriteria.class))).thenReturn(
                Collections.singletonList(personRole));
        
        
        userAuthenticationService.setPersonRoleDAO(personRoleDAO);
        final UserDetails username = userAuthenticationService.loadUserByUsername("sleroy");
        verify(personDAO, times(1)).selectByCriteria(Matchers.any(PersonCriteria.class));
        assertEquals("sleroy", username.getUsername());
        assertEquals("demoPassword", username.getPassword());
        assertEquals("DEMO", username.getAuthorities().iterator().next().getAuthority());
    }
}
