/**
 * 
 */

package org.komea.product.backend.service;



import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.database.model.PersonRole;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRoleDataBeanTest
{
    
    
    @Mock
    private IPersonRoleService personRoleDao;
    @InjectMocks
    private UserRoleDataBean   userRoleDataBean;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.UserRoleDataBean#init()}.
     */
    @Test
    public final void testInitWithAdmin() throws Exception {
    
    
        when(personRoleDao.selectByKey("ADMIN")).thenReturn(null);
        when(personRoleDao.selectByKey("USER")).thenReturn(new PersonRole());
        
        userRoleDataBean.init();
        final ArgumentCaptor<PersonRole> argumentCaptor = ArgumentCaptor.forClass(PersonRole.class);
        verify(personRoleDao, times(1)).saveOrUpdate(argumentCaptor.capture());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.UserRoleDataBean#init()}.
     */
    @Test
    public final void testInitWithUser() throws Exception {
    
    
        when(personRoleDao.selectByKey("USER")).thenReturn(null);
        when(personRoleDao.selectByKey("ADMIN")).thenReturn(new PersonRole());
        
        userRoleDataBean.init();
        final ArgumentCaptor<PersonRole> argumentCaptor = ArgumentCaptor.forClass(PersonRole.class);
        verify(personRoleDao, times(1)).saveOrUpdate(argumentCaptor.capture());
    }
}
