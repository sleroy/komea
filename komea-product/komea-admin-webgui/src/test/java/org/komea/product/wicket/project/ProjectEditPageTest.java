/**
 * 
 */

package org.komea.product.wicket.project;



import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.dao.CustomerDao;
import org.komea.product.database.model.Project;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



/**
 * @author sleroy
 */
public class ProjectEditPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    private IProjectService             projectService;
    
    
    
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(CustomerDao.class));
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IPersonGroupService.class));
        projectService = Mockito.mock(IProjectService.class);
        wicketRule.getApplicationContextMock().putBean(projectService);
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IPersonService.class));
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.project.ProjectEditPage#ProjectEditPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testProjectEditPagePageParameters() throws Exception {
    
    
        wicketRule.testStart(ProjectEditPage.class);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.project.ProjectEditPage#ProjectEditPage(org.apache.wicket.request.mapper.parameter.PageParameters, org.komea.product.database.model.Project)}
     * .
     */
    @Test
    public final void testProjectEditPagePageParametersProject() throws Exception {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        try {
            newWicketTester.startPage(ProjectPage.class);
            // final DataTable table =
            // (DataTable) newWicketTester.getComponentFromLastRenderedPage("table");
            // assertNotNull(table);
            // final int startElement = (int) table.getDataProvider().size();
            
            newWicketTester.startPage(new ProjectEditPage(new PageParameters(), new Project()));
            newWicketTester.assertNoErrorMessage();
            newWicketTester.debugComponentTrees();
            final FormTester newFormTester = newWicketTester.newFormTester("form"); // Path
            newFormTester.setValue("projectKey", "PROJECT_DEMO");
            newFormTester.setValue("description", "PROJECT DEMO");
            newFormTester.setValue("idCustomer", "0");
            newFormTester.setValue("name", "Graou");
            newFormTester.submit();
            // FIXME :: La page ne revient pas sur Projectpage!!!
            // FIXME:: newWicketTester.assertRenderedPage(ProjectPage.class);
            
            final ArgumentCaptor<Project> argumentCaptor = ArgumentCaptor.forClass(Project.class);
            verify(projectService, times(1)).insert(argumentCaptor.capture());
            final Project project = argumentCaptor.getValue();
            assertEquals("PROJECT_DEMO", project.getProjectKey());
            // final DataTable table2 =
            // (DataTable) newWicketTester.getComponentFromLastRenderedPage("table");
            // assertNotNull(table2);
            // assertEquals(startElement + 1, table2.getDataProvider().size());
            // verify(projectService, times(1)).saveOrUpdateProject(Matchers.any(Project.class),
            // Matchers.any(List.class), Matchers.any(List.class), Matchers.any(List.class),
            // Matchers.any(List.class));
            
        } finally {
            newWicketTester.destroy();
        }
    }
}
