
package org.komea.product.backend.storage;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.business.IDAOObjectStorage;
import org.komea.product.backend.service.fs.IObjectStorage;
import org.komea.product.backend.utils.SearchFilter;
import org.komea.product.database.api.IHasId;

import com.google.common.collect.Lists;



/**
 * This class defines a dao storage built upon an object storage.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class DAOObjectStorage<T extends IHasId> implements IDAOObjectStorage<T>
{
    
    
    private DAOStorageIndex<T>                       daoStorageIndex;
    private boolean                                  saveOnChangeFlag = true;
    private final IObjectStorage<DAOStorageIndex<T>> storage;
    
    
    
    /**
     * Constructor for DAOObjectStorage.
     * 
     * @param _registerStorage
     *            IObjectStorage<DAOStorageIndex<T>>
     */
    public DAOObjectStorage(final IObjectStorage<DAOStorageIndex<T>> _registerStorage) {
    
    
        super();
        storage = _registerStorage;
        daoStorageIndex = storage.get();
        if (daoStorageIndex == null) {
            daoStorageIndex = new DAOStorageIndex<T>();
        }
    }
    
    
    /**
     * Method delete.
     * 
     * @param _object
     *            T
     * @see org.komea.product.backend.business.IDAOObjectStorage#delete(T)
     */
    @Override
    public synchronized boolean delete(final T _object) {
    
    
        final boolean res = daoStorageIndex.getObjectIndex().containsKey(_object.getId());
        daoStorageIndex.getObjectIndex().remove(_object.getId());
        if (saveOnChangeFlag) {
            saveChanges();
        }
        return res;
        
    }
    
    
    /**
     * Method deleteAll.
     * 
     * @see org.komea.product.backend.business.IDAOObjectStorage#deleteAll()
     */
    @Override
    public synchronized void deleteAll() {
    
    
        daoStorageIndex.getObjectIndex().clear();
        
        
        if (saveOnChangeFlag) {
            saveChanges();
        }
    }
    
    
    /**
     * Method disableSaveOnChange.
     * 
     * @see org.komea.product.backend.business.IDAOObjectStorage#disableSaveOnChange()
     */
    @Override
    public void disableSaveOnChange() {
    
    
        saveOnChangeFlag = false;
        
    }
    
    
    /**
     * Method enableSaveOnChange.
     * 
     * @see org.komea.product.backend.business.IDAOObjectStorage#enableSaveOnChange()
     */
    @Override
    public void enableSaveOnChange() {
    
    
        saveOnChangeFlag = true;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.business.IDAOObjectStorage#exists(java.lang.Object)
     */
    @Override
    public boolean exists(final T _object) {
    
    
        return daoStorageIndex.getObjectIndex().containsKey(_object.getId());
    }
    
    
    /**
     * Method find.
     * 
     * @param _filter
     *            SearchFilter<T>
     * @return List<T>
     * @see org.komea.product.backend.business.IDAOObjectStorage#find(SearchFilter<T>)
     */
    @Override
    public synchronized List<T> find(final SearchFilter<T> _filter) {
    
    
        final List<T> res = new ArrayList<T>(daoStorageIndex.getObjectIndex().size());
        for (final T object : daoStorageIndex.getObjectIndex().values()) {
            if (_filter.match(object)) {
                res.add(object);
            }
        }
        return res;
    }
    
    
    /**
     * Method saveChanges.
     * 
     * @see org.komea.product.backend.business.IDAOObjectStorage#saveChanges()
     */
    @Override
    public void saveChanges() {
    
    
        storage.set(daoStorageIndex);
        
    }
    
    
    /**
     * Method update.
     * 
     * @param _object
     *            T
     * @see org.komea.product.backend.business.IDAOObjectStorage#saveOrUpdate(T)
     */
    @Override
    public synchronized void saveOrUpdate(final T _object) {
    
    
        if (_object.getId() == null) {
            daoStorageIndex.incID();
            _object.setId(daoStorageIndex.getId());
            daoStorageIndex.put(_object);
        }
        
        
        if (saveOnChangeFlag) {
            saveChanges();
        }
    }
    
    
    /**
     * Method selectAll.
     * 
     * @return List<T>
     * @see org.komea.product.backend.business.IDAOObjectStorage#selectAll()
     */
    @Override
    public synchronized List<T> selectAll() {
    
    
        return Lists.newArrayList(daoStorageIndex.getObjectIndex().values());
    }
    
}
