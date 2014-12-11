
package org.komea.connectors.git.events;


import java.util.List;

import org.apache.commons.io.output.NullOutputStream;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;
import org.komea.connectors.git.impl.DiffComputation;
import org.komea.connectors.git.impl.GitCommit;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileModificationEventProducer implements IGitCommitProcessor
{
    
    private static final Logger LOGGER       = LoggerFactory.getLogger(FileModificationEventProducer.class);
    
    private static final String FILE_UPDATED = "fileUpdated";
    private final IEventStorage storage;
    private final Git           git;
    
    public FileModificationEventProducer(final IEventStorage storage, final Git git) {
    
        super();
        this.storage = storage;
        this.git = git;
    }
    @Override
    public void process(final RevCommit commit, final RevWalk revWalk, final IGitCommit convertGitCommit) {
    
        try {
            // push event on file modification
            final DiffComputation computation = new DiffComputation(this.git, convertGitCommit, commit, revWalk);
            final List<DiffEntry> entriesFromCommit = computation.obtainDiffEntriesFromCommit(new DiffFormatter(new NullOutputStream()));
            for (final DiffEntry entry : entriesFromCommit) {
                if (isNotNewPath(entry)) {
                    this.storage.storeEvent(newFileModificationEvent(commit, convertGitCommit, entry, false));
                } else {
                    this.storage.storeEvent(newFileModificationEvent(commit, convertGitCommit, entry, true));
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Unable to compute file modification from commit {}", commit.getName(), e);
        }
    }
    private boolean isNotNewPath(final DiffEntry entry) {
    
        return !entry.getOldPath().equals(entry.getNewPath()) && !entry.getOldPath().equals("/dev/null");
    }
    private ComplexEvent newFileModificationEvent(final RevCommit commit, final IGitCommit convertGitCommit, final DiffEntry entry,
            final boolean newPath) {
    
        final ComplexEvent complexEventDto = new ComplexEvent();
        complexEventDto.setProvider(GitCommit.GIT);
        complexEventDto.setEventType(FILE_UPDATED);
        
        complexEventDto.addField("change_type", entry.getChangeType());
        complexEventDto.addField("file", newPath ? entry.getNewPath() : entry.getOldPath());
        complexEventDto.addField("similarity", entry.getScore());
        complexEventDto.addField("fileMode", entry.getNewMode().getBits());
        complexEventDto.setDate(convertGitCommit.getCommitTime());
        complexEventDto.addField("author", commit.getCommitterIdent().getEmailAddress());
        
        return complexEventDto;
    }
}
