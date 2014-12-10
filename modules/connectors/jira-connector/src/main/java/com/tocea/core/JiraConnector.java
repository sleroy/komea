/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.komea.event.query.impl.EventQueryManager;
import org.komea.event.storage.impl.EventStorage;
import org.springframework.orientdb.orm.session.IOrientSessionFactory;
import org.springframework.orientdb.session.impl.OrientSessionFactory;
import org.springframework.orientdb.session.impl.TestDatabaseConfiguration;

import com.tocea.core.generalplugin.BadConfigurationException;
import com.tocea.core.generalplugin.IEventConnector;
import com.tocea.core.generalplugin.IOrganisationConnector;

/**
 *
 * @author rgalerme
 */
public class JiraConnector implements IEventConnector<JiraConfiguration>, IOrganisationConnector<JiraConfiguration> {

	private JiraServerAPI	      jiraAPI;
	private KomeaServerAPI	      komeaAPI;
	private IOrientSessionFactory	orient;

	public JiraConnector() {

	}

	public EventQueryManager getQueryService() {
		return new EventQueryManager(this.orient);
	}

	@Override
	public void initConnector(final JiraConfiguration _configuration) {
		try {
			final TestDatabaseConfiguration dbc = new TestDatabaseConfiguration();
			this.orient = new OrientSessionFactory(dbc);
			final EventStorage event = new EventStorage(this.orient);
			this.komeaAPI = new KomeaServerAPI(event);
			this.jiraAPI = JiraServerAPI.getNewInstance(_configuration);
		} catch (final BadConfigurationException ex) {
			Logger.getLogger(JiraConnector.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void initOrganisation() {

	}

	@Override
	public void updateEvents(final Date _lastLaunchDate) {
		JiraService.importNewIssue(this.jiraAPI, this.komeaAPI, _lastLaunchDate);
		JiraService.importUpdateIssue(this.jiraAPI, this.komeaAPI, _lastLaunchDate);

	}

	@Override
	public void updateOrganisation() {

	}

}
