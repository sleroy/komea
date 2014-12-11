/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.jiraconnector.core;

import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.event.query.impl.EventQueryManager;
import org.komea.event.storage.IEventStorage;
import org.komea.event.storage.impl.EventStorage;
import org.springframework.orientdb.session.impl.OrientSessionFactory;

/**
 *
 * @author rgalerme
 */
public class KomeaServerContext {

    public static final String EVENT_NEW_BUG = "new_bug";
    public static final String EVENT_UPDATE_BUG = "update_bug";
    public static final String PROVIDER_BUG = "jira";

    private final IEventStorage eventStorage;
    private final JiraSchema schemaAPI;
    private final OrientSessionFactory orientSession;
//    private final OrientDocumentDatabaseFactory orient;

    public KomeaServerContext(OrientSessionFactory db, JiraSchema schemaAPI) {
//        this.db = db;
        this.orientSession = db;
        this.eventStorage = new EventStorage(orientSession);
        this.schemaAPI = schemaAPI;
    }

    public JiraSchema getSchemaAPI() {
        return schemaAPI;
    }

    public IEventStorage getEventStorage() {
        return eventStorage;
    }

    public IKomeaGraphStorage getNewCompanyStorage() {

        return new OKomeaGraphStorage(schemaAPI.getSchema(), orientSession.getGraphTx());
    }

    public EventQueryManager getQueryService() {
        return new EventQueryManager(orientSession);
    }
}
