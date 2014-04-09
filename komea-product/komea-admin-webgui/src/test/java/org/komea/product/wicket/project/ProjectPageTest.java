/**
 * 
 */

package org.komea.product.wicket.project;



import java.util.Collections;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class ProjectPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    @Before
    public void before() {
    
    
        final IProjectService mock = Mockito.mock(IProjectService.class);
        final Project projectDemo = new Project();
        projectDemo.setName("Nom");
        projectDemo.setId(1);
        when(mock.selectAll()).thenReturn(Collections.singletonList(projectDemo));
        wicketRule.getApplicationContextMock().putBean(mock);
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.project.ProjectPage#ProjectPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testProjectPage() throws Exception {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        try {
            newWicketTester.startPage(ProjectPage.class);
            newWicketTester.assertNoErrorMessage();
            
            // newWicketTester.dumpPage();
            newWicketTester.debugComponentTrees();
            final DataTable dataTable =
                    (DataTable) newWicketTester.getComponentFromLastRenderedPage("table");
            System.out.println(dataTable);
        } finally {
            newWicketTester.destroy();
        }
        
    }
    
}
