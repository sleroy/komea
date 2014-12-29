
package org.komea.connectors.git.events;


import org.apache.commons.lang.Validate;
import org.eclipse.jgit.api.Git;
import org.komea.connectors.git.IGitCommitProcessor;
import org.komea.connectors.git.IGitRepository;
import org.komea.connectors.git.exceptions.GitRuntimeException;
import org.komea.connectors.git.impl.GitRepository;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitEventsConnector
{

    private static final Logger LOGGER = LoggerFactory.getLogger(GitEventsConnector.class);
    private final IEventStorage storage;

    public GitEventsConnector(final IEventStorage storage) {

        super();
        this.storage = storage;
    }

    private IGitCommitProcessor buildCommitsProcessor(final GitConnectorConfiguration options, final Git git) {

        final TimedCompositeCommitProcessor processor = new TimedCompositeCommitProcessor(options.getSince(), options.getUntil());
        processor.addCommitProcessor(new CommitProjectSetter(options.getProject()));
        processor.addCommitProcessor(new CommitEventProducer(this.storage));
        processor.addCommitProcessor(new FileModificationEventProducer(this.storage));
        processor.addCommitProcessor(new GitEventTagProducer( this.storage,git));
        return processor;
    }

    public void launch(final GitConnectorConfiguration options) {
    
        Validate.notNull(options.getRepositoryFolder());
        Validate.isTrue(options.getRepositoryFolder().exists());
        final IGitRepository gitRepository = new GitRepository(options.getRepositoryFolder(), options.getRepositoryUrl());
        try {

            LOGGER.info("Processing commits of GIT repository");
            final IGitCommitProcessor processor = buildCommitsProcessor(options, gitRepository.getGit());
            gitRepository.processAllCommits(processor);
        } catch (final Exception e1) {
            throw new GitRuntimeException(e1);
        }
    }

}
