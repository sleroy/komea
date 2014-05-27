/**
 * 
 */

package org.komea.product.backend.groovy;



import groovy.lang.Binding;
import groovy.lang.Script;

import org.apache.commons.lang3.Validate;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.backend.service.ISpringService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public abstract class GroovyFormulaScript extends Script
{
    
    
    private static final Logger                                    LOGGER =
                                                                                  LoggerFactory
                                                                                          .getLogger(GroovyFormulaScript.class);
    private final IKPIService                                      ikpiService;
    private final org.komea.product.backend.service.ISpringService springService;
    private final IStatisticsAPI                                   statisticsAPI;
    
    
    
    protected GroovyFormulaScript(final Binding binding) {
    
    
        super(binding);
        springService = (ISpringService) getBinding().getVariable("spring");
        statisticsAPI = getSpringService().getBean(IStatisticsAPI.class);
        ikpiService = getSpringService().getBean(IKPIService.class);
        Validate.notNull(springService, "Spring service must be provided to the groovy shell.");
    }
    
    
    /**
     * AUtowiring the cep event query.
     * 
     * @param _query
     *            the query.
     * @return
     */
    
    public ICEPQuery autowired(final ICEPQuery _query) {
    
    
        LOGGER.info("Autowiring the cep query {}", _query);
        getSpringService().autowirePojo(_query);
        return _query;
        
    }
    
    
    /**
     * Autowiring the data query.
     * 
     * @param _query
     *            the query
     * @return the dynamic data query
     */
    public IDynamicDataQuery<?> autowired(final IDynamicDataQuery _query) {
    
    
        LOGGER.info("Autowiring the dynamic data query {}", _query);
        getSpringService().autowirePojo(_query);
        return _query;
        
    }
    
    
    public ISpringService getSpringService() {
    
    
        return springService;
    }
    
    
    public KpiValueProxy kpi(final String _kpiName) {
    
    
        return new KpiValueProxy(ikpiService.findKPIOrFail(_kpiName), statisticsAPI);
    }
}
