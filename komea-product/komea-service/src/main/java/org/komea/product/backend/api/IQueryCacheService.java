package org.komea.product.backend.api;

import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.quartz.Job;

public interface IQueryCacheService extends Job {

    IDynamicDataQuery addQueryInCache(final IDynamicDataQuery dynamicDataQuery);

}
