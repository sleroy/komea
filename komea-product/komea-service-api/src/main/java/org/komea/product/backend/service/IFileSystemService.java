
package org.komea.product.backend.service;



import java.io.InputStream;
import java.io.OutputStream;



/**
 * This interface defines the methods to load and store resources from Komea Folder.
 * 
 * @author sleroy
 */
public interface IFileSystemService
{
    
    
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
     */
    public InputStream open(String _resourceName);
    
    
    /**
     * Store the results from the output stream
     * 
     * @param _resourceName
     *            the resource name
     * @param _outputStream
     *            the output stream.
     */
    public void store(String _resourceName, OutputStream _outputStream);
}
