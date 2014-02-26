
package org.komea.product.backend.storage;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.komea.product.backend.service.InvalidKomeaFileSystemException;
import org.komea.product.backend.service.fs.IPluginFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



;


/**
 * This class defines service offering possibilities to load / store files in the Komea file system
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */

public class PluginFileSystem implements IPluginFileSystem
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginFileSystem.class);
    
    private final File          fileSystemFolder;
    
    
    
    /**
     * Constructor for PluginFileSystem.
     * 
     * @param _folder
     *            File
     */
    public PluginFileSystem(final File _folder) {
    
    
        super();
        fileSystemFolder = _folder;
        if (!fileSystemFolder.exists() && !fileSystemFolder.mkdirs()) { throw new InvalidKomeaFileSystemException(
                "Could not initialize Plugin Filesystem : folder could not be created",
                fileSystemFolder); }
        
    }
    
    
    /**
     * Method existResource.
     * 
     * @param _resourceName
     *            String
     * @return boolean
     * @see org.komea.product.backend.service.fs.IPluginFileSystem#existResource(String)
     */
    @Override
    public boolean existResource(final String _resourceName) {
    
    
        return getResourceInFS(_resourceName).exists();
    }
    
    
    /**
     * Method open.
     * 
     * @param _resourceName
     *            String
     * @return InputStream
     * @see org.komea.product.backend.service.fs.IPluginFileSystem#open(String)
     */
    @Override
    public InputStream open(final String _resourceName) {
    
    
        LOGGER.debug("Open resource in FS {} : {}", fileSystemFolder, _resourceName);
        try {
            return new BufferedInputStream(new FileInputStream(new File(fileSystemFolder,
                    _resourceName)));
        } catch (final FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    
    /**
     * Method store.
     * 
     * @param _resourceName
     *            String
     * @param _inputStream
     *            InputStream
     * @see org.komea.product.backend.service.fs.IPluginFileSystem#store(String, InputStream)
     */
    @Override
    public void store(final String _resourceName, final InputStream _inputStream) {
    
    
        LOGGER.debug("Store resource in FS {} : {}", fileSystemFolder, _resourceName);
        final File resource = getResourceInFS(_resourceName);
        BufferedOutputStream bufferedOuputStream = null;
        try {
            bufferedOuputStream = new BufferedOutputStream(new FileOutputStream(resource));
            IOUtils.copy(_inputStream, bufferedOuputStream);
        } catch (final Exception e) {
            throw new IllegalArgumentException("Resource " + resource, e);
        } finally {
            IOUtils.closeQuietly(bufferedOuputStream);
            
            
        }
    }
    
    
    /**
     * Method getResource.
     * 
     * @param _file
     *            File
     * @return File
     */
    private File getResource(final File _file) {
    
    
        if (!_file.isAbsolute()) { throw new IllegalArgumentException(
                "The path should be absolute " + _file); }
        return _file;
    }
    
    
    /**
     * Method getResourceInFS.
     * 
     * @param _resourceName
     *            String
     * @return File
     */
    private File getResourceInFS(final String _resourceName) {
    
    
        return getResource(new File(fileSystemFolder, _resourceName));
    }
    
}
