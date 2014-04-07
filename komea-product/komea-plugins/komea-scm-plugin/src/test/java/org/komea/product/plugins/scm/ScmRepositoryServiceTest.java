/**
 * 
 */

package org.komea.product.plugins.scm;



import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.plugins.IPluginStorageService;
import org.komea.product.plugins.repository.model.ScmRepositoryDefinition;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class ScmRepositoryServiceTest
{
    
    
    private IDAOObjectStorage     daoMock;
    @Mock
    private IPluginStorageService pluginStorageService;
    @InjectMocks
    private ScmRepositoryService  scmRepositoryService;
    
    
    
    @Before
    public void before() {
    
    
        daoMock = mock(IDAOObjectStorage.class);
        when(
                pluginStorageService.registerDAOStorage(Matchers.anyString(),
                        Matchers.any(Class.class))).thenReturn(daoMock);
        scmRepositoryService.init();
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.scm.ScmRepositoryService#findByName(java.lang.String)}.
     */
    @Test
    public final void testFindByName() throws Exception {
    
    
        final String key = "REPO_NEW";
        final ScmRepositoryDefinition findByName = scmRepositoryService.findByName(key);
        assertNull(findByName);
        final ScmRepositoryDefinition scmRepositoryDefinition = new ScmRepositoryDefinition();
        scmRepositoryDefinition.setKey(key);
        when(scmRepositoryService.getDAO().selectAll()).thenReturn(
                Lists.newArrayList(scmRepositoryDefinition));
        
        assertEquals("Same definition", scmRepositoryDefinition,
                scmRepositoryService.findByName(key));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.scm.ScmRepositoryService#getAllRepositories()}.
     */
    @Test
    public final void testGetAllRepositories() throws Exception {
    
    
        final ScmRepositoryDefinition scmRepositoryDefinition = new ScmRepositoryDefinition();
        when(scmRepositoryService.getDAO().selectAll()).thenReturn(
                Lists.newArrayList(scmRepositoryDefinition));
        final List<ScmRepositoryDefinition> allRepositories =
                scmRepositoryService.getAllRepositories();
        assertEquals(1, allRepositories.size());
        assertEquals(scmRepositoryDefinition, allRepositories.get(0));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.scm.ScmRepositoryService#getPluginStorageService()}.
     */
    @Test
    public final void testGetPluginStorageService() throws Exception {
    
    
        assertNotNull(scmRepositoryService.getPluginStorageService());
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.scm.ScmRepositoryService#getRepositoriesNotAssociated()}.
     */
    @Test
    public final void testGetRepositoriesNotAssociated() throws Exception {
    
    
        final ScmRepositoryDefinition scmRepositoryDefinition = new ScmRepositoryDefinition();
        final ScmRepositoryDefinition scmRepositoryDefinition2 = new ScmRepositoryDefinition();
        scmRepositoryService.registerCronJobOfScm(scmRepositoryDefinition2);
        when(scmRepositoryService.getDAO().selectAll()).thenReturn(
                Lists.newArrayList(scmRepositoryDefinition));
        
        scmRepositoryService.getRepositoriesNotAssociated();
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.scm.ScmRepositoryService#isAssociatedToCron(org.komea.product.plugins.repository.model.ScmRepositoryDefinition)}
     * .
     */
    @Test
    public final void testIsAssociatedToCron() throws Exception {
    
    
        final ScmRepositoryDefinition scmRepositoryDefinition2 = new ScmRepositoryDefinition();
        assertFalse(scmRepositoryService.isAssociatedToCron(scmRepositoryDefinition2));
        scmRepositoryService.registerCronJobOfScm(scmRepositoryDefinition2);
        assertTrue(scmRepositoryService.isAssociatedToCron(scmRepositoryDefinition2));
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.scm.ScmRepositoryService#remove(org.komea.product.plugins.repository.model.ScmRepositoryDefinition)}
     * .
     */
    @Test
    public final void testRemove() throws Exception {
    
    
        // TODO :: testRemove
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.scm.ScmRepositoryService#saveOrUpdate(org.komea.product.plugins.repository.model.ScmRepositoryDefinition)}
     * .
     */
    @Test
    public final void testSaveOrUpdate() throws Exception {
    
    
        // TODO :: saveOrUpdate
    }
    
}
