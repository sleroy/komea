
package org.komea.product.backend.storage;



import java.util.ArrayList;
import java.util.List;



/**
 * DAO Object storage index.
 */
public class DAOStorageIndex<T>
{
    
    
    private List<T> objectIndex = new ArrayList<T>(100);
    
    
    
    /**
     * Method getObjectIndex.
     * 
     * @return List<T>
     */
    public List<T> getObjectIndex() {
    
    
        return objectIndex;
    }
    
    
    /**
     * Method setObjectIndex.
     * 
     * @param _objectIndex
     *            List<T>
     */
    public void setObjectIndex(final List<T> _objectIndex) {
    
    
        objectIndex = _objectIndex;
    }
}
