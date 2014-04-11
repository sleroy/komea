
package org.komea.product.backend.storage;



import java.util.HashMap;
import java.util.Map;

import org.komea.product.database.api.IHasId;



/**
 * DAO Object storage index.
 */
public class DAOStorageIndex<T extends IHasId>
{
    
    
    private int             id;
    private Map<Integer, T> objectIndex = new HashMap<Integer, T>();
    
    
    
    public int getId() {
    
    
        return id;
    }
    
    
    /**
     * Method getObjectIndex.
     * 
     * @return List<T>
     */
    public Map<Integer, T> getObjectIndex() {
    
    
        return objectIndex;
    }
    
    
    public void incID() {
    
    
        id++;
    }
    
    
    /**
     * Puts a value.
     * 
     * @param _object
     *            the object.
     */
    public void put(final T _object) {
    
    
        objectIndex.put(_object.getId(), _object);
    }
    
    
    /**
     * Method setObjectIndex.
     * 
     * @param _objectIndex
     *            List<T>
     */
    public void setObjectIndex(final Map<Integer, T> _objectIndex) {
    
    
        objectIndex = _objectIndex;
    }
}
