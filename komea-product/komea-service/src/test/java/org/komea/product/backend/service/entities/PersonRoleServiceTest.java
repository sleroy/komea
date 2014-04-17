/**
 * 
 */

package org.komea.product.backend.service.entities;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.generic.IDAOEventRegistry;
import org.komea.product.database.dao.PersonRoleDao;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PersonRoleServiceTest
{
    
    
    @Mock
    private IDAOEventRegistry daoEventRegistry;
    
    
    @Mock
    private PersonRoleDao     requiredDAO;
    @InjectMocks
    private PersonRoleService personRoleService;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.entities.PersonRoleService#getAdminRole()}.
     */
    @Test
    public final void testGetAdminRole() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.entities.PersonRoleService#getDefaultUserRole()}.
     */
    @Test
    public final void testGetDefaultUserRole() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
}
