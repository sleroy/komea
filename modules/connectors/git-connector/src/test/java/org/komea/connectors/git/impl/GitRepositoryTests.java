
package org.komea.connectors.git.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;
import org.komea.connectors.git.AbstractLocalGitTest;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;

import com.google.common.collect.Lists;

public class GitRepositoryTests extends AbstractLocalGitTest
{
    
    @Test
    public void getGitTest() {
    
        Git git = this.repository.getGit();
        assertNotNull(git);
    }
    
    @Test
    public void getBranchesTest() {
    
        List<Ref> branches = this.repository.getBranches();
        assertEquals(4, branches.size());
    }
    
    @Test
    public void getBranchesTagsTest() {
    
        Set<String> tags = this.repository.getAllTagsFromABranch("remotes/origin/eval");
        assertEquals(22, tags.size());
    }
    
    @Test
    public void processAllCommitsTest() {
    
        final List<String> commits = Lists.newArrayList();
        
        this.repository.processAllCommits(new IGitCommitProcessor()
        {
            
            @Override
            public void process(final RevCommit commit, final RevWalk revWalk, final IGitCommit convertGitCommit) {
            
                commits.add(commit.getName());
                
            }
        });
        assertEquals(getExpectedNumberOfCommits(), commits.size());
    }
}
