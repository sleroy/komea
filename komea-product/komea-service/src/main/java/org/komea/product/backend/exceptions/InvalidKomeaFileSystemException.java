/**
 * 
 */

package org.komea.product.backend.exceptions;



import java.io.File;



/**
 * @author sleroy
 */
public class InvalidKomeaFileSystemException extends RuntimeException
{
    
    
    private final File pluginPath;
    
    
    
    /**
     * @param _messageString
     * @param _pluginPath
     */
    public InvalidKomeaFileSystemException(final String _messageString, final File _pluginPath) {
    
    
        super(_messageString + _pluginPath);
        pluginPath = _pluginPath;
        
    }
    
    
    public File getPluginPath() {
    
    
        return pluginPath;
    }
    
}
