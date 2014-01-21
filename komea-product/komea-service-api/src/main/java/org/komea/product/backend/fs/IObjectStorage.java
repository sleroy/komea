
package org.komea.product.backend.fs;



import java.util.List;



public interface IObjectStorage<T>
{
    
    
    /**
     * Returns the primary key
     * 
     * @param _primaryKey
     *            the primary key.
     */
    T get(int _primaryKey);
    
    
    /**
     * Returns the list of keys.
     * 
     * @return
     */
    List<Integer> getKeys();
    
    
    /**
     * Returns the unique object (assuming primary key =1)
     * 
     * @return the object
     */
    T getUnique();
    
    
    /**
     * Stores an object
     * 
     * @param _primaryKey
     *            the primary key
     * @param _object
     *            the object.
     */
    void store(int _primaryKey, T _object);
    
    
    /**
     * Store an unique object in the storage
     * 
     * @param _object
     *            the object
     */
    void storeUnique(T _object);
    
}
