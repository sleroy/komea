package org.komea.connectors.git.events;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;
import org.komea.connectors.git.IGitEvent;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CommitEventProducer implements IGitCommitProcessor {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CommitEventProducer.class);

    private final IEventStorage eventStorage;

    private final GitCommitConverter converter = new GitCommitConverter();

    public CommitEventProducer(final IEventStorage eventStorage) {

        this.eventStorage = eventStorage;
        this.eventStorage.declareEventType(IGitEvent.COMMIT);

    }

    @Override
    public void process(final RevCommit commit, final RevWalk walk,
            final IGitCommit convertGitCommit) {
        eventStorage.storeComplexEvent(converter.newCommitEvent(convertGitCommit));
    }
}
