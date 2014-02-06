
package org.komea.product.backend.storage;



import java.util.ArrayList;
import java.util.List;

import org.komea.product.backend.fs.IObjectStorage;
import org.komea.product.backend.service.business.IDAOObjectStorage;
import org.komea.product.backend.utils.SearchFilter;



/**
 * This class defines a dao storage built upon an object storage.
 * 
 * @author sleroy
 */
public class DAOStorage<T> implements IDAOObjectStorage<T>
{
    
    
    private final IObjectStorage<DAOStorageIndex<T>> storage;
    private DAOStorageIndex<T>                       daoStorageIndex;
    private boolean                                  saveOnChangeFlag;
    
    
    
    public DAOStorage(final IObjectStorage<DAOStorageIndex<T>> _registerStorage) {
    
    
        super();
        storage = _registerStorage;
        daoStorageIndex = storage.get();
        if (daoStorageIndex == null) {
            daoStorageIndex = new DAOStorageIndex<T>();
        }
    }
    
    
    @Override
    public void delete(final T _object) {
    
    
        daoStorageIndex.getObjectIndex().remove(_object);
        if (saveOnChangeFlag) {
            saveChanges();
        }
        
    }
    
    
    @Override
    public void deleteAll() {
    
    
        daoStorageIndex.getObjectIndex().clear();
        ;
        
        if (saveOnChangeFlag) {
            saveChanges();
        }
    }
    
    
    @Override
    public void disableSaveOnChange() {
    
    
        saveOnChangeFlag = false;
        
    }
    
    
    @Override
    public void enableSaveOnChange() {
    
    
        saveOnChangeFlag = true;
        
    }
    
    
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
    
    
    @Override
    public void saveChanges() {
    
    
        storage.set(daoStorageIndex);
        
    }
    
    
    @Override
    public List<T> selectAll() {
    
    
        return daoStorageIndex.getObjectIndex();
    }
    
    
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
