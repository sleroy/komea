
package org.komea.product.backend.service.kpi;



import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.Validate;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.backend.service.esper.QueryInformations;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * @author sleroy
 */
@Service
@Transactional
public class KpiQueryRegisterService implements IKpiQueryRegisterService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("kpi-query-register");
    
    @Autowired
    private IEventEngineService esperEngine;
    
    @Autowired
    private ISpringService      springService;
    
    
    
    /*
     * (non-Javadoc)
     * @see
     * org.komea.product.backend.service.kpi.IKpiQueryRegisterService#registerQuery
     * (org.komea.product.database.model.Kpi, java.lang.Object)
     */
    @Override
    public void registerQuery(@NotNull
    final Kpi _kpi, final IQuery queryImplementation) {
    
    
        Validate.notNull(_kpi);
        Validate.notNull(queryImplementation);
        Validate.notEmpty(_kpi.getEsperRequest());
        
        if (esperEngine.existQuery(FormulaID.of(_kpi))) {
            LOGGER.debug("KPI {} reuses the query {}.", _kpi.getEsperRequest());
            return;
        }
        LOGGER.debug("KPI {} provides a dynamic data query {}.", _kpi, queryImplementation);
        springService.autowirePojo(queryImplementation);
        
        LOGGER.debug("KPI {} provides an event query {}.", _kpi, queryImplementation);
        esperEngine.createOrUpdateQuery(new QueryInformations(_kpi.getEsperRequest(),
                queryImplementation));
        
    }
}
