
package org.komea.product.plugins.git.admin;



import java.util.Collections;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.plugins.git.model.GitRepositoryDefinition;
import org.komea.product.plugins.git.repositories.api.IGitRepositoryService;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.when;



public class GitRepositoryPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    @Before
    public void beforeTest() {
    
    
        final IGitRepositoryService gitRepositoryService =
                Mockito.mock(IGitRepositoryService.class);
        wicketRule.getApplicationContextMock().putBean(gitRepositoryService);
        final GitRepositoryDefinition gitRepositoryDefinition = GitDemoData.buildRepositoryDemo();
        when(gitRepositoryService.getAllRepositories()).thenReturn(
                Collections.singletonList(gitRepositoryDefinition));
    }
    
    
    @Test
    public final void testGitRepositoryPagePageParameters() throws Exception {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        try {
            
            newWicketTester.startPage(GitRepositoryPage.class);
            newWicketTester.assertNoErrorMessage(); // No bug at starting
            final DataTable tableComponent =
                    (DataTable) newWicketTester.getComponentFromLastRenderedPage("table");
            assertNotNull(tableComponent);
            // Table should have one element
            Assert.assertEquals(1, tableComponent.getDataProvider().size());
            
            
        } finally {
            newWicketTester.destroy();
        }
        
    }
    
    
}
