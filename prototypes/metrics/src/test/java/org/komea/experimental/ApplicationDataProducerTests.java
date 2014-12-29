
package org.komea.experimental;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.EmptyTreeIterator;
import org.junit.Test;
import org.komea.connectors.git.impl.DiffComputation;
import org.komea.connectors.git.impl.GitCommit;
import org.komea.connectors.git.impl.GitRepository;
import org.komea.experimental.model.KomeaConfiguration;
import org.komea.experimental.model.SoftwareFactoryConfiguration;

import com.google.common.collect.Lists;

public class ApplicationDataProducerTests
{
    
    @Test
    // @Ignore
    public void test() throws IOException {
    
        KomeaConfiguration komea = new KomeaConfiguration("localhost:2424", "localhost:2424");
        SoftwareFactoryConfiguration configuration = new SoftwareFactoryConfiguration(new File("/Users/afloch/Documents/git/mongo"),
                "https://jira.mongodb.org/", "mongodb");
        
        ApplicationEventsProducer analyzer = new ApplicationEventsProducer(configuration, komea);
        analyzer.connect("root", "root");
        analyzer.pushJiraEvents();
        //analyzer.pushGitEvents();
        analyzer.close();
    }
    
    public static void main(final String[] args) throws Exception {
    
        GitRepository repo = new GitRepository(new File("/Users/afloch/Documents/git/mongo"), "");
        String doubleBranchCommit = "451dccb3ec7801b0929a870f30adaf2f78286922";
        RevWalk walk = new RevWalk(repo.getGit().getRepository());
        ObjectId resolve = repo.getGit().getRepository().resolve(doubleBranchCommit);
        RevCommit parseCommit = walk.parseCommit(resolve);
        Date when = parseCommit.getAuthorIdent().getWhen();
        List<String> branches = getBranches(repo.getGit(), doubleBranchCommit, repo.getLocalBranches());
        
        GitCommit gitcommit = new GitCommit(parseCommit.getName(), parseCommit.getAuthorIdent(), parseCommit.getCommitterIdent(),
                parseCommit.getFullMessage());
   
        DiffComputation diffComputation = new DiffComputation(repo.getGit(), gitcommit, parseCommit);
        diffComputation.update();
        System.out.println(branches);
    }
    
    
    
    private static List<String> getBranches(final Git git, final String name, final List<Ref> branches) throws RevisionSyntaxException,
            MissingObjectException, IncorrectObjectTypeException, AmbiguousObjectException, IOException {
    
        RevWalk walk = new RevWalk(git.getRepository());
        RevCommit commit = walk.parseCommit(git.getRepository().resolve(name));
        List<String> names = Lists.newArrayList();
        for (Ref branch : branches) {
            RevCommit branchCommit = walk.parseCommit(branch.getObjectId());
            boolean merged = walk.isMergedInto(commit, branchCommit);
            if (merged || branchCommit.getName().equals(commit.getName())) {
                names.add(branch.getName());
            }
            
        }
        
        return names;
    }
}
