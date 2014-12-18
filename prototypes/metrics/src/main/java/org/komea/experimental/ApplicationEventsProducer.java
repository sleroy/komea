
package org.komea.experimental;


import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.Validate;
import org.komea.connectors.git.events.GitConnectorConfiguration;
import org.komea.connectors.git.events.GitEventsConnector;
import org.komea.connectors.jira.JiraConfiguration;
import org.komea.connectors.jira.JiraEventsConnector;
import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.core.exceptions.KomeaRuntimeException;
import org.komea.event.storage.IEventStorage;
import org.komea.event.storage.impl.EventStorage;
import org.komea.experimental.model.KomeaConfiguration;
import org.komea.experimental.model.SoftwareFactoryConfiguration;
import org.springframework.orientdb.session.impl.RemoteDatabaseConfiguration;

public class ApplicationEventsProducer
{
    
    private final SoftwareFactoryConfiguration configuration;
    private final KomeaConfiguration           komea;
    
    private IEventStorage                      eventStorage;
    
    public ApplicationEventsProducer(final SoftwareFactoryConfiguration configuration, final KomeaConfiguration komea) {
    
        super();
        this.configuration = configuration;
        this.komea = komea;
    }
    
    public void connect(final String user, final String password) {
    
        if (this.eventStorage == null) {
            RemoteDatabaseConfiguration database = new RemoteDatabaseConfiguration(this.komea.getEventsDbUrl(), "events");
            database.setPassword(password);
            database.setUsername(user);
            this.eventStorage = new EventStorage(database);
            
        }
    }
    
    public void close() throws IOException {
    
        if (this.eventStorage == null) {
            this.eventStorage.close();
        }
    }
    
    public void pushGitEvents() {
    
        Validate.isTrue(this.eventStorage != null, "Database are not connected.");
        GitConnectorConfiguration config = new GitConnectorConfiguration("https://github.com/mongodb/mongo.git",
                this.configuration.getName(), this.configuration.getRepositoryFolder(), null, null);
        
        GitEventsConnector gitconnector = new GitEventsConnector(this.eventStorage);
        gitconnector.launch(config);
        
    }
    
    public void pushJiraEvents() {
    
        Validate.isTrue(this.eventStorage != null, "Database are not connected.");
        
        JiraConfiguration config = new JiraConfiguration(this.configuration.getJiraUrl());
        
        JiraEventsConnector jira = new JiraEventsConnector(this.eventStorage);
        try {
            jira.push(config, new Date(1900, 1, 1));
        } catch (BadConfigurationException e) {
            throw new KomeaRuntimeException(e);
        }
        
    }
    
    @Override
    public String toString() {
    
        return this.configuration.getName();
    }
    
}
