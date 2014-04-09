/**
 * 
 */

package org.komea.product.plugins.git.cron;



import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.git.bean.GitProviderPlugin;
import org.komea.product.plugins.git.utils.GitRepositoryProxy;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class GitRepoFactory
{
    
    
    public static ScmRepositoryDefinition createDummyGitRepository()
            throws IOException, MalformedURLException {
    
    
        final long time = new Date().getTime();
        final File file = new File("target/fakeRepository/" + time);
        file.mkdirs();
        
        
        final File gitFile = new File(file, ".git");
        final FileRepositoryBuilder builder = new FileRepositoryBuilder();
        final Repository trpo = builder.setGitDir(gitFile).readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
        trpo.create();
        // ... use the new repository ...
        
        
        final String name = "dummy-repository-" + time;
        final ScmRepositoryDefinition gitRepo = new ScmRepositoryDefinition();
        gitRepo.setType(GitProviderPlugin.GIT_TYPE);
        gitRepo.setKey(name);
        gitRepo.setRepoName(name);
        gitRepo.setUrl(gitFile.toURL().toString());
        
        gitRepo.setProjectForRepository(name);
        return gitRepo;
    }
    
    
    public static IEventPushService initEsperEngine() {
    
    
        return Mockito.mock(IEventPushService.class, Mockito.withSettings().verboseLogging());
    }
    
    
    /**
     * Builds the new git cloner service
     * 
     * @param _gitRepo
     *            the git repo
     * @return the git cloner service.
     */
    public static GitRepositoryProxy newGitRepositoryProxy(final ScmRepositoryDefinition _gitRepo) {
    
    
        final File storageFolder = new File("target/clone");
        final GitRepositoryProxy gitcloner = new GitRepositoryProxy(_gitRepo, storageFolder);
        return gitcloner;
        
    }
    
    
    /**
     * 
     */
    public GitRepoFactory() {
    
    
        super();
    }
}
