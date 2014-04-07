/**
 * 
 */

package org.komea.product.plugins.git.cron;



import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import org.apache.wicket.util.file.File;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.git.bean.GitClonerService;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitClonerService;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.komea.product.plugins.scm.api.plugin.IScmCloner;
import org.komea.product.plugins.scm.cron.ScmCronJob;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;



/**
 * @author sleroy
 */
public class GitRepoFactory
{
    
    
    public static GitRepositoryDefinition createDummyGitRepository()
            throws IOException, MalformedURLException {
    
    
        final long time = new Date().getTime();
        final File file = new File("target/fakeRepository/" + time);
        file.mkdirs();
        
        
        final File gitFile = new File(file, ".git");
        final Repository trpo = FileRepositoryBuilder.create(gitFile);
        trpo.create();
        // ... use the new repository ...
        
        
        final String name = "dummy-repository-" + time;
        final GitRepositoryDefinition gitRepo =
                GitRepositoryDefinition.newGitRepository(name, gitFile.toURL().toString());
        gitRepo.setProjectForRepository(name);
        return gitRepo;
    }
    
    
    public static IEventPushService initEsperEngine() {
    
    
        return Mockito.mock(IEventPushService.class, Mockito.withSettings().verboseLogging());
    }
    
    
    /**
     * Launch a cron on git repository.
     * 
     * @param _eventPushEngine
     * @param gitRepo
     * @param gitcloner
     * @return
     * @throws Exception
     */
    public static final List<EventSimpleDto> launchGitCron(
            final IEventPushService _eventPushEngine,
            final GitRepositoryDefinition gitRepo,
            final IGitClonerService gitcloner) throws Exception {
    
    
        final IGitRepositoryService repository = Mockito.mock(IGitRepositoryService.class);
        final ScmCronJob gitCronJob = new ScmCronJob();
        
        final IPersonService personService =
                Mockito.mock(IPersonService.class, Mockito.withSettings());
        Mockito.when(personService.findOrCreatePersonByEmail(Matchers.anyString())).thenAnswer(
                new Answer<Person>()
                {
                    
                    
                    @Override
                    public Person answer(final InvocationOnMock _invocation) throws Throwable {
                    
                    
                        final Person person = new Person();
                        person.setEmail((String) _invocation.getArguments()[0]);
                        person.setId(person.getEmail().hashCode());
                        person.setLogin(person.getEmail().substring(0,
                                person.getEmail().indexOf('@')));
                        return person;
                    }
                });
        gitCronJob.setEsperEngine(_eventPushEngine);
        gitCronJob.setFetch(gitRepo);
        gitCronJob.setPersonService(personService);
        gitCronJob.setRepository(repository);
        gitCronJob.setScmCloner(gitcloner);
        gitCronJob.setScmReader(Mockito.mock(IScmCloner.class));
        gitCronJob.executeScmCron();
        final ArgumentCaptor<EventSimpleDto> argumentCaptor =
                ArgumentCaptor.forClass(EventSimpleDto.class);
        Mockito.verify(_eventPushEngine, Mockito.atLeastOnce()).sendEventDto(
                argumentCaptor.capture());
        return argumentCaptor.getAllValues();
    }
    
    
    /**
     * Builds the new git cloner service
     * 
     * @param _gitRepo
     *            the git repo
     * @return the git cloner service.
     */
    public static IGitClonerService newGitClonerService(final GitRepositoryDefinition _gitRepo) {
    
    
        final GitClonerService gitcloner = new GitClonerService();
        final IKomeaFS komeaFS = Mockito.mock(IKomeaFS.class);
        gitcloner.setKomeaFS(komeaFS);
        
        final File system = new File("target/clone/" + _gitRepo.getKey());
        system.mkdirs();
        Mockito.when(komeaFS.getFileSystemFolder(Matchers.anyString())).thenReturn(system);
        return gitcloner;
        
    }
    
    
    /**
     * 
     */
    public GitRepoFactory() {
    
    
        super();
    }
}
