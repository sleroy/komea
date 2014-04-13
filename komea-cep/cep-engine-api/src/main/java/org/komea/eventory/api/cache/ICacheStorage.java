/**
 * 
 */

package org.komea.eventory.api.cache;



import java.util.Collection;
import java.util.Iterator;



/**
 * This interface defines the cache to store the events received by a query inside the CEP.
 * 
 * @author sleroy
 */
public interface ICacheStorage<T>
{
    
    
    /**
     * Clear the cache.
     */
    void clear();
    
    
    /**
     * Returns all values.
     * 
     * @return all the values from the cache.
     */
    Collection<T> getAllValues();
    
    
    /**
     * Returns the list of items presents in the cache.
     * 
     * @return the list of items.
     */
    Iterator<T> getIterator();
    
    
    /**
     * Push an item into the cache. The items are stored in the order they are pushed.
     * 
     * @param _item
     *            the item.
     */
    void push(T _item);
    
    
    /**
     * Removes an event out this cache.
     * 
     * @param _event
     */
    void remove(T _event);
    
    
    /**
     * Returns the number of items.
     * 
     * @return the number of items.
     */
    long size();
}
