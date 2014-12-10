
package org.komea.connectors.git.events;


import java.io.Serializable;
import java.util.Map;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;
import org.komea.connectors.git.impl.GitCommit;
import org.komea.core.utils.PojoToMap;
import org.komea.event.model.api.IBasicEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.storage.api.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CommitEventProducer implements IGitCommitProcessor
{
  

    private static final Logger LOGGER = LoggerFactory.getLogger(CommitEventProducer.class);
    
    private final IEventStorage eventStorage;
    
    private final PojoToMap     commitPojoToMap;
    
    public CommitEventProducer(final IEventStorage eventStorage) {
    
        this.eventStorage = eventStorage;
        this.commitPojoToMap = new PojoToMap();
    }
    
    @Override
    public void process(final RevCommit commit, final RevWalk revWalk, final IGitCommit convertGitCommit) {
    
        try {
            this.eventStorage.storeEvent(newCommitEvent(convertGitCommit));
            
        } catch (final Exception e) {
            LOGGER.error("GIT Commit exception {}", e.getMessage(), e);
        }
    }
    
    private IBasicEvent newCommitEvent(final IGitCommit convertGitCommit) {
    
        final ComplexEvent event = new ComplexEvent();
        event.setProvider(GitCommit.GIT);
        event.setEventType(GitCommit.COMMIT_EVENT_TYPE);
        event.setDate(convertGitCommit.getCommitTime());
        final Map<String, Serializable> properties = this.commitPojoToMap.convertPojoInMap(convertGitCommit);
        event.setProperties(properties);
        return event;
    }
    
}
