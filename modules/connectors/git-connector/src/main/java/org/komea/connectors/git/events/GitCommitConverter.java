/**
 *
 */
package org.komea.connectors.git.events;

import java.util.Map;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitEvent;
import org.komea.core.utils.PojoToMap;
import org.komea.event.model.KomeaEvent;

/**
 * @author sleroy
 */
public class GitCommitConverter {

    private final PojoToMap commitPojoToMap = new PojoToMap();

    public KomeaEvent newCommitEvent(final IGitCommit convertGitCommit) {

        final KomeaEvent event = new KomeaEvent();
        event.setProvider(IGitEvent.PROVIDER);
        event.setEventType(IGitEvent.COMMIT);
        event.setDate(convertGitCommit.getCommitTime());
        final Map<String, Object> properties = commitPojoToMap
                .convertPojoInMap(convertGitCommit);
        event.addFields(properties);
        return event;
    }
}
