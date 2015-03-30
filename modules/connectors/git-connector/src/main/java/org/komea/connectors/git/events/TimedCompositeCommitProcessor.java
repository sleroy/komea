
package org.komea.connectors.git.events;


import java.util.Date;
import java.util.List;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.joda.time.DateTime;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;

import com.google.common.collect.Lists;

/**
 * A git commit processor that delegate the process to nested processors only if a commit on a time windows.
 *
 * @author afloch
 */
public class TimedCompositeCommitProcessor implements IGitCommitProcessor
{

    private final List<IGitCommitProcessor> processors;
    private final DateTime                  since;
    private final DateTime                  until;

    public TimedCompositeCommitProcessor(final DateTime since, final DateTime until) {

        super();
        this.since = since;
        this.until = until;
        this.processors = Lists.newArrayList();
        
    }

    public void addCommitProcessor(final IGitCommitProcessor processor) {
    
        this.processors.add(processor);
    }

    /**
     * Tests if the commit is considered as updated. Is updated a commit
     * when its date is after the since date or since date is null.
     *
     * @param _date
     *            the date
     */
    private boolean isUpdated(final Date _date) {

        final boolean isAfter = this.since == null || this.since.isBefore(new DateTime(_date));
        final boolean isBefore = this.until == null || this.until.isAfter(new DateTime(_date));
        return isAfter && isBefore;
    }
    
    @Override
    public void process(final RevCommit commit, final RevWalk revWalk, final IGitCommit convertGitCommit) {

        if (isUpdated(commit.getAuthorIdent().getWhen())) {
            for (final IGitCommitProcessor processor : this.processors) {
                processor.process(commit, revWalk, convertGitCommit);
            }
        }
    }

}
