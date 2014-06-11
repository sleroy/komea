/**
 *
 */

package org.komea.product.backend.service.dataplugin;



import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.komea.eventory.api.cache.ICacheConfiguration;
import org.komea.eventory.api.cache.ICacheStorage;
import org.komea.eventory.api.cache.ICacheStorageFactory;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.database.utils.Validate;
import org.komea.product.plugins.model.IDynamicDataSource;
import org.komea.product.plugins.model.IDynamicDataSourceSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;



/**
 * This class defines the pool that handles data sources
 *
 * @author sleroy
 */
@Service
public class DynamicDataSourcePoolService implements IDynamicDataSourcePool
{


    private static final Logger                              LOGGER              =
            LoggerFactory
            .getLogger("datapool");

    @Autowired
    private ICacheStorageFactory                             cacheStorageFactory;

    private final Map<String, IDynamicDataSourceSession>     dataSessions        =
            Maps.newConcurrentMap();

    private final Multimap<Class, IDynamicDataSourceSession> dataTypeSessions    =
            ArrayListMultimap
            .create();

    private boolean                                          lazyInitDataTypeMap = false;


    @Autowired
    private ISpringService                                   springService;



    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.dataplugin.IDynamicDataSourcePool#getDataSource(java.lang.String)
     */
    @Override
    public <T extends IDynamicDataSourceSession> T getDataSource(final String _dataSourceName) {


        LOGGER.info("Requesting data source {}", _dataSourceName);
        final IDynamicDataSourceSession dataSourceSession = dataSessions.get(_dataSourceName);
        if (dataSourceSession != null) {
            LOGGER.info("Retrieving data source from cache {}", dataSourceSession);
            return (T) dataSourceSession;
        }
        LOGGER.info("Instantiating a new data source session");
        final IDynamicDataSource bean = springService.getBean(_dataSourceName);
        if (bean == null) {
            LOGGER.info("No data source with name {} has been found", _dataSourceName);
            return null;
        }
        LOGGER.info("Registering the new data source", _dataSourceName);
        final IDynamicDataSourceSession dynamicDataSourceSession =
                register(_dataSourceName, bean,
                        CacheConfigurationBuilder.expirationTimeCache(10, TimeUnit.MINUTES));
        dataSessions.put(_dataSourceName, dynamicDataSourceSession);
        return (T) dynamicDataSourceSession;
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.dataplugin.IDynamicDataSourcePool#getDataSourceData(java.lang.String)
     */
    @Override
    public <T extends Serializable> T getDataSourceData(final String _dataSourceName) {


        final IDynamicDataSourceSession<T> dataSource = getDataSource(_dataSourceName);
        return dataSource.fetchData();
    }


    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.dataplugin.IDynamicDataSourcePool#getDataSourceOfType(java.lang.Class)
     */
    @Override
    public synchronized <T extends Serializable> Collection<T> getDataSourceOfType(
            final Class<T> _class) {


        if (!lazyInitDataTypeMap) {
            for (final String dynSource : springService.getApplicationContext()
                    .getBeanNamesForType(IDynamicDataSource.class, true, true)) {
                final IDynamicDataSourceSession dataSource = getDataSource(dynSource);

                dataTypeSessions.put(dataSource.getDefinition(), dataSource);
            }
            lazyInitDataTypeMap = true;
        }
        final List<T> dataValues = Lists.newArrayList();
        for (final IDynamicDataSourceSession<T> dataSourceSession : dataTypeSessions.get(_class)) {
            dataValues.add(dataSourceSession.fetchData());
        }
        return dataValues;

    }


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
