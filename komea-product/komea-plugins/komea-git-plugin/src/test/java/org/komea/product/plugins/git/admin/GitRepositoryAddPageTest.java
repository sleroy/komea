
package org.komea.product.plugins.git.admin;



import java.util.Collections;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class GitRepositoryAddPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    private IGitRepositoryService       gitRepositoryService;
    
    
    
    @Before
    public void beforeTest() {
    
    
        gitRepositoryService =
                Mockito.mock(IGitRepositoryService.class/* , withSettings().verboseLogging() */);
        wicketRule.getApplicationContextMock().putBean(gitRepositoryService);
        final GitRepositoryDefinition gitRepositoryDefinition = GitDemoData.buildRepositoryDemo();
        when(gitRepositoryService.getAllRepositories()).thenReturn(
                Collections.singletonList(gitRepositoryDefinition));
    }
    
    
    @Test
    public final void testForm() throws Exception {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        try {
            
            newWicketTester.startPage(new GitRepositoryAddPage(new PageParameters()));
            newWicketTester.assertNoErrorMessage(); // No bug at starting
            final FormTester newFormTester = newWicketTester.newFormTester("form");
            
            final String exampleRepoName = "DemoRepo";
            final String userName = "user";
            final String password = "passwd";
            final String urlRepo = "http://localhost";
            final String demoProject = "DEMO_PROJ";
            
            newFormTester.setValue("repoName", exampleRepoName);
            newFormTester.setValue("userName", userName);
            newFormTester.setValue("password", password);
            newFormTester.setValue("url", urlRepo);
            newFormTester.setValue("projectForRepository", demoProject);
            newFormTester.submit();
            newWicketTester.assertRenderedPage(GitRepositoryPage.class); // We expect to return to the previous page
            // We control what we receive from formular
            final ArgumentCaptor<GitRepositoryDefinition> submitedRepo =
                    ArgumentCaptor.forClass(GitRepositoryDefinition.class);
            verify(gitRepositoryService, times(1)).saveOrUpdate(submitedRepo.capture());
            final GitRepositoryDefinition value = submitedRepo.getValue();
            assertEquals(userName, value.getUserName());
            assertEquals(password, value.getPassword());
            assertEquals(exampleRepoName, value.getRepoName());
            assertEquals(urlRepo, value.getUrl());
            assertEquals(demoProject, value.getProjectForRepository());
            
        } finally {
            newWicketTester.destroy();
        }
    }
    
    
    @Test
    public final void testGitRepositoryAddPagePageParameters() throws Exception {
    
    
        wicketRule.testStart(GitRepositoryAddPage.class);
        
    }
    
    
    @Test
    public final void testGitRepositoryAddPagePageParametersGitRepositoryDefinition()
            throws Exception {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        try {
            
            newWicketTester.startPage(new GitRepositoryAddPage(new PageParameters(),
                    new GitRepositoryDefinition()));
            newWicketTester.assertNoErrorMessage(); // No bug at starting
            
        } finally {
            newWicketTester.destroy();
        }
    }
    
    
    @Test
    public final void testInvalidForm() throws Exception {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        try {
            
            newWicketTester.startPage(new GitRepositoryAddPage(new PageParameters()));
            newWicketTester.assertNoErrorMessage(); // No bug at starting
            final FormTester newFormTester = newWicketTester.newFormTester("form");
            
            final String exampleRepoName = "";
            final String userName = "";
            final String password = "";
            final String urlRepo = "";
            final String demoProject = "DEMO_PROJ";
            
            newFormTester.setValue("repoName", exampleRepoName);
            newFormTester.setValue("userName", userName);
            newFormTester.setValue("password", password);
            newFormTester.setValue("url", urlRepo);
            newFormTester.setValue("projectForRepository", demoProject);
            
            newFormTester.submit();
            newWicketTester.assertRenderedPage(GitRepositoryAddPage.class); // We expect to not change page
            newWicketTester.assertErrorMessages("Le champ 'repoName' est obligatoire.",
                    "Le champ 'url' est obligatoire.", "Le champ 'password' est obligatoire.");
            
        } finally {
            newWicketTester.destroy();
        }
    }
    
}
