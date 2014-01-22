/**
 * 
 */

package org.komea.product.backend.service;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringBufferInputStream;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;



/**
 * @author sleroy
 */
public class PluginFileSystemTest
{
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.PluginFileSystem#FileSystemService(java.io.File)}.
     */
    @Test
    public final void testFileSystemServiceExist() {
    
    
        final PluginFileSystem fileSystemService =
                new PluginFileSystem(new File("src/test/resources/fakeS/truc").getAbsoluteFile());
        Assert.assertTrue(fileSystemService.existResource("example.txt"));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.PluginFileSystem#FileSystemService(java.io.File)}.
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testFileSystemServiceFail() {
    
    
        final PluginFileSystem fileSystemService =
                new PluginFileSystem(new File("src/test/resources/fakeS/truc"));
        Assert.assertTrue(fileSystemService.existResource("../example.txt"));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.PluginFileSystem#FileSystemService(java.io.File)}.
     * 
     * @throws IOException
     * @throws FileNotFoundException
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
     * Test method for {@link org.komea.product.backend.service.PluginFileSystem#FileSystemService(java.io.File)}.
     * 
     * @throws IOException
     * @throws FileNotFoundException
     */
    @Test
    public final void testFileSystemServiceStore() throws FileNotFoundException, IOException {
    
    
        final PluginFileSystem fileSystemService =
                new PluginFileSystem(new File("src/test/resources/fakeS/truc").getAbsoluteFile());
        final String original =
                IOUtils.toString(new FileInputStream("src/test/resources/fakeS/truc/example.txt"));
        
        fileSystemService.store("example.txt", new StringBufferInputStream("bla.txt"));
        final String received = IOUtils.toString(fileSystemService.open("example.txt"));
        Assert.assertEquals("bla.txt", received);
        
    }
}
