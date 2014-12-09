/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import com.tocea.core.generalplugin.BadConfigurationException;
import com.tocea.core.generalplugin.IEventConnector;
import com.tocea.core.generalplugin.IOrganisationConnector;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.komea.event.query.service.EventQueryManagerService;
import org.komea.event.storage.service.EventStorageService;
import org.komea.orientdb.session.impl.OrientDocumentDatabaseFactory;
import org.komea.orientdb.session.impl.TestDatabaseConfiguration;

/**
 *
 * @author rgalerme
 */
public class JiraConnector implements IEventConnector<JiraConfiguration>, IOrganisationConnector<JiraConfiguration> {

    private JiraServerAPI jiraAPI;
    private KomeaServerAPI komeaAPI;
    private OrientDocumentDatabaseFactory orient;

    public JiraConnector() {

    }

    @Override
    public void updateEvents(Date _lastLaunchDate) {
        JiraService.importNewIssue(jiraAPI, komeaAPI, _lastLaunchDate);
        JiraService.importUpdateIssue(jiraAPI, komeaAPI, _lastLaunchDate);

    }

    @Override
    public void updateOrganisation() {

    }

    @Override
    public void initConnector(JiraConfiguration _configuration) {
        try {
            TestDatabaseConfiguration dbc = new TestDatabaseConfiguration();
            orient = new OrientDocumentDatabaseFactory(dbc);
            EventStorageService event = new EventStorageService(orient);
            komeaAPI = new KomeaServerAPI(event);
            jiraAPI = JiraServerAPI.getNewInstance(_configuration);
        } catch (BadConfigurationException ex) {
            Logger.getLogger(JiraConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public EventQueryManagerService getQueryService() {
        return new EventQueryManagerService(orient);
    }

    @Override
    public void initOrganisation() {

    }

}
