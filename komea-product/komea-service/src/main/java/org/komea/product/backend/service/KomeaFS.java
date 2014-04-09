
package org.komea.product.backend.service;



import java.io.File;

import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.backend.service.fs.IPluginFileSystem;
import org.komea.product.backend.storage.PluginFileSystem;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;



/**
 */
@Service
public class KomeaFS implements IKomeaFS
{
    
    
    /**
     * 
     */
    public static final String      STORAGE_PATH_KEY = "storage_path";
    
    
    /**
     * 
     */
    private static final String     ENV_KOMEA_HOME   = "ENV_KOMEA_HOME";
    
    
    /**
     * 
     */
    private static final String     KOMEA            = ".komea";
    
    
    /**
     * 
     */
    private static final String     KOMEA_DIR        = "KOMEA_DIR";
    
    
    private static org.slf4j.Logger LOGGER           = LoggerFactory.getLogger("komea-filesystem");
    
    
    
    public KomeaFS() {
    
    
        super();
    }
    
    
    /**
     * Method getFileSystem.
     * 
     * @param _fileSystemName
     *            String
     * @return IPluginFileSystem
     * @see org.komea.product.backend.service.fs.IKomeaFS#getFileSystem(String)
     */
    @Override
    public IPluginFileSystem getFileSystem(final String _fileSystemName) {
    
    
        final File computePluginStoragePath = computePluginStoragePath(_fileSystemName);
        return new PluginFileSystem(getPath(computePluginStoragePath));
    }
    
    
    /**
     * Method getFileSystem.
     * 
     * @param _fileSystemName
     *            String
     * @return IPluginFileSystem
     * @see org.komea.product.backend.service.fs.IKomeaFS#getFileSystem(String)
     */
    @Override
    public File getFileSystemFolder(final String _fileSystemName) {
    
    
        final File computePluginStoragePath = computePluginStoragePath(_fileSystemName);
        return getPath(computePluginStoragePath);
    }
    
    
    /**
     * Method getStorage_path.
     * 
     * @return ISettingProxy<File>
     */
    public File getStorage_path() {
    
    
        final String komeaHome = System.getenv(ENV_KOMEA_HOME);
        String storage_path = "";
        if (!Strings.isNullOrEmpty(komeaHome)) {
            storage_path = komeaHome + "/kdata";
        }
        if (Strings.isNullOrEmpty(storage_path)) {
            storage_path = System.getProperty(KOMEA_DIR);
        }
        if (Strings.isNullOrEmpty(storage_path)) {
            storage_path = KOMEA;
        }
        LOGGER.info("Storage path for plugins is {}", storage_path);
        if (storage_path == null) { throw new BeanCreationException(
                "Storage path was not initialized"); }
        
        return new File(storage_path);
    }
    
    
    private File computePluginStoragePath(final String _fileSystemName) {
    
    
        final File pluginPath = getStorage_path();
        if (!pluginPath.exists() && !pluginPath.mkdirs()) { throw new InvalidKomeaFileSystemException(
                "Could not initialize Komea Filesystem : folder could not be created", pluginPath); }
        final String absolutePath = pluginPath.getAbsolutePath();
        
        return new File(absolutePath, _fileSystemName);
    }
    
    
    /**
     * Method getPath.
     * 
     * @param _file
     *            File
     * @return File
     */
    private File getPath(final File _file) {
    
    
        if (!_file.isAbsolute()) { throw new IllegalArgumentException(
                "File system names should be a directory folder"); }
        if (!_file.exists() && !_file.mkdirs()) { throw new IllegalArgumentException(
                "Cannot create the folder for the FS " + _file.getAbsolutePath()); }
        return _file;
    }
    
}
