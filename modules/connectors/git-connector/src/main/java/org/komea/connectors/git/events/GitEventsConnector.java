package org.komea.connectors.git.events;

import org.apache.commons.lang3.Validate;
import org.eclipse.jgit.api.Git;
import org.komea.connectors.git.IGitCommitProcessor;
import org.komea.connectors.git.IGitRepository;
import org.komea.connectors.git.exceptions.GitRuntimeException;
import org.komea.connectors.git.impl.GitRepository;
import org.komea.events.api.IEventsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitEventsConnector {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(GitEventsConnector.class);
    private final IEventsClient storage;

    public GitEventsConnector(final IEventsClient storage) {

        super();
        this.storage = storage;
    }

    public void launch(final GitConnectorConfiguration options) {

        Validate.notNull(options.getRepositoryFolder());
        Validate.isTrue(options.getRepositoryFolder().exists());
        final IGitRepository gitRepository = new GitRepository(
                options.getRepositoryFolder(), options.getRepositoryUrl());
        try {

            LOGGER.info("Processing commits of GIT repository");
            final IGitCommitProcessor processor = buildCommitsProcessor(
                    options, gitRepository.getGit());
            gitRepository.processAllCommits(processor);
        } catch (final Exception e1) {
            throw new GitRuntimeException(e1);
        }
    }

    private IGitCommitProcessor buildCommitsProcessor(
            final GitConnectorConfiguration options, final Git git) {

        final TimedCompositeCommitProcessor processor = new TimedCompositeCommitProcessor(
                options.getSince(), options.getUntil());
        processor.addCommitProcessor(new CommitProjectSetter(options
                .getProject()));
        processor.addCommitProcessor(new CommitEventProducer(storage));
        processor
                .addCommitProcessor(new FileModificationEventProducer(storage));
        processor.addCommitProcessor(new GitEventTagProducer(storage, git));
        return processor;
    }

}
