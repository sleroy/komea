/**
 *
 */
package org.komea.connectors.git.events;

import java.io.Serializable;
import java.util.Map;

import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitEvent;
import org.komea.core.utils.PojoToMap;
import org.komea.event.model.beans.ComplexEvent;

/**
 * @author sleroy
 */
public class GitCommitConverter {
	private final PojoToMap	commitPojoToMap	= new PojoToMap();

	public ComplexEvent newCommitEvent(final IGitCommit convertGitCommit) {

		final ComplexEvent event = new ComplexEvent();
		event.setProvider(IGitEvent.PROVIDER);
		event.setEventType(IGitEvent.COMMIT);
		event.setDate(convertGitCommit.getCommitTime());
		final Map<String, Serializable> properties = commitPojoToMap
				.convertPojoInMap(convertGitCommit);
		event.setProperties(properties);
		return event;
	}
}
