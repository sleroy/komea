
package org.komea.product.backend.storage;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.service.fs.IPluginFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;



/**
 * This class implements an Object Storage.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class ObjectStorage<T> implements IObjectStorage<T>
{
    
    
    private static final Logger     LOGGER   = LoggerFactory.getLogger(ObjectStorage.class);
    private final Class<?>          className;
    private final IPluginFileSystem service;
    private final XStream           X_STREAM = new XStream(new XppDriver());
    
    
    
    /**
     * Constructor for ObjectStorage.
     * 
     * @param _service
     *            IPluginFileSystem
     * @param _className
     *            Class<?>
     */
    public ObjectStorage(final IPluginFileSystem _service, final Class<?> _className) {
    
    
        service = _service;
        className = _className;
        
    }
    
    
    /**
     * Method get.
     * 
     * @return T
     * @see org.komea.product.backend.service.fs.IObjectStorage#get()
     */
    @Override
    public synchronized T get() {
    
    
        final String resourceName = getResource();
        try {
            
            if (!service.existResource(resourceName)) { return null; }
            final InputStream open = service.open(resourceName);
            return (T) X_STREAM.fromXML(open);
        } catch (final Exception e) {
            LOGGER.error("Could not retrieve data from file {}", resourceName, e);
        }
        return null;
        
    }
    
    
    /**
     * Method set.
     * 
     * @param _object
     *            T
     * @see org.komea.product.backend.service.fs.IObjectStorage#set(T)
     */
    @Override
    public synchronized void set(final T _object) {
    
    
        final ByteArrayOutputStream out = new ByteArrayOutputStream(10000);
        X_STREAM.toXML(_object, out);
        final InputStream decodedInput = new ByteArrayInputStream(out.toByteArray());
        service.store(getResource(), decodedInput);
        
        
    }
    
    
    /**
     * Method getResource.
     * 
     * @return String
     */
    private String getResource() {
    
    
        return className.getName() + ".xml";
    }
    
}
