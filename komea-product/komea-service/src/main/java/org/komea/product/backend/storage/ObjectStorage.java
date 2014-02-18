
package org.komea.product.backend.storage;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.komea.product.backend.fs.IObjectStorage;
import org.komea.product.backend.service.IPluginFileSystem;

import com.thoughtworks.xstream.XStream;



/**
 * This class implements an Object Storage.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class ObjectStorage<T> implements IObjectStorage<T>
{
    
    
    private final XStream            X_STREAM = new XStream();
    private final IPluginFileSystem service;
    private final Class<?>           className;
    
    
    
    /**
     * Constructor for ObjectStorage.
     * @param _service IPluginFileSystem
     * @param _className Class<?>
     */
    public ObjectStorage(final IPluginFileSystem _service, final Class<?> _className) {
    
    
        service = _service;
        className = _className;
        
    }
    
    
    /**
     * Method get.
     * @return T
     * @see org.komea.product.backend.fs.IObjectStorage#get()
     */
    @Override
    public T get() {
    
    
        final String resourceName = getResource();
        final InputStream open = service.open(resourceName);
        return (T) X_STREAM.fromXML(open);
        
    }
    
    
    /**
     * Method set.
     * @param _object T
     * @see org.komea.product.backend.fs.IObjectStorage#set(T)
     */
    @Override
    public void set(final T _object) {
    
    
        final ByteArrayOutputStream out = new ByteArrayOutputStream(10000);
        X_STREAM.toXML(_object, out);
        final InputStream decodedInput = new ByteArrayInputStream(out.toByteArray());
        service.store(getResource(), decodedInput);
        
        
    }
    
    
    /**
     * Method getResource.
     * @return String
     */
    private String getResource() {
    
    
        return className + ".xml";
    }
    
}
