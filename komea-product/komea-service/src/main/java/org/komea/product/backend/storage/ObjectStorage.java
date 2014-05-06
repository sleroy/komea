package org.komea.product.backend.storage;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDriver;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.apache.commons.lang.SerializationException;
import org.apache.commons.lang.Validate;
import org.komea.eventory.utils.ClassUtils;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.service.fs.IPluginFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements an Object Storage.
 *
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class ObjectStorage<T> implements IObjectStorage<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectStorage.class);
    private final Class<?> className;
    private final IPluginFileSystem service;
    private final XStream X_STREAM = new XStream(new XppDriver());

    /**
     * Constructor for ObjectStorage.
     *
     * @param _service IPluginFileSystem
     * @param _className Class<?>
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

        final String resourceName = getResource();
        try {
            if (!service.existResource(resourceName)) {
                return (T) ClassUtils.instantiate(className);
            }
            final InputStream open = service.open(resourceName);
            return (T) X_STREAM.fromXML(open);
        } catch (final Exception e) {
            LOGGER.error("Could not retrieve data from file {}, invalid format", resourceName, e);
        }
        return null;

    }

    /**
     * Method set.
     *
     * @param _object T
     * @see org.komea.product.backend.service.fs.IObjectStorage#set(T)
     */
    @Override
    public synchronized void set(final T _object) {

        try {
            X_STREAM.toXML(_object, service.store(getResource()));
        } catch (final FileNotFoundException e) {
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
