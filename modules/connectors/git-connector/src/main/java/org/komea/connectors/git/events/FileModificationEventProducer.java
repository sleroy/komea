
package org.komea.connectors.git.events;


import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.komea.connectors.git.IFileUpdate;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;
import org.komea.connectors.git.IGitEvent;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.storage.IEventStorage;

public class FileModificationEventProducer implements IGitCommitProcessor
{

    private final IEventStorage storage;

    public FileModificationEventProducer(final IEventStorage storage) {

        super();
        this.storage = storage;

    }
    @Override
    public void process(final RevCommit gcommit, final RevWalk revWalk, final IGitCommit commit) {

        for (final IFileUpdate update : commit.getModifications()) {
            send(commit, update);
        }

    }

    private void send(final IGitCommit commit, final IFileUpdate update) {

        final ComplexEvent event = new ComplexEvent();
        event.setProvider(IGitEvent.PROVIDER);
        event.setEventType(IGitEvent.UPDATE);

        event.setDate(commit.getCommitTime());
        event.addField("commit", commit.getId());
        event.addField("file", update.getPath());
        event.addField("total_updated_lines", update.getNumberOfAddedLines() + update.getNumberOfDeletedLines());
        event.addField("added_lines", update.getNumberOfAddedLines());
        event.addField("deleted_lines", update.getNumberOfDeletedLines());
        event.addField("branch", commit.getBranch());
        event.addField("project", commit.getShProject());

        this.storage.storeComplexEvent(event);
    }
}
