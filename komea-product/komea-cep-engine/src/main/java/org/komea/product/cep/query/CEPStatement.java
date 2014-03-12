/**
 * 
 */

package org.komea.product.cep.query;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.komea.product.cep.api.ICEPEventStorage;
import org.komea.product.cep.api.ICEPStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;



/**
 * This class handle a list of event storages and provides aggregate view of these events.
 * 
 * @author sleroy
 */
public class CEPStatement<T extends Serializable> implements ICEPStatement<T>
{
    
    
    private static final Logger             LOGGER   = LoggerFactory.getLogger("cepstatement");
    
    
    private final List<ICEPEventStorage<T>> storages = new ArrayList<ICEPEventStorage<T>>();
    
    
    
    /**
     * add an CEP Event storage.
     * 
     * @param _cepEventStorage
     *            the CEP event sto
     */
    public void add(final CEPEventStorage<T> _cepEventStorage) {
    
    
        storages.add(_cepEventStorage);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPStatement#getAggregateView()
     */
    @Override
    public List<T> getAggregateView() {
    
    
        final ArrayList<T> newArrayListWithCapacity = Lists.newArrayListWithCapacity(1000);
        
        for (final ICEPEventStorage<T> storage : storages) {
            newArrayListWithCapacity.addAll(storage.getCache().getAllValues());
        }
        return newArrayListWithCapacity;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPStatement#getDefaultStorage()
     */
    @Override
    public List<T> getDefaultStorage() {
    
    
        if (storages.isEmpty()) {
            LOGGER.warn("CEPStatement without filter and storage");
            return Collections.emptyList();
            
        }
        return Collections.unmodifiableList(new ArrayList<T>(storages.get(0).getCache()
                .getAllValues()));
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPStatement#getFilter(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public ICEPEventStorage<T> getEventStorage(final String _filtername) {
    
    
        for (final ICEPEventStorage<?> eventStorage : storages) {
            if (_filtername.equals(eventStorage.getFilterName())) { return (ICEPEventStorage<T>) eventStorage; }
        }
        return null;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPStatement#getFilters()
     */
    @Override
    public List<ICEPEventStorage<T>> getEventStorages() {
    
    
        return Collections.unmodifiableList(storages);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPStatement#notifyEvent(org.komea.product.database.alert.Event)
     */
    @Override
    public void notifyEvent(final T _event) {
    
    
        Validate.notNull(_event);
        
        /**
         * Notify the event storages
         */
        LOGGER.debug("Transmitting event to the storages ");
        for (final ICEPEventStorage<? extends Serializable> storage : storages) {
            LOGGER.debug("Transmit to {} ", storage.getFilterName());
            storage.notifyEvent(_event);
        }
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    
    
        return "CEPStatement [storages=" + storages + "]";
    }
}
