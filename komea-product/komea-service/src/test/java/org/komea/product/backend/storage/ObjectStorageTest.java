/**
 * 
 */

package org.komea.product.backend.storage;



import java.io.FileNotFoundException;
import java.io.StringBufferInputStream;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.fs.IPluginFileSystem;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.thoughtworks.xstream.XStream;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class ObjectStorageTest
{
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.storage.ObjectStorage#ObjectStorage(org.komea.product.backend.service.fs.IPluginFileSystem, java.lang.Class)}
     * .
     * 
     * @throws FileNotFoundException
     */
    @Test
    public final void testObjectStorage() throws FileNotFoundException {
    
    
        final IPluginFileSystem mock =
                Mockito.mock(IPluginFileSystem.class, Mockito.withSettings().verboseLogging());
        
        final ObjectStorage<String> objectStorage = new ObjectStorage<String>(mock, String.class);
        Mockito.when(mock.existResource(Matchers.anyString())).thenReturn(Boolean.TRUE);
        Mockito.when(mock.open(Matchers.anyString())).thenReturn(
                new StringBufferInputStream(new XStream().toXML("Yoda is a coward")));
        Assert.assertEquals("Yoda is a coward", objectStorage.get());
    }
}
