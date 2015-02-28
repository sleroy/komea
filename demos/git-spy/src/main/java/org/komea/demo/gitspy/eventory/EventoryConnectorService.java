/**
 *
 */
package org.komea.demo.gitspy.eventory;

import java.net.ConnectException;
import java.net.URISyntaxException;

import org.komea.connectors.sdk.rest.impl.EventoryClientAPI;
import org.komea.connectors.sdk.rest.impl.IEventoryClientAPI;
import org.komea.demo.gitspy.configuration.GitSpyConfigurationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class defines the service to obtain a connection to the eventory server.
 * @author sleroy
 *
 */
@Service
public class EventoryConnectorService {
	@Autowired
	private GitSpyConfigurationBean configuration;

	private static final Logger LOGGER = LoggerFactory.getLogger(EventoryConnectorService.class);

	public IEventoryClientAPI newConnection() {
		final EventoryClientAPI eventoryClientAPI = new EventoryClientAPI();
		try {
			eventoryClientAPI.setServerBaseURL(this.configuration.getEventoryURL());
		} catch (ConnectException | URISyntaxException e) {
			LOGGER.error("Could not obtain a connection to the event server", e);
		}
		return eventoryClientAPI;

	}
}
