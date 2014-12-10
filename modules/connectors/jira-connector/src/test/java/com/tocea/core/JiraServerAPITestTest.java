/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.komea.event.query.impl.EventQueryManager;
import org.springframework.orientdb.session.impl.OrientSessionFactory;
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration;

/**
 *
 * @author rgalerme
 */
public class JiraServerAPITestTest {

	public JiraServerAPITestTest() {
	}

	@Test
	public void testNewBugs() {
		final TestDatabaseConfiguration dbc = new TestDatabaseConfiguration();
		// ORIENTDB;

		final OrientSessionFactory ogf = new OrientSessionFactory(dbc);
		ogf.getOrCreateDB();
		final EventQueryManager queryservice = new EventQueryManager(ogf);
		queryservice.countEventsOfType("Event_new_jira_bug");

	}

	@Test
	public void testSomeMethod() {
		try {
			final JiraServerAPITest js = new JiraServerAPITest();
			js.test();
		} catch (final ParseException ex) {
			Logger.getLogger(JiraServerAPITestTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
