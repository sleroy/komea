
package org.komea.product.backend.service;



import java.io.File;

import javax.annotation.PostConstruct;

import org.komea.product.backend.plugin.api.InjectSetting;
import org.komea.product.backend.service.fs.IKomeaFS;
import org.komea.product.backend.service.fs.IPluginFileSystem;
import org.komea.product.backend.storage.PluginFileSystem;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 */
@Service
public class KomeaFS implements IKomeaFS
{
    
    
    /**
     * 
     */
    public static final String      STORAGE_PATH_KEY = "storage_path";
    
    
    private static org.slf4j.Logger LOGGER           = LoggerFactory.getLogger("komea-filesystem");
    
    
    @Autowired
    private ISettingService         settingService;
    
    
    private ISettingProxy<File>     storage_path;
    
    
    
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
    
    
    public ISettingService getSettingService() {
    
    
        return settingService;
    }
    
    
    /**
     * Method getStorage_path.
     * 
     * @return ISettingProxy<File>
     */
    @InjectSetting
    public ISettingProxy<File> getStorage_path() {
    
    
        return storage_path;
    }
    
    
    @PostConstruct
    public void init() {
    
    
        storage_path = settingService.getProxy(STORAGE_PATH_KEY);
        LOGGER.info("Storage path for plugins is " + storage_path);
        if (storage_path == null) { throw new BeanCreationException(
                "Storage path was not initialized"); }
        
    }
    
    
    public void setSettingService(final ISettingService _settingService) {
    
    
        settingService = _settingService;
    }
    
    
    /**
     * Method setStorage_path.
     * 
     * @param _storage_path
     *            ISettingProxy<File>
     */
    public void setStorage_path(final ISettingProxy<File> _storage_path) {
    
    
        storage_path = _storage_path;
    }
    
    
    private File computePluginStoragePath(final String _fileSystemName) {
    
    
        final File pluginPath = storage_path.getValue();
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
