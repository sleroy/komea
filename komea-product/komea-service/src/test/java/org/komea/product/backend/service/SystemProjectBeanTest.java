
package org.komea.product.backend.service;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.model.ProjectCriteria;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



@RunWith(MockitoJUnitRunner.class)
public class SystemProjectBeanTest
{
    
    
    @Mock
    private ProjectDao        projectDAO;
    @InjectMocks
    private SystemProjectBean systemProjectBean;
    
    
    
    @Test 
    public void testSystemProjectBean() throws Exception {
    
    
        systemProjectBean.init();
        Assert.assertNotNull(systemProjectBean.getSystemProject());
        
        verify(projectDAO, times(1)).selectByCriteria(Matchers.any(ProjectCriteria.class));
        
    }
}
