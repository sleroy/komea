/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import org.komea.event.query.service.EventQueryManagerService;
import org.komea.event.storage.service.EventStorageService;
import org.komea.orientdb.session.impl.OrientDocumentDatabaseFactory;
import org.komea.orientdb.session.impl.TestDatabaseConfiguration;

/**
 *
 * @author rgalerme
 */
public class JiraServerAPITestTest {

    public JiraServerAPITestTest() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testSomeMethod() {
        try {
            JiraServerAPITest js = new JiraServerAPITest();
            js.test();
        } catch (ParseException ex) {
            Logger.getLogger(JiraServerAPITestTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testNewBugs() {
        TestDatabaseConfiguration dbc = new TestDatabaseConfiguration();
        // ORIENTDB;
        OrientDocumentDatabaseFactory ogf = new OrientDocumentDatabaseFactory(dbc);
        EventStorageService eventStorage = new EventStorageService(ogf);
        EventQueryManagerService queryservice = new EventQueryManagerService(ogf);
        queryservice.countEventsOfType("Event_new_jira_bug");

    }

}
