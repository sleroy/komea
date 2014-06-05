/**
 * 
 */

package org.komea.product.backend.service.dataplugin;



import java.util.Map;
import java.util.Set;

import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.product.database.utils.Validate;
import org.komea.product.plugins.model.IDynamicDataSource;
import org.komea.product.plugins.model.IDynamicDataSourceSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;



/**
 * This class defines the pool that handles data sources
 * 
 * @author sleroy
 */
@Service
public class DynamicDataSourcePoolService implements IDynamicDataSourcePool
{
    
    
    private static final Logger                          LOGGER       =
                                                                              LoggerFactory
                                                                                      .getLogger("datapool");
    
    @Autowired
    private ICacheStorageFactory                         cacheStorageFactory;
    
    
    private final Map<String, IDynamicDataSourceSession> dataSessions = Maps.newConcurrentMap();
    
    
    
    @Override
    public Set<String> getDataSources() {
    
    
        return dataSessions.keySet();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.dataplugin.IDynamicDataSourcePool#isDataInCache(java.lang.String)
     */
    @Override
    public boolean isDataInCache(final String _dataSourceName) {
    
    
        if (dataSessions.containsKey(_dataSourceName)) {
            return false;
        }
        return dataSessions.get(_dataSourceName).isDataAvailableInCache();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.dataplugin.IDynamicDataSourcePool#register(org.komea.product.plugins.model.IDynamicDataSource,
     * org.komea.eventory.api.cache.ICacheConfiguration)
     */
    @Override
    public IDynamicDataSourceSession register(
            final String _dataSourceName,
            final IDynamicDataSource _sourcePool,
            final ICacheConfiguration _cacheConfiguration) {
    
    
        Validate.isTrue(!dataSessions.containsKey(_dataSourceName));
        LOGGER.info("Registering a new data source {} inside Komea", _dataSourceName);
        final ICacheStorage cacheStorage = cacheStorageFactory.newCacheStorage(_cacheConfiguration);
        return new DynamicDataSourceSession(_dataSourceName, cacheStorage, _sourcePool);
    }
    
    
}
