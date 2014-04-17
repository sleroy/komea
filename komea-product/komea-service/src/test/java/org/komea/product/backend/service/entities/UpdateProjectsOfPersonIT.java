
package org.komea.product.backend.service.entities;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.Project;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import com.google.common.collect.Lists;

public class UpdateProjectsOfPersonIT extends AbstractSpringDBunitIntegrationTest {
    
    @Autowired
    private IProjectPersonService projectPersonService;
    
    @Autowired
    private IProjectService       projectService;
    
    @Autowired
    private IPersonService        personService;
    
    @Before
    public void setUp() throws Exception {
    
    }
    //
    
    @After
    public void tearDown() throws Exception {
    
    }
    
    // @Ignore
    @Test
    @ExpectedDatabase(value = "database_updatePerson.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void add_project_list_to_person() {
    
        // GIVEN two project Komea and Scertify
        Project komea = projectService.getOrCreate("KOMEA");
        Project scertify = projectService.getOrCreate("SCERTIFY");
        List<Project> projects = Lists.newArrayList(komea, scertify);
        
        // AND One user 'sylvain Leroy
        Person sylvain = personService.findOrCreatePersonByLogin("sleroy");
        
        // AND sylvain is associate to 0 projects
        Assert.assertEquals(0, projectPersonService.getProjectIdsOfPerson(sylvain.getId()).size());
        
        // WHEN I associate these two projects to sylvain
        projectPersonService.updateProjectsOfPerson(projects, sylvain);
        
        // THEN sylvain is associate to komea ans scertify project
    }
    
    @Test
    @DatabaseSetup(value = {
        "database_projectLinks.xml" })
    @ExpectedDatabase(value = "database_noProjects.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void clean_project_list_to_person() {
    
        // GIVEN One user 'sylvain Leroy
        Person sylvain = personService.findOrCreatePersonByLogin("sleroy");
        // AND an empty list of projects
        List<Project> noProjects = Lists.newArrayListWithExpectedSize(0);
        // AND sylvain is associate to 2 projects
        Assert.assertEquals(2, projectPersonService.getProjectIdsOfPerson(sylvain.getId()).size());
        
        // WHEN I associate these two projects to sylvain
        projectPersonService.updateProjectsOfPerson(noProjects, sylvain);
        
        // THEN sylvain must be associate to 0 projects
    }
    
    @Test
    @DatabaseSetup("database_projectLinks.xml")
    @ExpectedDatabase(value = "database_personChangeProject.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void change_project_list_to_person() {
    
        // GIVEN One user 'sylvain Leroy
        Person sylvain = personService.findOrCreatePersonByLogin("sleroy");
        // AND an list of projects compose tof the SYSTEM project
        Project system = projectService.getOrCreate("SYSTEM");
        List<Project> projects = Lists.newArrayList(system);
        // AND sylvain is associate to 2 projects
        Assert.assertEquals(2, projectPersonService.getProjectIdsOfPerson(sylvain.getId()).size());
        
        // WHEN I associate these two projects to sylvain
        projectPersonService.updateProjectsOfPerson(projects, sylvain);
        
        // THEN sylvain must be associate to 0 projects
    }
}
