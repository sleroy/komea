
package org.komea.product.backend.service;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



;


/**
 * This class defines service offering possibilities to load / store files in the Komea file system
 * 
 * @author sleroy
 */

public class PluginFileSystem implements IPluginFileSystem
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PluginFileSystem.class);
    
    private final File          fileSystemFolder;
    
    
    
    public PluginFileSystem(final File _folder) {
    
    
        super();
        fileSystemFolder = _folder;
        
    }
    
    
    @Override
    public boolean existResource(final String _resourceName) {
    
    
        return getResourceInFS(_resourceName).exists();
    }
    
    
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
    
    
    private File getResource(final File _file) {
    
    
        if (!_file.isAbsolute()) { throw new IllegalArgumentException(
                "The path should be absolute " + _file); }
        return _file;
    }
    
    
    private File getResourceInFS(final String _resourceName) {
    
    
        return getResource(new File(fileSystemFolder, _resourceName));
    }
    
}
