
package org.komea.product.rest.client;


import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.model.Project;
import org.komea.product.rest.client.api.IProjectsAPI;

public class ProjectsAPIIT extends AbstractRestClientIntegrationTestCase
{
    
    @Before
    public void setUp() throws Exception {
    
    }
    //
    
    @Test
    public void testAllProjects() throws ConnectException, URISyntaxException {
    
        IProjectsAPI projectsAPI = RestClientFactory.INSTANCE.createProjectsAPI("http://localhost:8585/komea");
        Assert.assertNotNull(projectsAPI);
        List<Project> projects = projectsAPI.allProjects();
        Assert.assertTrue(projects.isEmpty());
    }
}
