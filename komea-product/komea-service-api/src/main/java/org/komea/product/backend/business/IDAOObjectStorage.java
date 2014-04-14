
package org.komea.product.backend.business;



import java.util.List;

import org.komea.product.backend.utils.SearchFilter;
import org.komea.product.database.api.IHasId;



/**
 * Returns an interface to manipulate entities as DAO.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public interface IDAOObjectStorage<T extends IHasId>
{
    
    
    /**
     * Delete the object from the storage
     * 
     * @param _object
     */
    boolean delete(T _object);
    
    
    /**
     * Delete all the records.
     */
    void deleteAll();
    
    
    /**
     * Enables to save the structure on change.
     */
    void disableSaveOnChange();
    
    
    /**
     * Enables to save the structure on change.
     */
    void enableSaveOnChange();
    
    
    /**
     * Tests if the object is present in the dao.
     * 
     * @param _object
     */
    boolean exists(T _object);
    
    
    /**
     * Finds all the entities corresponding the the filter.
     * 
     * @param _filter
     *            the filter
     * @return the entities.
     */
    List<T> find(SearchFilter<T> _filter);
    
    
    /**
     * Save changes.
     */
    void saveChanges();
    
    
    /**
     * Inserts/Updates the object
     * 
     * @param _object
     *            the object
     */
    void saveOrUpdate(T _object);
    
    
    /**
     * Returns all the entities
     * 
     * @return the entities
     */
    List<T> selectAll();
}
