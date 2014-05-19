/**
 * 
 */

package org.komea.product.backend.service;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.service.fs.IPluginFileSystem;
import org.komea.product.backend.storage.DAOStorageIndex;
import org.komea.product.database.api.IHasId;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class PluginStorageServiceTest
{
    
    
    @Mock
    private IKomeaFS             komeaFS;
    @InjectMocks
    private PluginStorageService pluginStorageService;
    
    
    
    @Before
    public void before() {
    
    
        when(komeaFS.getFileSystem(Matchers.anyString())).thenReturn(mock(IPluginFileSystem.class));
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.PluginStorageService#registerDAOStorage(java.lang.String, java.lang.Class)}.
     */
    @Test
    public final void testRegisterDAOStorage_singleton() throws Exception {
    
    
        final IDAOObjectStorage<IHasId> storage =
                pluginStorageService.registerDAOStorage("daoStorage", IHasId.class);
        assertNotNull(storage);
        assertEquals(storage, pluginStorageService.registerDAOStorage("daoStorage", IHasId.class));
        assertEquals(storage, pluginStorageService.registerDAOStorage("daoStorage", IHasId.class));
    }
    
    /**
     * Test method for {@link org.komea.product.backend.service.PluginStorageService#registerDAOStorage(java.lang.String, java.lang.Class)}.
     */
    @Test
    public final void testRegisterDAOStorage_checkDaoStorageIndexFromObjectStorage() throws Exception {
    
    
        final IDAOObjectStorage<IHasId> storage =
                pluginStorageService.registerDAOStorage("daoStorage", IHasId.class);
        assertNotNull(storage);
        
        Field declaredField = storage.getClass().getDeclaredField("daoStorageIndex");
        declaredField.setAccessible(true);
        assertTrue(declaredField.get(storage) instanceof DAOStorageIndex);
        
    }
    
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.PluginStorageService#registerStorage(java.lang.String, java.lang.Class)}.
     */
    @Test
    public final void testRegisterStorage_singleton() throws Exception {
    
    
        final IObjectStorage<IHasId> storage =
                pluginStorageService.registerStorage("plugin", IHasId.class);
        assertNotNull(storage);
        assertEquals(storage, pluginStorageService.registerStorage("plugin", IHasId.class));
        assertEquals(storage, pluginStorageService.registerStorage("plugin", IHasId.class));
    }
    
    
}
