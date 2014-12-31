package org.komea.experimental;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.Validate;
import org.komea.connectors.git.events.GitConnectorConfiguration;
import org.komea.connectors.git.events.GitEventsConnector;
import org.komea.connectors.jira.JiraConfiguration;
import org.komea.connectors.jira.JiraEventsConnector;
import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.utils.JiraServerFactory;
import org.komea.core.exceptions.KomeaRuntimeException;
import org.komea.event.storage.IEventStorage;
import org.komea.event.storage.impl.EventStorage;
import org.komea.event.storage.orientdb.impl.OEventDBFactory;
import org.komea.experimental.model.KomeaConfiguration;
import org.komea.experimental.model.SoftwareFactoryConfiguration;
import org.springframework.orientdb.session.impl.RemoteDatabaseConfiguration;

public class ApplicationEventsProducer {

	private final SoftwareFactoryConfiguration	configuration;
	private final KomeaConfiguration	       komea;

	private IEventStorage	                   eventStorage;

	public ApplicationEventsProducer(final SoftwareFactoryConfiguration configuration, final KomeaConfiguration komea) {

		super();
		this.configuration = configuration;
		this.komea = komea;
	}

	public void close() throws IOException {

		if (eventStorage == null) {
			eventStorage.close();
		}
	}

	public void connect(final String user, final String password) {

		if (eventStorage == null) {
			final RemoteDatabaseConfiguration database = new RemoteDatabaseConfiguration(komea.getEventsDbUrl(),
					"events");
			database.setPassword(password);
			database.setUsername(user);
			eventStorage = new EventStorage(new OEventDBFactory(database));

		}
	}

	public void pushGitEvents() {

		Validate.isTrue(eventStorage != null, "Database are not connected.");
		final GitConnectorConfiguration config = new GitConnectorConfiguration("https://github.com/mongodb/mongo.git",
				configuration.getName(), configuration.getRepositoryFolder(), null, null);

		final GitEventsConnector gitconnector = new GitEventsConnector(eventStorage);
		gitconnector.launch(config);

	}

	public void pushJiraEvents() {

		Validate.isTrue(eventStorage != null, "Database are not connected.");

		final JiraConfiguration config = new JiraConfiguration(configuration.getJiraUrl());

		final JiraEventsConnector jira = new JiraEventsConnector(eventStorage, JiraServerFactory.getInstance());
		try {
			jira.push(config, new Date(1900, 1, 1));
		} catch (final BadConfigurationException e) {
			throw new KomeaRuntimeException(e);
		}

	}

	@Override
	public String toString() {

		return configuration.getName();
	}

}
