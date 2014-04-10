/**
 * 
 */

package org.komea.product.plugins.testlink.core;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.testlink.api.ITestLinkAlertFactory;
import org.komea.product.plugins.testlink.api.ITestLinkAnalysisService;
import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxy;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxyFactory;
import org.komea.product.plugins.testlink.model.TestLinkProject;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class TestLinkCheckerCronTest
{
    
    
    @Mock
    private ITestLinkAlertFactory       alertFactory;
    
    
    @Mock
    private IEventPushService           alertService;
    
    
    @Mock
    private ITestLinkAnalysisService    analysisService;
    
    @Mock
    private ITestLinkServerDAO          serverConfig;
    
    @InjectMocks
    private TestLinkCheckerCron         testLinkCheckerCron;
    
    @Mock
    private ITestLinkServerProxyFactory testlinkServerFactory;
    
    
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.core.TestLinkCheckerCron#checkServers()}.
     */
    @Test
    public final void testCheckServers() throws Exception {
    
    
        final TestLinkServer testLinkServer = new TestLinkServer();
        testLinkServer.setAddress("http://");
        testLinkServer.setKey("Testlink server 1");
        when(serverConfig.selectAll()).thenReturn(Lists.newArrayList(testLinkServer));
        final ITestLinkServerProxy proxy = Mockito.mock(ITestLinkServerProxy.class);
        
        when(proxy.getListProject()).thenReturn(Lists.newArrayList(new TestLinkProject()));
        when(testlinkServerFactory.newConnector(Matchers.any(TestLinkServer.class))).thenReturn(
                proxy);
        testLinkCheckerCron.checkServers();
        verify(proxy, times(1)).getListProject(); // One invocation of getList projects
        verify(analysisService, times(1)).checkTestlinkProject(
                Matchers.any(ITestLinkServerProxy.class), Matchers.any(TestLinkProject.class));
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.core.TestLinkCheckerCron#checkTestLinkServer(org.komea.product.plugins.testlink.model.TestLinkServer)}
     * .
     */
    @Test
    public void testCheckTestLinkServer() throws Exception {
    
    
        final TestLinkServer testLinkServer = new TestLinkServer();
        testLinkServer.setAddress("http://");
        testLinkServer.setKey("Testlink server 1");
        final ITestLinkServerProxy proxy = Mockito.mock(ITestLinkServerProxy.class);
        when(proxy.getListProject()).thenReturn(Lists.newArrayList(new TestLinkProject()));
        when(testlinkServerFactory.newConnector(Matchers.any(TestLinkServer.class))).thenReturn(
                proxy);
        testLinkCheckerCron.checkTestLinkServer(testLinkServer);
        verify(proxy, times(1)).getListProject(); // One invocation of getList projects
        verify(analysisService, times(1)).checkTestlinkProject(
                Matchers.any(ITestLinkServerProxy.class), Matchers.any(TestLinkProject.class));
    }
    
}
