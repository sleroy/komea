
package org.komea.experimental;


import java.io.IOException;

import org.apache.commons.lang.Validate;
import org.komea.connectors.git.events.GitConnectorConfiguration;
import org.komea.connectors.git.events.GitEventsConnector;
import org.komea.event.storage.IEventStorage;
import org.komea.event.storage.impl.EventStorage;
import org.springframework.orientdb.session.impl.RemoteDatabaseConfiguration;

public class ApplicationAnalyzer
{
    
    private final ApplicationConfiguration configuration;
    private final KomeaConfiguration       komea;
    
    private IEventStorage                  eventStorage;
    
    public ApplicationAnalyzer(final ApplicationConfiguration configuration, final KomeaConfiguration komea) {
    
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
    
//        Validate.isTrue(this.eventStorage != null, "Database are not connected.");
//        
//        JiraConfiguration config = new JiraConfiguration(this.configuration.getJiraUrl());
//        JiraEventsConnector connector = new JiraEventsConnector(8, config, this.eventStorage);
//        
//        connector.process();
    }
    
    @Override
    public String toString() {
    
        return this.configuration.getName();
    }
    
}
