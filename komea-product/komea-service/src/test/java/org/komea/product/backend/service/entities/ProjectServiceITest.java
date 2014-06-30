/**
 *
 */

package org.komea.product.backend.service.entities;



import org.junit.Test;
import org.komea.product.database.model.Project;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class ProjectServiceITest extends AbstractSpringIntegrationTestCase
{


    private static final String PROJECT_NAME = "DEMO_PROJECT_NAME";
    @Autowired
    private IProjectService     projectService;



    @Test
    public void testGetOrCreate() {


        assertFalse(projectService.selectByAlias(PROJECT_NAME) != null);
        assertFalse(projectService.selectByKey(PROJECT_NAME) != null);
        assertFalse(projectService.exists(PROJECT_NAME));
        final Project orCreate = projectService.getOrCreate(PROJECT_NAME);
        assertNotNull(orCreate);
        assertTrue(orCreate.getKey().equals(PROJECT_NAME));
        assertTrue(projectService.selectByAlias(PROJECT_NAME) != null);
        assertTrue(projectService.selectByKey(PROJECT_NAME) != null);
        assertTrue(projectService.exists(PROJECT_NAME));
    }
}
