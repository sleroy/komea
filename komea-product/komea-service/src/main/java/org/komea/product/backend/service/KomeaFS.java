
package org.komea.product.backend.service;



import java.io.File;

import org.apache.commons.lang3.SystemUtils;
import org.komea.product.backend.api.exceptions.InvalidKomeaFileSystemException;
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
    
    
    public static final String      ENV_KOMEA_HOME        = "KOMEA_HOME";
    
    
    /**
     * Komea configuration folder
     */
    public static final String      KOMEA_FOLDER          = ".komea";
    
    
    public static final String      KOMEA_SYSTEM_PROPERTY = "komea.home";
    
    
    public static final String      STORAGE_PATH_KEY      = "storage_path";
    
    
    private static org.slf4j.Logger LOGGER                = LoggerFactory
                                                                  .getLogger("komea-filesystem");
    
    
    private String                  storage_path;
    
    
    
    public KomeaFS() {
    
    
        super();
        initKomeaFS();
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
     * Method getPath.
     * 
     * @param _file
     *            File
     * @return File
     */
    public File getPath(final File _file) {
    
    
        if (!_file.isAbsolute()) {
            throw new IllegalArgumentException("File system names should be a directory folder");
        }
        if (!_file.exists() && !_file.mkdirs()) {
            throw new IllegalArgumentException("Cannot create the folder for the FS "
                    + _file.getAbsolutePath());
        }
        return _file.getAbsoluteFile();
    }
    
    
    /**
     * Method getStorage_path.
     * 
     * @return ISettingProxy<File>
     */
    public File getStorage_path() {
    
    
        return new File(storage_path);
    }
    
    
    /**
     * Initialisation of Komea FS.
     */
    
    public void initKomeaFS() {
    
    
        LOGGER.info("KOMEA Plugin Filesystem >>>>> ");
        
        final String komeaHome = System.getenv(ENV_KOMEA_HOME);
        storage_path = "";
        if (!Strings.isNullOrEmpty(komeaHome)) {
            storage_path = komeaHome + "/kdata";
        }
        if (Strings.isNullOrEmpty(storage_path)) {
            storage_path = System.getProperty(KOMEA_SYSTEM_PROPERTY);
        }
        if (Strings.isNullOrEmpty(storage_path)) {
            storage_path = new File(SystemUtils.getUserDir(), KOMEA_FOLDER).getAbsolutePath();
        }
        LOGGER.info("\n\t\t>>>>> Storage path for plugins is {}", storage_path);
        if (storage_path == null) {
            throw new BeanCreationException("Storage path was not initialized");
        }
        
    }
    
    
    private File computePluginStoragePath(final String _fileSystemName) {
    
    
        final File pluginPath = getStorage_path();
        if (!pluginPath.exists() && !pluginPath.mkdirs()) {
            throw new InvalidKomeaFileSystemException(
                    "Could not initialize Komea Filesystem : folder could not be created",
                    pluginPath);
        }
        final String absolutePath = pluginPath.getAbsolutePath();
        
        return new File(absolutePath, _fileSystemName);
    }
    
}
