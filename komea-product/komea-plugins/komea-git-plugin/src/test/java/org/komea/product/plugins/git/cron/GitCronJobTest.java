/**
 * 
 */

package org.komea.product.plugins.git.cron;



import java.io.File;

import org.junit.Test;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.Person;
import org.komea.product.plugins.git.bean.GitClonerService;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
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
     * Test method for {@link org.komea.product.plugins.git.cron.GitCronJob#execute(org.quartz.JobExecutionContext)}.
     */
    @Test
    public final void testExecute() throws Exception {
    
    
        final GitCronJob gitCronJob = new GitCronJob();
        
        final IEventPushService esperEngine =
                Mockito.mock(IEventPushService.class, Mockito.withSettings().verboseLogging());
        final IGitRepositoryService repository = Mockito.mock(IGitRepositoryService.class);
        final GitClonerService gitcloner = new GitClonerService();
        final IKomeaFS komeaFS = Mockito.mock(IKomeaFS.class);
        gitcloner.setKomeaFS(komeaFS);
        
        final File system = new File("target/tmp");
        system.mkdirs();
        Mockito.when(komeaFS.getFileSystemFolder(Matchers.anyString())).thenReturn(system);
        
        
        final GitRepositoryDefinition gitRepo =
                GitRepositoryDefinition.newGitRepository("MyBatis generator",
                        "https://github.com/sleroy/generator.git");
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
        Mockito.verify(esperEngine, Mockito.atLeastOnce()).sendEventDto(
                Matchers.any(EventSimpleDto.class));
    }
    
}
