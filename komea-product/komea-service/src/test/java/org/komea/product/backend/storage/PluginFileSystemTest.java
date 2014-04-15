/**
 * 
 */

package org.komea.product.backend.storage;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class PluginFileSystemTest
{
    
    
    /**
     * Test method for {@link org.komea.product.backend.storage.PluginFileSystem#FileSystemService(java.io.File)}.
     */
    @Test
    public final void testFileSystemServiceExist() {
    
    
        final PluginFileSystem fileSystemService =
                new PluginFileSystem(new File("src/test/resources/fakeS/truc").getAbsoluteFile());
        Assert.assertTrue(fileSystemService.existResource("example.txt"));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.storage.PluginFileSystem#FileSystemService(java.io.File)}.
     */
    @Test(
        expected = IllegalArgumentException.class)
    public final void testFileSystemServiceFail() {
    
    
        final PluginFileSystem fileSystemService =
                new PluginFileSystem(new File("src/test/resources/fakeS/truc"));
        Assert.assertTrue(fileSystemService.existResource("../example.txt"));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.storage.PluginFileSystem#FileSystemService(java.io.File)}.
     * 
     * @throws FileNotFoundException
     *             * @throws IOException
     */
    @Test
    public final void testFileSystemServiceOpen() throws FileNotFoundException, IOException {
    
    
        final PluginFileSystem fileSystemService =
                new PluginFileSystem(new File("src/test/resources/fakeS/truc").getAbsoluteFile());
        final String original =
                IOUtils.toString(new FileInputStream("src/test/resources/fakeS/truc/example.txt"));
        final String received = IOUtils.toString(fileSystemService.open("example.txt"));
        Assert.assertEquals(original, received);
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.storage.PluginFileSystem#FileSystemService(java.io.File)}.
     * 
     * @throws FileNotFoundException
     *             * @throws IOException
     */
    @Test
    public final void testFileSystemServiceStore() throws FileNotFoundException, IOException {
    
    
        final File file = new File("target/src/test/resources/fakeS/truc");
        file.mkdirs();
        
        final PluginFileSystem fileSystemService = new PluginFileSystem(file.getAbsoluteFile());
        
        
        final FileOutputStream store = fileSystemService.store("example.txt");
        IOUtils.write("bla.txt", store);
        
        final String received = IOUtils.toString(fileSystemService.open("example.txt"));
        Assert.assertEquals("bla.txt", received);
        
    }
    
}
