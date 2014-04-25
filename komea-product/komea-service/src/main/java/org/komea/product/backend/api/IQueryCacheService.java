package org.komea.product.backend.api;

import java.util.List;
import org.komea.cep.dynamicdata.IDynamicDataQuery;

/**
 * This interface defines the service to manipulate the cache query.
 *
 * @author sleroy
 */
public interface IQueryCacheService {

    /**
     * Add a cache on a dynamic query.
     *
     * @param dynamicDataQuery the dynamic query
     * @return the dynamic queyr with the cache.
     */
    IDynamicDataQuery addCacheOnDynamicQuery(final IDynamicDataQuery dynamicDataQuery);

    /**
     * Returns the stored query names.
     *
     * @return the stored query names
     */
    List<String> getStoredQueryNames();

    void refreshAll();

}
