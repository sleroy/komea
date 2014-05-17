package org.komea.product.backend.service.kpi;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.Validate;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.product.backend.api.IDynamicDataQueryRegisterService;
import org.komea.product.backend.api.IDynamicQueryCacheService;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.backend.service.ISpringService;
import org.komea.product.backend.service.esper.QueryInformations;
import org.komea.product.cep.api.dynamicdata.IDynamicDataQuery;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sleroy
 * 
 */
@Service
@Transactional
public class KpiQueryRegisterService implements IKpiQueryRegisterService {

	private static final Logger	             LOGGER	= LoggerFactory.getLogger("kpi-query-register");

	@Autowired
	private IDynamicDataQueryRegisterService	dynamicDataQueryRegisterService;

	@Autowired
	private IDynamicQueryCacheService	     dynamicQueryCacheService;

	@Autowired
	private IEventEngineService	             esperEngine;

	@Autowired
	private ISpringService	                 springService;

	public void registerCEPQuery(@NotNull final Kpi _kpi, final Object queryImplementation) {

		if (esperEngine.existQuery(_kpi.getEsperRequest())) {
			LOGGER.debug("KPI {} reuses the query {}.", _kpi.getEsperRequest());
			return;
		}
		LOGGER.debug("KPI {} provides an event query {}.", _kpi, queryImplementation);
		esperEngine.createOrUpdateQuery(new QueryInformations(_kpi.getEsperRequest(),
		        (ICEPQueryImplementation) queryImplementation));
	}

	public void registerDynamicQuery(@NotNull final Kpi _kpi, final Object queryImplementation) {

		if (dynamicDataQueryRegisterService.existQuery(_kpi.getEsperRequest())) {
			LOGGER.debug("KPI {} reuses the query {}.", _kpi.getEsperRequest());
			return;
		}
		LOGGER.debug("KPI {} provides a dynamic data query {}.", _kpi, queryImplementation);
		springService.autowirePojo(queryImplementation);

		final String kpiEsperKey = _kpi.getEsperRequest();
		final IDynamicDataQuery cachedQuery = dynamicQueryCacheService.addCacheOnDynamicQuery(kpiEsperKey,
		        (IDynamicDataQuery) queryImplementation);
		dynamicDataQueryRegisterService.registerQuery(kpiEsperKey, cachedQuery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.komea.product.backend.service.kpi.IKpiQueryRegisterService#registerQuery
	 * (org.komea.product.database.model.Kpi, java.lang.Object)
	 */
	@Override
	public void registerQuery(@NotNull final Kpi _kpi, final Object queryImplementation) {
		Validate.notNull(_kpi);
		Validate.notNull(queryImplementation);
		Validate.notEmpty(_kpi.getEsperRequest());

		if (queryImplementation instanceof ICEPQueryImplementation) {
			registerCEPQuery(_kpi, queryImplementation);

		} else if (queryImplementation instanceof IDynamicDataQuery) {
			registerDynamicQuery(_kpi, queryImplementation);
		} else {
			throw new IllegalArgumentException("Does not know the kind of query provided by the formula "
			        + queryImplementation.getClass().getName());
		}
	}

}
