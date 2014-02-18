/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.testlink;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.komea.backend.plugins.testlink.api.ITestLinkServerManagerService;
import org.komea.backend.plugins.testlink.api.ITestLinkServerProxy;
import org.komea.product.backend.fs.IObjectStorage;
import org.komea.product.backend.service.IPluginStorageService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.dto.EventSimpleDto;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author rgalerme
 */
public class TestLinkCheckerBeanTest {
    
    @Test
    public void testSomeMethodWithoutServer() {
    
        // Mockito.mock(null)
        TestLinkCheckerBean checkerBean = new TestLinkCheckerBean();
        
        checkerBean.setAlertFactory(new TestLinkAlertFactory());
        IEventPushService alertService = Mockito.mock(IEventPushService.class);
        checkerBean.setAlertService(alertService);
        
        // creation structure internet du plugin
        ITestLinkServerManagerService serverManager = new TestLinkServerManagerService();
        
        // simulation du système de configuration avec injection d'un vrai server testlink de test
        IPluginStorageService pluginStorage = Mockito.mock(IPluginStorageService.class);
        IObjectStorage objectStorage = Mockito.mock(IObjectStorage.class);
        
        TestLinkStorageConfiguration storConf = new TestLinkStorageConfiguration();
        List<TestLinkServer> configurations = new ArrayList<TestLinkServer>();
        TestLinkServer testLinkServer = new TestLinkServer("http://ares.tocea/testlink/lib/api/xmlrpc.php",
                "2dec70df08045278463817fb15d79c4d");
        configurations.add(testLinkServer);
        // configurations.add(new TestLinkServer("http://127.0.0.1:8080/testlink/lib/api/xmlrpc/v1/xmlrpc.php",
        // "cf86c231b7c909f15b4d61e12ace5507"));
        storConf.setConfigurations(configurations);
        
        Mockito.when(objectStorage.get()).thenReturn(storConf);
        Mockito.when(pluginStorage.registerStorage("TESTLINK", TestLinkStorageConfiguration.class)).thenReturn(objectStorage);
        
        serverManager.setPluginStorage(pluginStorage);
        ((TestLinkServerManagerService) serverManager).init();
        
        // //////////////////////////////////////////////
        // mock du server testlink
        TestLinkServerProxyFactory testLinkServerProxyFactory = Mockito.mock(TestLinkServerProxyFactory.class);
        ITestLinkServerProxy proxy = Mockito.mock(ITestLinkServerProxy.class);
        
        // mise en place des données classique
        List<TestLinkProject> listProject = new ArrayList<TestLinkProject>();
        listProject.add(new TestLinkProject(25, "ilum"));
        listProject.add(new TestLinkProject(147, "voss"));
        
        List<TestLinkTestCase> totalTestsIlum = new ArrayList<TestLinkTestCase>();
        List<TestLinkTestCase> totalTestsVoss = new ArrayList<TestLinkTestCase>();
        
        totalTestsIlum.add(new TestLinkTestCase(1, "p"));
        totalTestsIlum.add(new TestLinkTestCase(2, "p"));
        totalTestsIlum.add(new TestLinkTestCase(3, "n"));
        totalTestsIlum.add(new TestLinkTestCase(4, "n"));
        totalTestsIlum.add(new TestLinkTestCase(5, "b"));
        totalTestsIlum.add(new TestLinkTestCase(6, "f"));
        totalTestsIlum.add(new TestLinkTestCase(7, "u"));
        totalTestsIlum.add(new TestLinkTestCase(8, "u"));
        totalTestsIlum.add(new TestLinkTestCase(9, "b"));
        
        totalTestsVoss.add(new TestLinkTestCase(10, "p"));
        totalTestsVoss.add(new TestLinkTestCase(11, "n"));
        totalTestsVoss.add(new TestLinkTestCase(12, "u"));
        totalTestsVoss.add(new TestLinkTestCase(13, "u"));
        totalTestsVoss.add(new TestLinkTestCase(14, "u"));
        totalTestsVoss.add(new TestLinkTestCase(15, "f"));
        totalTestsVoss.add(new TestLinkTestCase(16, "f"));
        totalTestsVoss.add(new TestLinkTestCase(17, "b"));
        totalTestsVoss.add(new TestLinkTestCase(18, "n"));
        totalTestsVoss.add(new TestLinkTestCase(19, "n"));
        
        // ////////////////////////////////////////////////
        Mockito.when(proxy.getListProject()).thenReturn(listProject);
        Mockito.when(proxy.getTotalTests(listProject.get(0))).thenReturn(totalTestsIlum);
        Mockito.when(proxy.getTotalTests(listProject.get(1))).thenReturn(totalTestsVoss);
        
        Mockito.when(testLinkServerProxyFactory.newConnector(testLinkServer)).thenReturn(proxy);
        
        serverManager.setServerProxyFactory(testLinkServerProxyFactory);
        // /////////////////////////////////
        
        ArgumentCaptor<EventSimpleDto> forClass = ArgumentCaptor.forClass(EventSimpleDto.class);
        
        checkerBean.setServerConfig(serverManager);
        checkerBean.checkServers();
        Mockito.verify(alertService, Mockito.times(12)).sendEventDto(forClass.capture());
        List<EventSimpleDto> allValues = forClass.getAllValues();
        // afficher les alerts
        for (EventSimpleDto iAlert : allValues) {
            System.out.println(iAlert.getProject() + " : " + iAlert.getMessage() + " : " + iAlert.getValue());
        }
    }
    
    // @Test
    // public void testSomeMethodWithServer() {
    // // Mockito.mock(null)
    // TestLinkCheckerBean checkerBean = new TestLinkCheckerBean();
    //
    // checkerBean.setAlertFactory(new TestLinkAlertFactory());
    // IEventPushService alertService = Mockito.mock(IEventPushService.class);
    // checkerBean.setAlertService(alertService);
    //
    // // creation structure internet du plugin
    // ITestLinkServerManagerService serverManager = new TestLinkServerManagerService();
    //
    // //simulation du système de configuration avec injection d'un vrai server testlink de test
    // IPluginStorageService pluginStorage = Mockito.mock(IPluginStorageService.class);
    // IObjectStorage objectStorage = Mockito.mock(IObjectStorage.class);
    //
    // TestLinkStorageConfiguration storConf = new TestLinkStorageConfiguration();
    // List<TestLinkServer> configurations = new ArrayList<TestLinkServer>();
    // configurations.add(new TestLinkServer("http://ares.tocea/testlink/lib/api/xmlrpc.php", "2dec70df08045278463817fb15d79c4d"));
    // // configurations.add(new TestLinkServer("http://127.0.0.1:8080/testlink/lib/api/xmlrpc/v1/xmlrpc.php",
    // "cf86c231b7c909f15b4d61e12ace5507"));
    // storConf.setConfigurations(configurations);
    //
    // Mockito.when(objectStorage.get()).thenReturn(storConf);
    // Mockito.when(pluginStorage.registerStorage("TESTLINK", TestLinkStorageConfiguration.class)).thenReturn(objectStorage);
    //
    // serverManager.setPluginStorage(pluginStorage);
    // ((TestLinkServerManagerService) serverManager).init();
    //
    // ////////////////////////////////////////////////
    // TestLinkServerProxyFactory testLinkServerProxyFactory = new TestLinkServerProxyFactory();
    // serverManager.setServerProxyFactory(testLinkServerProxyFactory);
    // ///////////////////////////////////
    //
    // ArgumentCaptor<IEvent> forClass = ArgumentCaptor.forClass(IEvent.class);
    //
    // checkerBean.setServerConfig(serverManager);
    // checkerBean.checkServers();
    // Mockito.verify(alertService, Mockito.times(54)).sendEvent(forClass.capture());
    // List<IEvent> allValues = forClass.getAllValues();
    // // affiche les alerts
    // for (IEvent iAlert : allValues) {
    // System.out.println(iAlert.getProject() + " : " + iAlert.getMessage() + " : " + iAlert.getValue());
    //
    // }
    //
    // }
}
