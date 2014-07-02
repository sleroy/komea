/**
 *
 */
package org.komea.product.backend.service.entities;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import java.util.Collections;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.komea.product.database.model.Project;
import org.komea.product.test.spring.AbstractSpringDBunitIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sleroy
 */
public class ProjectServiceMergeITest extends AbstractSpringDBunitIntegrationTest {

    @Autowired
    private IProjectService projectService;

    @Test
    @DatabaseSetup("database_projects.xml")
    @ExpectedDatabase(value = "database_projectMerged.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void testMergeProjects() {
        final String srcProjectKey = "SCERTIFY";
        final Project destProject = projectService.selectByKey("KOMEA");
        destProject.addAlias(srcProjectKey);
        projectService.saveOrUpdateProject(destProject, Collections.EMPTY_LIST,
                Collections.EMPTY_LIST, Collections.EMPTY_LIST, Collections.EMPTY_LIST);
        assertFalse(projectService.exists(srcProjectKey));
    }

}
