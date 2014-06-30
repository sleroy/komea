/**
 *
 */

package org.komea.product.web.rest.api;



import java.util.List;

import org.junit.Test;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.IFilter;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.model.Project;
import org.komea.product.test.spring.AbstractSpringWebIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;



/**
 * @author sleroy
 */
public class ProjectsControllerTest extends AbstractSpringWebIntegrationTestCase
{


    private static final String PROJECT_XXA = "PROJECT_XXA";
    
    private static final String PROJECT_XXZ = "PROJECT_XXZ";

    @Autowired
    private ProjectsController  projectsController;

    @Autowired
    private IProjectService     projectService;



    /**
     * Test method for {@link org.komea.product.web.rest.api.ProjectsController#allProjects()}.
     */
    @Test
    public final void testAllProjects() throws Exception {
    
    
        projectService.saveOrUpdate(fakeProject(PROJECT_XXA));
        projectService.saveOrUpdate(fakeProject(PROJECT_XXZ));
        
        final List<ProjectDto> projects = projectsController.allProjects();
        assertNotNull(CollectionUtil.filter(projects, new IFilter<ProjectDto>()
        {
            
            
            @Override
            public boolean matches(final ProjectDto _task) {
            
            
                return PROJECT_XXA.equals(_task.getProjectKey());
            }

                }));
        assertNotNull(CollectionUtil.filter(projects, new IFilter<ProjectDto>()
        {
            
            
            @Override
            public boolean matches(final ProjectDto _task) {
            
            
                return PROJECT_XXZ.equals(_task.getProjectKey());
            }

                }));


    }


    /**
     * @param projectKey
     * @return
     */
    private Project fakeProject(final String projectKey) {


        final Project project = new Project();
        project.setProjectKey(projectKey);
        project.setName(projectKey);
        project.setDescription(projectKey);
        return project;
    }


}
