/**
 * 
 */

package org.komea.product.backend.service;



import java.io.File;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class KomeaFSTest
{
    
    
    private KomeaFS komeaFS;
    private String  tempFolder;
    
    
    
    @Before
    public void before() {
    
    
        tempFolder = "target/komeafs" + new Date().getTime();
        System.setProperty(KomeaFS.KOMEA_SYSTEM_PROPERTY, tempFolder);
        
        komeaFS = new KomeaFS();
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.KomeaFS#getFileSystem(java.lang.String)}.
     */
    @Test
    public final void testGetFileSystem() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.KomeaFS#getFileSystemFolder(java.lang.String)}.
     */
    @Test
    public final void testGetFileSystemFolder() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.KomeaFS#getStorage_path()}.
     */
    @Test
    public final void testGetStorage_path() throws Exception {
    
    
        assertEquals(new File(tempFolder), komeaFS.getStorage_path());
    }
    
}
