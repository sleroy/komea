/**
 * 
 */

package org.komea.product.plugins.bugzilla.datasource;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBZServerProxy;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.j2bugzilla.base.Bug;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



/**
 * This is AN UNIT TEST.
 * 
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class BugZillaDataSourceTest
{
    
    
    @Mock
    private IBZConfigurationDAO            bugZillaConfiguration;
    
    
    @InjectMocks
    private BugZillaDataSource             bugZillaDataSource;
    
    
    @Spy
    private final BugZillaToIssueConvertor bugZillaToIssueConvertor =
                                                                            new BugZillaToIssueConvertor();
    
    
    @Mock
    private IProjectService                projectService;
    @Mock
    private IBZServerProxyFactory          proxyFactory;
    
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugzilla.datasource.BugZillaDataSource#fetchData()}.
     */
    @Test
    public final void testFetchData() throws Exception {
    
    
        when(bugZillaConfiguration.selectAll()).thenReturn(Lists.newArrayList(fakeConfiguration()));
        when(projectService.getOrCreate(Mockito.anyString())).thenReturn(new Project());
        final IBZServerProxy proxy = mock(IBZServerProxy.class);
        when(proxyFactory.newConnector(Mockito.any(BZServerConfiguration.class))).thenReturn(proxy);
        when(proxy.getProductNames()).thenReturn(Lists.newArrayList("S1", "S2"));
        
        
        final Bug bug = mock(Bug.class);
        when(proxy.getBugs("S1")).thenReturn(Lists.newArrayList(bug, bug, bug));
        when(proxy.getBugs("S2")).thenReturn(Lists.newArrayList(bug, bug, bug));
        
        // EXECUTION WITH PROXY CONTAINING TWO PROJECTS , with both 3 bugs
        final IIssuePlugin fetchData = bugZillaDataSource.fetchData();
        assertEquals(6, fetchData.getData().size());
        verify(bugZillaConfiguration, times(1)).selectAll();
        verify(projectService, times(2)).selectByKey(Mockito.anyString());
        verify(projectService, times(2)).getOrCreate(Mockito.anyString());
    }
    
    
    /**
     * @return
     */
    private BZServerConfiguration fakeConfiguration() {
    
    
        final BZServerConfiguration bzServerConfiguration = new BZServerConfiguration();
        
        bzServerConfiguration.setAddress("http://eos/bugzilla/");
        bzServerConfiguration.setLogin("jeremie.guidoux@tocea.com");
        bzServerConfiguration.setPassword("tocea35");
        bzServerConfiguration.setReminderAlert(10);
        
        return bzServerConfiguration;
    }
    
}
