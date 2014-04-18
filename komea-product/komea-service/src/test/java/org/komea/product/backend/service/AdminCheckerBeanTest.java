/**
 * 
 */

package org.komea.product.backend.service;



import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.auth.IPasswordEncoder;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonRoleService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.database.model.Person;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminCheckerBeanTest
{
    
    
    @InjectMocks
    private AdminCheckerBean    adminCheckerBean;
    @Mock
    private IPersonGroupService iPersonGroupService;
    
    @Mock
    private IPersonRoleService  iPersonRoleService;
    
    @Mock
    private IPersonService      iPersonService;
    
    @Mock
    private IPasswordEncoder    passwordEncoder;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.AdminCheckerBean#init()}.
     */
    @Test
    public final void testInit() throws Exception {
    
    
        when(iPersonService.getAdministrators()).thenReturn(Collections.EMPTY_LIST);
        adminCheckerBean.init();
        verify(passwordEncoder, times(1)).encodePassword(Matchers.anyString());
        verify(iPersonService, times(1)).saveOrUpdate(Matchers.any(Person.class));
    }
}
