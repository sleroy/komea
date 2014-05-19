/**
 * 
 */

package org.komea.product.backend.storage;



import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.StringBufferInputStream;

import org.apache.commons.io.output.NullOutputStream;
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
    
    
    private static final String SALUTJESUISUNECHAINE = "salutjesuisunechaine";
    private static final String TARGET_TMP_XML       = "target/tmp.xml";
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.storage.ObjectStorage#ObjectStorage(org.komea.product.backend.service.fs.IPluginFileSystem, java.lang.Class)}
     * .
     * 
     * @throws FileNotFoundException
     */
    @Test
    public final void testGet_Exist() throws FileNotFoundException {
    
    
        final IPluginFileSystem mock =
                Mockito.mock(IPluginFileSystem.class, Mockito.withSettings().verboseLogging());
        
        final ObjectStorage<String> objectStorage = new ObjectStorage<String>(mock, String.class);
        Mockito.when(mock.existResource(Matchers.anyString())).thenReturn(Boolean.TRUE);
        Mockito.when(mock.open(Matchers.anyString())).thenReturn(
                new StringBufferInputStream(new XStream().toXML("Yoda is a coward")));
        Assert.assertEquals("Yoda is a coward", objectStorage.get());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.storage.ObjectStorage#get()}.
     */
    @Test
    public void testGet_NotExist() throws Exception {
    
    
        final IPluginFileSystem mock =
                Mockito.mock(IPluginFileSystem.class, Mockito.withSettings().verboseLogging());
        
        final ObjectStorage<String> objectStorage = new ObjectStorage<String>(mock, String.class);
        final String string = objectStorage.get();
        assertNotNull(string);
        assertTrue(string.isEmpty());
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.storage.ObjectStorage#set(java.lang.Object)}.
     */
    @Test
    public void testSet() throws Exception {
    
    
        final IPluginFileSystem mock =
                Mockito.mock(IPluginFileSystem.class, Mockito.withSettings().verboseLogging());
        
        final ObjectStorage<String> objectStorage = new ObjectStorage<String>(mock, String.class);
        when(mock.store(Matchers.anyString())).thenReturn(new NullOutputStream());
        objectStorage.set(SALUTJESUISUNECHAINE);
        verify(mock, times(1)).store(Matchers.anyString());
    }
}
