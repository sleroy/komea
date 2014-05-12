
package org.komea.product.rest.client;


import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.database.dto.DepartmentDto;
import org.komea.product.database.dto.PersonDto;
import org.komea.product.database.dto.ProjectDto;
import org.komea.product.database.dto.TeamDto;
import org.komea.product.rest.client.api.IDepartmentsAPI;
import org.komea.product.rest.client.api.IPersonsAPI;
import org.komea.product.rest.client.api.IProjectsAPI;
import org.komea.product.rest.client.api.ITeamsAPI;

public class DepartmentsAPIITest {
    
    @Rule
    public ServerMethodRule serverInit = new ServerMethodRule();
    
    //
    @Test
    public void testAllProjects() throws Exception {
    
        final IProjectsAPI projectsAPI = RestClientFactory.INSTANCE.createProjectsAPI(serverInit.getAddress());
        Assert.assertNotNull(projectsAPI);
        final List<ProjectDto> projects = projectsAPI.allProjects();
        Assert.assertFalse(projects.isEmpty());
    }
    
    @Test
    public void testGetAllDepartments() throws Exception {
    
        final IDepartmentsAPI projectsAPI = RestClientFactory.INSTANCE.createDeparmtentsAPI(serverInit.getAddress());
        Assert.assertNotNull(projectsAPI);
        final List<DepartmentDto> departments = projectsAPI.allDepartments();
        // Assert.assertTrue(projects.get(0) instanceof DepartmentDto);
        Assert.assertTrue(departments.isEmpty());
    }
    
    @Test
    public void testGetAllPersons() throws Exception {
    
        final IPersonsAPI projectsAPI = RestClientFactory.INSTANCE.createPersonsAPI(serverInit.getAddress());
        Assert.assertNotNull(projectsAPI);
        final List<PersonDto> persons = projectsAPI.allPersons();
        Assert.assertEquals(1, persons.size());
    }
    
    @Test
    public void testgetallTeams() throws Exception {
    
        final ITeamsAPI projectsAPI = RestClientFactory.INSTANCE.createTeamsAPI(serverInit.getAddress());
        Assert.assertNotNull(projectsAPI);
        final List<TeamDto> teams = projectsAPI.allTeams();
        Assert.assertTrue(teams.isEmpty());
    }
}
