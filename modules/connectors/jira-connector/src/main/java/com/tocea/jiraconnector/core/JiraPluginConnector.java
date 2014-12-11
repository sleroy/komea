/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.jiraconnector.core;

import com.tocea.jiraconnector.generalplugin.BadConfigurationException;
import com.tocea.jiraconnector.generalplugin.IEventConnector;
import com.tocea.jiraconnector.generalplugin.IOrganisationConnector;
import com.tocea.jiraconnector.service.JiraEventConnector;
import com.tocea.jiraconnector.service.JiraProcessConnector;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.komea.event.query.service.EventQueryManagerService;
import org.komea.orientdb.session.impl.TestDatabaseConfiguration;
import org.komea.software.model.impl.MinimalCompanySchema;

/**
 *
 * @author rgalerme
 */
public class JiraPluginConnector implements IEventConnector<JiraConfiguration>, IOrganisationConnector<JiraConfiguration> {

    private JiraEventConnector jCon;
    private JiraProcessConnector jPross;
    private KomeaServerContext komeaContext;

    public JiraPluginConnector() {

    }

    @Override
    public void updateEvents(Date _lastLaunchDate) {
        
        jCon.importNewIssue(_lastLaunchDate);
        jCon.importUpdateIssue(_lastLaunchDate);
        

    }

    @Override
    public void updateOrganisation() {
        jPross.updateJiraOrganisation();
    }

    @Override
    public void initConnector(JiraConfiguration _configuration) {

        JiraServerContext initJiraConnector = initJiraConnector(_configuration);
        KomeaServerContext initKomeaconnector = initKomeaconnector();
        jCon = new JiraEventConnector(initJiraConnector, initKomeaconnector);
        jPross = new JiraProcessConnector(initJiraConnector, initKomeaconnector);

    }

    private JiraServerContext initJiraConnector(JiraConfiguration _configuration) {
        try {
            return JiraServerContext.getNewInstance(_configuration);
        } catch (BadConfigurationException ex) {
            Logger.getLogger(JiraPluginConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private KomeaServerContext initKomeaconnector() {
        TestDatabaseConfiguration dbc = new TestDatabaseConfiguration();
        JiraSchema jiraSchema = new JiraSchema(new MinimalCompanySchema());
        return new KomeaServerContext(dbc, jiraSchema);
    }

    public EventQueryManagerService getQueryService() {
        return komeaContext.getQueryService();
    }

}
