
package org.komea.product.backend.business;



import java.util.List;

import org.komea.product.backend.utils.SearchFilter;



/**
 * Returns an interface to manipulate entities as DAO.
 * 
 * @author sleroy

 * @version $Revision: 1.0 $
 */
public interface IDAOObjectStorage<T>
{
    
    
    /**
     * Delete the object from the storage
     * 
     * @param _object
     */
    void delete(T _object);
    
    
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
     * Finds all the entities corresponding the the filter.
     * 
     * @param _filter
     *            the filter
    
     * @return the entities. */
    List<T> find(SearchFilter<T> _filter);
    
    
    /**
     * Save changes.
     */
    void saveChanges();
    
    
    /**
     * Returns all the entities
     * 
    
     * @return the entities */
    List<T> selectAll();
    
    
    /**
     * Inserts/Updates the object
     * 
     * @param _object
     *            the object
     */
    void saveOrUpdate(T _object);
}
