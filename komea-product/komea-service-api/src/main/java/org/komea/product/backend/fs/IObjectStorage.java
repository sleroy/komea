
package org.komea.product.backend.fs;



public interface IObjectStorage<T>
{
    
    
    /**
     * Returns the object.
     */
    T get();
    
    
    /**
     * Store an unique object in the storage
     * 
     * @param _object
     *            the object
     */
    void set(T _object);
    
}
