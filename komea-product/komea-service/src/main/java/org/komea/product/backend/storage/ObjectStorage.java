
package org.komea.product.backend.storage;



import org.apache.commons.lang.SerializationException;
import org.apache.commons.lang3.Validate;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.service.fs.IPluginFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

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
        Validate.notNull(service);
        Validate.notNull(_className);
        
    }
    
    
    /**
     * Method get.
     * 
     * @return T
     * @see org.komea.product.backend.service.fs.IObjectStorage#get()
     */
    @Override
    public synchronized T get() {
    
    
        T newBean = null;
        final String resourceName = getResource();
        try {
            if (service.existResource(resourceName)) {
                
                newBean = (T) X_STREAM.fromXML(service.open(resourceName));
            }
        } catch (final Exception e) {
            LOGGER.error("Could not retrieve data from file {}, invalid format", resourceName, e);
        } finally {
            if (newBean == null) {
                newBean = (T) BeanUtils.instantiate(className);
            }
        }
        return newBean;
        
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
    
    
        try {
            
            X_STREAM.toXML(_object, service.store(getResource()));
        } catch (final Exception e) {
            throw new SerializationException(e.getMessage(), e);
        }
        
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
