/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
import org.komea.event.query.impl.EventQueryManager;

/**
 *
 * @author rgalerme
 */
public class JiraConnectorTest {

	public JiraConnectorTest() {
	}

	@Before
	public void setUp() {
	}

	@Test
	public void testUpdateEvents() {
		final JiraConnector jc = new JiraConnector();
		final JiraConfiguration jconf = new JiraConfiguration("https://jira.atlassian.com/", null, null);
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		try {
			final Date parse = dateFormat.parse("2014/12/11 11:00");
			jc.initConnector(jconf);
			jc.updateEvents(parse);

			final EventQueryManager queryService = jc.getQueryService();
			final long countEventsOfType = queryService.countEventsOfType(KomeaService.EVENT_NEW_BUG);
			assertTrue(countEventsOfType > 0);
		} catch (final ParseException ex) {
			Logger.getLogger(JiraConnectorTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
