package org.komea.connectors.git.impl;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.komea.connectors.git.IGitCommit;
import org.komea.connectors.git.IGitCommitProcessor;
import org.komea.connectors.git.IGitRepository;
import org.komea.connectors.git.events.GitCommitConverter;
import org.komea.event.model.KomeaEvent;

public class DumpDataRepositoryTests {

    protected final File folder = new File(
            "/home/sleroy/git/komea-product");
    protected IGitRepository repository;

    @After
    public void end() {

        repository.close();
    }

    @Before
    public void init() {

        assertTrue(folder.exists());
        repository = new GitRepository(folder,
                "/home/sleroy/git/komea-product/.git");
    }

    @Test()
    @Ignore("sleroy")
    public void processAllCommitsTest() {

        final List<KomeaEvent> commits = Lists.newArrayList();
        final Kryo kryo = new Kryo();
        final GitCommitConverter gitCommitConverter = new GitCommitConverter();

        repository.processAllCommits(new IGitCommitProcessor() {

            @Override
            public void process(final RevCommit commit, final RevWalk revWalk,
                    final IGitCommit convertGitCommit) {

                commits.add(gitCommitConverter.newCommitEvent(convertGitCommit));

            }

        });
        System.out.println(commits.size());
        try (FileOutputStream outputStream = new FileOutputStream(
                "gitdemo.ser")) {
            kryo.writeObject(new Output(
                    outputStream),
                    commits);
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
