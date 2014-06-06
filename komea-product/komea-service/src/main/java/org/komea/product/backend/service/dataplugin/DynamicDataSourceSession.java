/**
 * 
 */

package org.komea.product.backend.service.dataplugin;



import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.product.plugins.model.IDynamicDataSource;
import org.komea.product.plugins.model.IDynamicDataSourceSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * This class is basically a wrapper over a dynamic data source that provides a cache before to obtain the value.
 * 
 * @author sleroy
 */
public class DynamicDataSourceSession implements IDynamicDataSourceSession
{
    
    
    private final String             dataSourceName;
    private final Logger             LOGGER;
    private final ICacheStorage      newCacheStorage;
    
    
    private final IDynamicDataSource sourcePool;
    
    
    
    /**
     * @param _dataSourceName
     * @param _newCacheStorage
     * @param _sourcePool
     */
    public DynamicDataSourceSession(final String _dataSourceName, @SuppressWarnings("rawtypes")
    final ICacheStorage<IDynamicDataSource> _newCacheStorage, @SuppressWarnings("rawtypes")
    final IDynamicDataSource _sourcePool) {
    
    
        dataSourceName = _dataSourceName;
        newCacheStorage = _newCacheStorage;
        sourcePool = _sourcePool;
        LOGGER = LoggerFactory.getLogger("datasource-" + dataSourceName);
        Validate.notNull(dataSourceName);
        Validate.notNull(newCacheStorage);
        Validate.notNull(sourcePool);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataSource#getData()
     */
    @SuppressWarnings("unchecked")
    @Override
    public Serializable fetchData() {
    
    
        LOGGER.debug("Requesting the data from the source");
        final Collection<Serializable> allValues = newCacheStorage.getAllValues();
        if (allValues.isEmpty()) {
            LOGGER.debug("Data is not in cache, requesting it directly");
            final Serializable data = sourcePool.fetchData();
            LOGGER.debug("Obtained a data {}", data != null);
            newCacheStorage.push(data);
            Validate.isTrue(newCacheStorage.size() == 1); // One element should be present.
            return data;
        } else {
            LOGGER.debug("Returning the data from the cache ");
            return allValues.iterator().next();
        }
        
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataSourceSession#getDataSourceName()
     */
    @Override
    public String getDataSourceName() {
    
    
        return dataSourceName;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.model.IDynamicDataSourceSession#isDataAvailableInCache()
     */
    @Override
    public boolean isDataAvailableInCache() {
    
    
        return newCacheStorage.size() > 0;
    }
    
}
