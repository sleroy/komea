/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.jiraconnector.core;

import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.event.query.service.EventQueryManagerService;
import org.komea.event.storage.api.IEventStorage;
import org.komea.event.storage.service.EventStorageService;
import org.komea.orientdb.session.impl.DatabaseConfiguration;
import org.komea.orientdb.session.impl.OrientDocumentDatabaseFactory;

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
    private final DatabaseConfiguration db;
    private final OrientDocumentDatabaseFactory orient;

    public KomeaServerContext(DatabaseConfiguration db, JiraSchema schemaAPI) {
        this.db = db;
        this.orient = new OrientDocumentDatabaseFactory(db);
        this.eventStorage = new EventStorageService(orient);
        this.schemaAPI = schemaAPI;
    }

    public JiraSchema getSchemaAPI() {
        return schemaAPI;
    }

    public IEventStorage getEventStorage() {
        return eventStorage;
    }

    public IKomeaGraphStorage getNewCompanyStorage() {

        return new OKomeaGraphStorage(schemaAPI.getSchema(), db);
    }

    public EventQueryManagerService getQueryService() {
        return new EventQueryManagerService(orient);
    }

}
