/**
 * 
 */

package org.komea.product.plugins.mantis.core;



import org.junit.Test;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.mantis.api.IMantisConfigurationDAO;
import org.komea.product.plugins.mantis.model.MantisServerConfiguration;
import org.komea.product.plugins.mantis.service.MantisServerProxyFactory;
import org.mockito.Mockito;

import biz.futureware.mantis.rpc.soap.client.IssueData;

import com.google.common.collect.Lists;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class MantisBugCountKPITest
{
    
    
    /**
     * Test method for {@link org.komea.product.plugins.mantis.core.MantisBugCountKPI#getResult()}.
     */
    @Test
    public void testGetResult() throws Exception {
    
    
        final MantisBugCountKPI mantisBugCountKPI =
                new MantisBugCountKPI(new IBugMatcher<IssueData>()
                {
                    
                    
                    @Override
                    public boolean matches(final IssueData _item) {
                    
                    
                        return "urgent".equals(_item.getPriority().getName());
                    }
                });
        final IMantisConfigurationDAO mock = mock(IMantisConfigurationDAO.class);
        when(mock.selectAll()).thenReturn(Lists.newArrayList(configuration()));
        final IProjectService projectService = mock(IProjectService.class);
        final Project value = new Project(1, "GNI", "NAME", "", 1, "icon");
        when(projectService.getOrCreate(Mockito.anyString())).thenReturn(value);
        mantisBugCountKPI.setMantisConfiguration(mock);
        mantisBugCountKPI.setProjectService(projectService);
        
        mantisBugCountKPI.setProxyFactory(buildFactory());
        
        
        final KpiResult result = mantisBugCountKPI.getResult();
        System.out.println(result);
    }
    
    
    private MantisServerProxyFactory buildFactory() {
    
    
        final MantisServerProxyFactory mantisServerProxyFactory = new MantisServerProxyFactory();
        final SessionFactory sessionFactory = new SessionFactory();
        sessionFactory.init();
        mantisServerProxyFactory.setSessionFactory(sessionFactory);
        return mantisServerProxyFactory;
    }
    
    
    /**
     * @return
     */
    private MantisServerConfiguration configuration() {
    
    
        final MantisServerConfiguration serv = new MantisServerConfiguration();
        serv.setAddress("http://bugreport.tocea.fr/api/soap/mantisconnect.php");
        serv.setLogin("sleroy");
        serv.setPassword("ensapono");
        return serv;
    }
}
