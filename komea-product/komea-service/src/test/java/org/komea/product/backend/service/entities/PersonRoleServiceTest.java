/**
 * 
 */

package org.komea.product.backend.service.entities;



import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.generic.IDAOEventRegistry;
import org.komea.product.database.dao.PersonRoleDao;
import org.komea.product.database.model.PersonRole;
import org.komea.product.database.model.PersonRoleCriteria;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonRoleServiceTest
{
    
    
    @Mock
    private IDAOEventRegistry daoEventRegistry;
    
    
    @InjectMocks
    private PersonRoleService personRoleService;
    @Mock
    private PersonRoleDao     requiredDAO;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.entities.PersonRoleService#getAdminRole()}.
     */
    @Test
    public final void testGetAdminRole() throws Exception {
    
    
        when(requiredDAO.selectByCriteria(Matchers.any(PersonRoleCriteria.class))).thenReturn(
                Collections.singletonList(new PersonRole()));
        personRoleService.getAdminRole();
        verify(requiredDAO, times(1)).selectByCriteria(Matchers.any(PersonRoleCriteria.class));
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.entities.PersonRoleService#getDefaultUserRole()}.
     */
    @Test
    public final void testGetDefaultUserRole() throws Exception {
    
    
        when(requiredDAO.selectByCriteria(Matchers.any(PersonRoleCriteria.class))).thenReturn(
                Collections.singletonList(new PersonRole()));
        personRoleService.getDefaultUserRole();
        verify(requiredDAO, times(1)).selectByCriteria(Matchers.any(PersonRoleCriteria.class));
    }
}
