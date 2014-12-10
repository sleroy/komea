package org.komea.connectors.git.events;

import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;


public class CommitProjectSetter implements IGitCommitProcessor
{
    private final String project;
    
    public CommitProjectSetter(final String project) {
    
        super();
        this.project = project;
    }

    @Override
    public void process(final RevCommit commit, final RevWalk revWalk, final IGitCommit convertGitCommit) {
    
        convertGitCommit.setShProject(this.project);
        
    }
}
