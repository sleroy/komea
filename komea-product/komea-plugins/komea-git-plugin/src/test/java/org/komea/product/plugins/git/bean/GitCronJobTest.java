/**
 * 
 */

package org.komea.product.plugins.git.bean;



import java.io.File;

import org.junit.Test;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.git.model.GitRepo;
import org.komea.product.plugins.git.repositories.api.IGitRepository;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;



/**
 * @author sleroy
 */
public class GitCronJobTest
{
    
    
    /**
     * Test method for {@link org.komea.product.plugins.git.bean.GitCronJob#execute(org.quartz.JobExecutionContext)}.
     */
    @Test
    public final void testExecute() throws Exception {
    
    
        final GitCronJob gitCronJob = new GitCronJob();
        
        final IEventPushService esperEngine =
                Mockito.mock(IEventPushService.class, Mockito.withSettings().verboseLogging());
        final IGitRepository repository = Mockito.mock(IGitRepository.class);
        final GitClonerService gitcloner = new GitClonerService();
        
        
        final File system = new File("target/tmp");
        system.mkdirs();
        gitcloner.setSystem(system);
        
        
        final GitRepo gitRepo = new GitRepo();
        gitRepo.setId(1L);
        gitRepo.setRepoName("MyBatis generator");
        gitRepo.setUrl("https://github.com/sleroy/generator.git");
        gitRepo.setProjectAssociated("MyBatis generator");
        
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
        gitCronJob.executeGitCron(esperEngine, repository, gitcloner, gitRepo, personService);
    }
}
