package org.komea.connectors.git.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.komea.connectors.git.impl.ScmRepositoryDefinition;

public class GitClonerTestsIT {

    @Test
    public void cloneTest() throws IOException {

        ScmRepositoryDefinition conf = new ScmRepositoryDefinition("https://github.com/muan/github-gmail.git", "github-gmail");

        File output = new File("build/git/");

        GitCloner cloner = new GitCloner(output, conf);
        cloner.cloneRepository();
        Assert.assertTrue(FileUtils.getFile(output, "github-gmail", ".git").exists());
        FileUtils.deleteDirectory(output);

        String string = cloner.toString();
        Assert.assertTrue(string.contains("github-gmail/.git") || string.contains("github-gmail\\.git"));
    }

}
