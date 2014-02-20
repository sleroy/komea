
package org.komea.product.backend.storage;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.utils.SearchFilter;



/**
 * This class defines a dao storage built upon an object storage.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class DAOStorage<T> implements IDAOObjectStorage<T>
{
    
    
    private final IObjectStorage<DAOStorageIndex<T>> storage;
    private DAOStorageIndex<T>                       daoStorageIndex;
    private boolean                                  saveOnChangeFlag;
    
    
    
    /**
     * Constructor for DAOStorage.
     * @param _registerStorage IObjectStorage<DAOStorageIndex<T>>
     */
    public DAOStorage(final IObjectStorage<DAOStorageIndex<T>> _registerStorage) {
    
    
        super();
        storage = _registerStorage;
        daoStorageIndex = storage.get();
        if (daoStorageIndex == null) {
            daoStorageIndex = new DAOStorageIndex<T>();
        }
    }
    
    
    /**
     * Method delete.
     * @param _object T
     * @see org.komea.product.backend.business.IDAOObjectStorage#delete(T)
     */
    @Override
    public void delete(final T _object) {
    
    
        daoStorageIndex.getObjectIndex().remove(_object);
        if (saveOnChangeFlag) {
            saveChanges();
        }
        
    }
    
    
    /**
     * Method deleteAll.
     * @see org.komea.product.backend.business.IDAOObjectStorage#deleteAll()
     */
    @Override
    public void deleteAll() {
    
    
        daoStorageIndex.getObjectIndex().clear();
        ;
        
        if (saveOnChangeFlag) {
            saveChanges();
        }
    }
    
    
    /**
     * Method disableSaveOnChange.
     * @see org.komea.product.backend.business.IDAOObjectStorage#disableSaveOnChange()
     */
    @Override
    public void disableSaveOnChange() {
    
    
        saveOnChangeFlag = false;
        
    }
    
    
    /**
     * Method enableSaveOnChange.
     * @see org.komea.product.backend.business.IDAOObjectStorage#enableSaveOnChange()
     */
    @Override
    public void enableSaveOnChange() {
    
    
        saveOnChangeFlag = true;
        
    }
    
    
    /**
     * Method find.
     * @param _filter SearchFilter<T>
     * @return List<T>
     * @see org.komea.product.backend.business.IDAOObjectStorage#find(SearchFilter<T>)
     */
    @Override
    public List<T> find(final SearchFilter<T> _filter) {
    
    
        final List<T> res = new ArrayList<T>(daoStorageIndex.getObjectIndex().size());
        for (final T object : daoStorageIndex.getObjectIndex()) {
            if (_filter.match(object)) {
                res.add(object);
            }
        }
        return res;
    }
    
    
    /**
     * Method saveChanges.
     * @see org.komea.product.backend.business.IDAOObjectStorage#saveChanges()
     */
    @Override
    public void saveChanges() {
    
    
        storage.set(daoStorageIndex);
        
    }
    
    
    /**
     * Method selectAll.
     * @return List<T>
     * @see org.komea.product.backend.business.IDAOObjectStorage#selectAll()
     */
    @Override
    public List<T> selectAll() {
    
    
        return daoStorageIndex.getObjectIndex();
    }
    
    
    /**
     * Method update.
     * @param _object T
     * @see org.komea.product.backend.business.IDAOObjectStorage#update(T)
     */
    @Override
    public void update(final T _object) {
    
    
        final int indexOf = daoStorageIndex.getObjectIndex().indexOf(_object);
        if (indexOf == -1) {
            daoStorageIndex.getObjectIndex().add(_object);
            
        } else {
            daoStorageIndex.getObjectIndex().set(indexOf, _object);
        }
        if (saveOnChangeFlag) {
            saveChanges();
        }
    }
    
}
