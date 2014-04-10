/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.plugins.bugzilla;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.plugins.bugzilla.api.IBugZillaAlertFactory;
import org.komea.product.plugins.bugzilla.api.IBugZillaConfigurationService;
import org.komea.product.plugins.bugzilla.api.IBugZillaServerProxy;
import org.komea.product.plugins.bugzilla.api.IBugZillaServerProxyFactory;
import org.komea.product.plugins.bugzilla.core.BugZillaAlertFactory;
import org.komea.product.plugins.bugzilla.core.BugZillaCheckerBean;
import org.komea.product.plugins.bugzilla.core.BugZillaConfiguration;
import org.komea.product.plugins.bugzilla.core.BugZillaConfigurationService;
import org.komea.product.plugins.bugzilla.data.BugZillaContext;
import org.komea.product.plugins.bugzilla.data.BugZillaServer;
import org.komea.product.plugins.bugzilla.data.BugzillaBug;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author rgalerme
 */
// @RunWith(SpringJUnit4ClassRunner.class)
public class BugZillaPluginTest {

    // @Mock
    // private IBugZillaConfigurationService conf;
    @Test
    public void testPluginWithoutBugZilla() {

        System.out.println("Debut du test");
        // Mockito.when(conf.getServers()).then(new Answer<List<I>)
        final BugZillaCheckerBean bbean = new BugZillaCheckerBean();
        // bbean.setAlertFactory(Mockito.mock(IBugZillaAlertFactory.class));

        final IBugZillaAlertFactory alertFactory = new BugZillaAlertFactory();
        bbean.setAlertFactory(alertFactory);
        final IEventPushService mockAlertService = Mockito.mock(IEventPushService.class);
        final ArgumentCaptor<EventSimpleDto> forClass = ArgumentCaptor.forClass(EventSimpleDto.class);
        bbean.setAlertService(mockAlertService);

        final IBugZillaConfigurationService bugZillaConfiguration = new BugZillaConfigurationService();
        final IPluginStorageService plService = Mockito.mock(IPluginStorageService.class);
        bugZillaConfiguration.setPluginStorage(plService);

        // mock du server bugzilla
        final IBugZillaServerProxyFactory bzfact = Mockito.mock(IBugZillaServerProxyFactory.class);
        final IBugZillaServerProxy j2connector = Mockito.mock(IBugZillaServerProxy.class);

        final List<String> listProjet = new ArrayList<String>();
        listProjet.add("Makeb");
        listProjet.add("Cornelia");

        Mockito.when(j2connector.getListProjects()).thenReturn(listProjet);

        final List<BugzillaBug> listMakeb = new ArrayList<BugzillaBug>();
        Date d = new Date();
        Date d1 = new Date();
        Date d2 = new Date();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {

            d = simpleDateFormat.parse("15/10/2013");
            d1 = simpleDateFormat.parse("15/12/2013");
            d2 = simpleDateFormat.parse("01/01/2014");
        } catch (final ParseException ex) {
            Logger.getLogger(BugZillaPluginTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        listMakeb.add(new BugzillaBug(1, "NEW", true, d, true, "", "")); // id , status, ouvert, date creation , assigne
        listMakeb.add(new BugzillaBug(2, "CONFIRMED", true, d, false, "", ""));
        listMakeb.add(new BugzillaBug(3, "UNCONFIRMED", true, d2, false, "", ""));
        listMakeb.add(new BugzillaBug(4, "REOPENED", true, d2, false, "", ""));
        listMakeb.add(new BugzillaBug(5, "RESOLVED", false, d1, true, "", ""));
        listMakeb.add(new BugzillaBug(6, "VERIFIED", false, d1, true, "", ""));

        final List<BugzillaBug> listCornelia = new ArrayList<BugzillaBug>();
        listCornelia.add(new BugzillaBug(7, "REOPENED", false, d2, false, "", ""));

        Mockito.when(j2connector.getListBugs("Makeb")).thenReturn(listMakeb);
        Mockito.when(j2connector.getListBugs("Cornelia")).thenReturn(listCornelia);

        // ///////////////////////////////////////////
        bugZillaConfiguration.setServerProxyFactory(bzfact);
//        bugZillaConfiguration.setServerProxyFactory(new BugZillaServerProxyFactory());

        final IObjectStorage<BugZillaConfiguration> storage = Mockito.mock(IObjectStorage.class);
        final BugZillaConfiguration bconf = Mockito.mock(BugZillaConfiguration.class);
        final BugZillaServer idServer = Mockito.mock(BugZillaServer.class);

        final BugZillaContext bc = new BugZillaContext();

        Mockito.when(idServer.getAddress()).thenReturn("http://192.168.1.88/bugzilla/");
        Mockito.when(idServer.getLogin()).thenReturn("raphael.galerme@tocea.com");
        Mockito.when(idServer.getMdp()).thenReturn("rgalerme");
        Mockito.when(idServer.getContext()).thenReturn(bc);

//        final BugZillaServer realServer = new BugZillaServer("http://eos/bugzilla/", "jeremie.guidoux@tocea.com", "tocea35", new BugZillaContext());
        final List<BugZillaServer> listBServ = new ArrayList<BugZillaServer>();
        listBServ.add(idServer);
//        listBServ.add(realServer);

        Mockito.when(bconf.getConfigurations()).thenReturn(listBServ);
        Mockito.when(storage.get()).thenReturn(bconf);
        Mockito.when(plService.registerStorage("BUGZILLA", BugZillaConfiguration.class)).thenReturn(storage);

        ((BugZillaConfigurationService) bugZillaConfiguration).init();

        bbean.setBugZillaConfiguration(bugZillaConfiguration);
        Mockito.when(bzfact.newConnector(idServer)).thenReturn(j2connector);

        bbean.checkServers();
        Mockito.verify(mockAlertService, Mockito.times(33)).sendEventDto(forClass.capture());
        System.out.println("forClass" + forClass.getAllValues());
        bbean.checkServers();
        Mockito.verify(mockAlertService, Mockito.times(66)).sendEventDto(forClass.capture());
        System.out.println("Fin du test");

    }

}
