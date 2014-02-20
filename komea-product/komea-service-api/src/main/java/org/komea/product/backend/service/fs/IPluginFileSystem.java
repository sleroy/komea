
package org.komea.product.backend.service.fs;



import java.io.InputStream;



/**
 * This interface defines the methods to load and store resources from Komea Folder.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public interface IPluginFileSystem
{
    
    
    /**
     * Tests if a ressource is existing.
     * 
     * @param _resourceName
     *            the name of the resource
    
     * @return true if the resource is existing. */
    public boolean existResource(String _resourceName);
    
    
    /**
     * Opens a resource if existing
     * 
     * @param _resourceName
     *            the resource name
    
     * @return the resource. */
    public InputStream open(String _resourceName);
    
    
    /**
     * Store the results from the output stream
     * 
     * @param _resourceName
     *            the resource name
     * @param _inputStream
     *            the input stream.
     */
    public void store(String _resourceName, InputStream _inputStream);
}
