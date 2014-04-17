/**
 * 
 */

package org.komea.product.backend.service.entities;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.HasProjectPersonDao;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectPersonServiceTest
{
    
    
    @Mock
    private HasProjectPersonDao  projectPersonDAO;
    @InjectMocks
    private ProjectPersonService projectPersonService;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.entities.ProjectPersonService#getPersonIdsOfProject(java.lang.Integer)}.
     */
    @Test
    public final void testGetPersonIdsOfProject() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.entities.ProjectPersonService#getProjectIdsOfPerson(java.lang.Integer)}.
     */
    @Test
    public final void testGetProjectIdsOfPerson() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.entities.ProjectPersonService#updatePersonsOfProject(java.util.List, org.komea.product.database.model.Project)}.
     */
    @Test
    public final void testUpdatePersonsOfProject() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.entities.ProjectPersonService#updateProjectsOfPerson(java.util.List, org.komea.product.database.model.Person)}.
     */
    @Test
    public final void testUpdateProjectsOfPerson() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
