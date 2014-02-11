
package org.komea.product.rest.client;


import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.dto.TeamDto;
import org.komea.product.rest.client.api.IDepartmentsAPI;
import org.komea.product.rest.client.api.IPersonsAPI;
import org.komea.product.rest.client.api.IProjectsAPI;
import org.komea.product.rest.client.api.ITeamsAPI;

public class DepartmentsAPIIT extends AbstractRestClientIntegrationTestCase {
    
    @Before
    public void setUp() throws Exception {
    
    }
    //
    
    @Test
    public void testGetAllDepartments() throws Exception {
    
        IDepartmentsAPI projectsAPI = RestClientFactory.INSTANCE.createDeparmtentsAPI("http://localhost:8585/komea");
        Assert.assertNotNull(projectsAPI);
        List<DepartmentDto> projects = projectsAPI.allDepartments();
        Assert.assertTrue(projects.get(0) instanceof DepartmentDto);
        Assert.assertFalse(projects.isEmpty());
    }
    
    @Test
    public void testGetAllPersons() throws Exception {
    
        IPersonsAPI projectsAPI = RestClientFactory.INSTANCE.createPersonsAPI("http://localhost:8585/komea");
        Assert.assertNotNull(projectsAPI);
        List<PersonDto> projects = projectsAPI.allPersons();
        Assert.assertFalse(projects.isEmpty());
    }
    
    @Test
    public void testAllProjects() throws Exception {
    
        IProjectsAPI projectsAPI = RestClientFactory.INSTANCE.createProjectsAPI("http://localhost:8585/komea");
        Assert.assertNotNull(projectsAPI);
        List<ProjectDto> projects = projectsAPI.allProjects();
        Assert.assertFalse(projects.isEmpty());
    }
    
    @Test
    public void testgetallTeams() throws Exception {
    
        ITeamsAPI projectsAPI = RestClientFactory.INSTANCE.createTeamsAPI("http://localhost:8585/komea");
        Assert.assertNotNull(projectsAPI);
        List<TeamDto> projects = projectsAPI.allTeams();
        Assert.assertFalse(projects.isEmpty());
    }
}
