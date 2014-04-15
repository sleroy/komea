/**
 * 
 */

package org.komea.product.backend.service;



import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.service.fs.IPluginFileSystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class KomeaFSTest
{
    
    
    /**
     * 
     */
    private static final int TEST_VALUE = 1024;
    private KomeaFS          komeaFS;
    private String           tempFolder;
    
    
    
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
    
    
        final IPluginFileSystem fileSystem = komeaFS.getFileSystem("truc");
        assertNotNull(fileSystem);
        assertTrue(new File(komeaFS.getStorage_path(), "truc").exists());
        assertTrue(new File(komeaFS.getStorage_path(), "truc").isDirectory());
        
        
        final OutputStream store = fileSystem.store("resource");
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(store);
        objectOutputStream.writeInt(1024);
        objectOutputStream.close();
        store.close();
        
        final File resourceFile = fileSystem.getResourceFile("resource");
        System.out.println(resourceFile);
        assertTrue(resourceFile.exists());
        
        final ObjectInputStream objectInputStream =
                new ObjectInputStream(fileSystem.open("resource"));
        assertEquals(TEST_VALUE, objectInputStream.readInt());
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.KomeaFS#getFileSystemFolder(java.lang.String)}.
     */
    @Test
    public final void testGetFileSystemFolder() throws Exception {
    
    
        final File file = new File(tempFolder, "folder");
        assertEquals(file.getAbsoluteFile(), komeaFS.getFileSystemFolder("folder")
                .getAbsoluteFile());
        assertTrue(file.exists());
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.KomeaFS#getStorage_path()}.
     */
    @Test
    public final void testGetStorage_path() throws Exception {
    
    
        assertEquals(new File(tempFolder), komeaFS.getStorage_path());
    }
    
}
