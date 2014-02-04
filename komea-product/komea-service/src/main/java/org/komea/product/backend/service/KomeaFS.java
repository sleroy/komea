
package org.komea.product.backend.service;



import java.io.File;

import org.komea.product.backend.plugin.api.InjectSetting;
import org.komea.product.backend.plugin.api.Properties;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.service.business.ISettingProxy;
import org.komea.product.backend.storage.PluginFileSystem;
import org.springframework.stereotype.Service;



@Service
@Properties(@Property(
        key = "storage_path",
        description = "Path to store informations of the plugins",
        type = File.class,
        value = "komea"))
public class KomeaFS implements IKomeaFS
{
    
    
    private ISettingProxy<File> storage_path;
    
    
    
    public KomeaFS() {
    
    
        super();
    }
    
    
    @Override
    public IPluginFileSystem getFileSystem(final String _fileSystemName) {
    
    
        return new PluginFileSystem(getPath(new File(storage_path.get().getAbsolutePath(),
                _fileSystemName)));
    }
    
    
    @InjectSetting
    public ISettingProxy<File> getStorage_path() {
    
    
        return storage_path;
    }
    
    
    public void setStorage_path(final ISettingProxy<File> _storage_path) {
    
    
        storage_path = _storage_path;
    }
    
    
    private File getPath(final File _file) {
    
    
        if (!_file.isAbsolute()) { throw new IllegalArgumentException(
                "File system names should be a directory folder"); }
        if (!_file.exists() && !_file.mkdirs()) { throw new IllegalArgumentException(
                "Cannot create the folder for the FS " + _file.getName()); }
        return _file;
    }
    
}
