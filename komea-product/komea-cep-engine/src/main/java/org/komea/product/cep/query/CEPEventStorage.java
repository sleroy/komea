/**
 * 
 */

package org.komea.product.cep.query;



import java.io.Serializable;

import org.komea.product.cep.api.ICEPEventStorage;
import org.komea.product.cep.api.IEventFilter;
import org.komea.product.cep.api.IEventTransformer;
import org.komea.product.cep.api.IFilterDefinition;
import org.komea.product.cep.api.TransformedEvent;
import org.komea.product.cep.api.cache.ICacheConfiguration;
import org.komea.product.cep.api.cache.ICacheStorage;
import org.komea.product.cep.cache.GoogleCacheStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class defines a storage to store event previously filtered.
 * 
 * @author sleroy
 */
public class CEPEventStorage<T extends Serializable> implements ICEPEventStorage<T>
{
    
    
    private final ICacheConfiguration                cacheConfiguration;
    private final IEventFilter<T>                    eventFilter;
    private final IEventTransformer<Serializable, T> eventTransfomer;
    private final String                             filterName;
    
    
    private final Logger                             LOGGER;
    private final ICacheStorage<T>                   storage;
    
    
    
    /**
     * Builds a new CEP Event storage.
     * 
     * @param _cacheConfiguration
     *            the cache configuration
     * @param _eventFilter
     *            the event filter;
     */
    public CEPEventStorage(final IFilterDefinition _filterDefinition) {
    
    
        super();
        LOGGER = LoggerFactory.getLogger("cepevent-storage-" + _filterDefinition.getFilterName());
        filterName = _filterDefinition.getFilterName();
        cacheConfiguration = _filterDefinition.getCacheConfiguration();
        eventFilter = _filterDefinition.getEventFilter();
        storage = new GoogleCacheStorage<T>(cacheConfiguration);
        eventTransfomer = _filterDefinition.getEventTransformer();
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEventStorage#getStorage()
     */
    @Override
    public ICacheStorage<T> getCache() {
    
    
        return storage;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEventStorage#getEventFilter()
     */
    @Override
    public IEventFilter<T> getEventFilter() {
    
    
        return eventFilter;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEventStorage#getEventTransformer()
     */
    @Override
    public IEventTransformer<?, T> getEventTransformer() {
    
    
        return eventTransfomer;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEventStorage#getFilterName()
     */
    @Override
    public String getFilterName() {
    
    
        return filterName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.cep.api.ICEPEventStorage#notifyEvent(org.komea.product.database.alert.Event)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void notifyEvent(final Serializable _event) {
    
    
        if (eventFilter.isFiltered((T) _event)) {
            LOGGER.debug("[FILTER-ACCEPT] --- {}", _event);
            Serializable eventToStore = _event;
            if (eventTransfomer != null) {
                LOGGER.debug("[REQUIRES-TRANSFORMATION]] ---  {}", _event);
                final TransformedEvent<T> transform = eventTransfomer.transform(_event);
                if (transform.isValid()) {
                    LOGGER.debug("[TRANSFORMATION-PERFORMED] ] --- {}", transform);
                    eventToStore = transform.getData();
                } else {
                    LOGGER.debug("[TRANSFORMATION-IGNORED]");
                }
            }
            LOGGER.debug("[PUSH-TO-CACHE]");
            storage.push((T) eventToStore);
            
        } else {
            LOGGER.debug("[FILTER-DENIED] {} -> {}", filterName, _event);
        }
    }
}
