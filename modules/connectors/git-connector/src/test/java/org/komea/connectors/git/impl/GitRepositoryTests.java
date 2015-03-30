
package org.komea.connectors.git.impl;


import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;
import org.komea.connectors.git.AbstractLocalGitTest;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;
import org.komea.connectors.git.IGitRepository;
import org.komea.connectors.git.exceptions.ScmCannotObtainGitProxyException;

import com.google.common.collect.Lists;

public class GitRepositoryTests extends AbstractLocalGitTest
{
    
    @Test
    public void getGitTest() {
    
        Git git = this.repository.getGit();
        assertNotNull(git);
    }
    
    @Test
    public void getNotExistingGitTest() {
    
        IGitRepository lrepository = new GitRepository(new File("bidon/"), "https://github.com/muan/github-gmail.git");
        boolean catched = false;
        try {
            lrepository.getGit();
            
        } catch (ScmCannotObtainGitProxyException e) {
            catched = true;
        }
        assertTrue(catched);
    }
    
    @Test
    public void getBranchesTest() {
    
        List<Ref> branches = this.repository.getAllBranches();
        assertEquals(4, branches.size());
    }
    
    @Test
    public void getBranchesTagsTest() {
    
        Map<String, Ref> tags = this.repository.getTagsForBranch("refs/heads/master");
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
