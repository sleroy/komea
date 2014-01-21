
package org.komea.product.backend.service;



/**
 * This interface defines the methods to load/store informations of a plugin in a dedicated place.
 * 
 * @author sleroy
 */
public interface IKomeaFS
{
    
    
    /**
     * Returns a component to manage a dedicated file system.
     * 
     * @param _fileSystemName
     *            the file system name;
     * @return the file system service.
     */
    IFileSystemService getFileSystem(String _fileSystemName);
}
