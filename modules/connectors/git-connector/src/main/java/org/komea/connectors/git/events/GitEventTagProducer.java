package org.komea.connectors.git.events;

import com.google.common.collect.Maps;
import java.io.Serializable;
import java.util.Map;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;
import org.komea.connectors.git.IGitEvent;
import org.komea.event.model.KomeaEvent;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitEventTagProducer implements IGitCommitProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitEventTagProducer.class);

    private final Map<String, String> tagsMap;

    private final IEventStorage storage;

    public GitEventTagProducer(final IEventStorage storage, final Git git) {

        this.storage = storage;
        this.tagsMap = Maps.newHashMap();

        final Repository repository = git.getRepository();
        final RevWalk walk = new RevWalk(repository);
        try {
            for (final Ref tag : git.tagList().call()) {
                final RevObject revObject = walk.parseAny(tag.getObjectId());
                final String tagName = Repository.shortenRefName(tag.getName());
                if (revObject instanceof RevTag) {
                    final RevTag jtag = (RevTag) revObject;

                    final RevObject tagged = walk.parseAny(jtag.getObject().getId());
                    final RevCommit referencedCommit = walk.parseCommit(repository.resolve(tagged.getName()));
                    this.tagsMap.put(referencedCommit.getId().name(), tagName);
                } else {
                    this.tagsMap.put(revObject.getId().name(), tagName);
                }
            }
        } catch (final Exception e) {
            LOGGER.error("GIT TAG exception.", e);
        }

    }

    @Override
    public void process(final RevCommit commit, final RevWalk walk, final IGitCommit convertGitCommit) {

        final String tag = this.tagsMap.get(convertGitCommit.getId());
        if (tag != null) {
            sendTag(tag, convertGitCommit);
        }

    }

    private void sendTag(final String tag, final IGitCommit convertGitCommit) {

        final KomeaEvent event = new KomeaEvent();
        event.setEventType(IGitEvent.TAG);
        event.setProvider(IGitEvent.PROVIDER);
        event.addField("name", tag);
        event.addField("project", convertGitCommit.getShProject());
        event.addField("branches", (Serializable) convertGitCommit.getBranches());
        event.setDate(convertGitCommit.getCommitTime());
        event.addField("commit", convertGitCommit.getId());

        this.storage.storeEvent(event);

    }

}
