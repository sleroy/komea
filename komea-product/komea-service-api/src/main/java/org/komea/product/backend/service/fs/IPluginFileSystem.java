
package org.komea.product.backend.service.fs;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * This interface defines the methods to load and store resources from Komea Folder.
 *
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public interface IPluginFileSystem
{
    
    
    /**
     * Backup and renames a resource
     *
     * @param _resourceName
     *            the resource name.
     * @throws IOException
     */
    public void backupAndRename(String _resourceName) throws IOException;
    
    
    /**
     * Tests if a ressource is existing.
     *
     * @param _resourceName
     *            the name of the resource
     * @return true if the resource is existing.
     */
    public boolean existResource(String _resourceName);
    
    
    /**
     * Opens a resource if existing
     *
     * @param _resourceName
     *            the resource name
     * @return the resource.
     * @throws FileNotFoundException
     */
    public InputStream open(String _resourceName) throws FileNotFoundException;
    
    
    /**
     * Store the results from the output stream
     *
     * @param _resourceName
     *            the resource name
     * @throws FileNotFoundException
     */
    public OutputStream store(String _resourceName) throws FileNotFoundException;
    
    
    /**
     * Returns the resource name
     *
     * @param _resourceName
     *            the resource name
     * @return the resource file.
     */
    File getResourceFile(String _resourceName);
}
