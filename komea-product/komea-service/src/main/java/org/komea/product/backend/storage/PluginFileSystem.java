
package org.komea.product.backend.storage;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.komea.product.backend.api.exceptions.InvalidKomeaFileSystemException;
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
        Validate.isTrue(_folder.isDirectory());
        
        if (!fileSystemFolder.exists() && !fileSystemFolder.mkdirs()) {
            throw new InvalidKomeaFileSystemException(
                    "Could not initialize Plugin Filesystem : folder could not be created",
                    fileSystemFolder);
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.fs.IPluginFileSystem#backupAndRename(java.lang.String)
     */
    @Override
    public void backupAndRename(final String _resourceName) throws IOException {


        final File resourceFile = getResourceFile(_resourceName);
        FileUtils.copyFile(resourceFile, new File(resourceFile, "~" + new DateTime().toString()));
        FileUtils.deleteQuietly(resourceFile);
        
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
    
    
        return getResourceFile(_resourceName).exists();
    }
    
    
    /**
     * Returns the location where the resource is stored
     *
     * @param _resourceName
     *            the resource name
     * @return the file.
     */
    @Override
    public File getResourceFile(final String _resourceName) {
    
    
        final File file = new File(fileSystemFolder, _resourceName);
        Validate.isTrue(file.isAbsolute());
        return file;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.fs.IPluginFileSystem#open(java.lang.String)
     */
    @Override
    public InputStream open(final String _resourceName) throws FileNotFoundException {
    
    
        LOGGER.debug("Open resource in FS {} : {}", fileSystemFolder, _resourceName);
        
        return new FileInputStream(getResourceFile(_resourceName));
    }
    
    
    /**
     * Method store.
     *
     * @param _resourceName
     *            String
     * @param _inputStream
     *            InputStream
     * @throws FileNotFoundException
     * @see org.komea.product.backend.service.fs.IPluginFileSystem#store(String, InputStream)
     */
    @Override
    public FileOutputStream store(final String _resourceName) throws FileNotFoundException {
    
    
        LOGGER.debug("Store resource in FS {} : {}", fileSystemFolder, _resourceName);
        
        final File resource = getResourceFile(_resourceName);
        return new FileOutputStream(resource);
        
    }
}
