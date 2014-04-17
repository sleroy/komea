/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.util.List;

import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.database.model.Kpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * This service performs the loading of existing KPI (extracted from Database).
 * 
 * @author sleroy
 */
@Service
@Transactional
public class KpiLoadingService
{
    
    
    private static final Logger      LOGGER = LoggerFactory.getLogger("kpi-loader");
    
    
    @Autowired
    private IKpiQueryRegisterService kpiRegisterService;
    
    
    @Autowired
    private IKPIService              kpiService;
    
    
    
    /**
     * @return the kpiRegisterService
     */
    public IKpiQueryRegisterService getKpiRegisterService() {
    
    
        return kpiRegisterService;
    }
    
    
    public IKPIService getKpiService() {
    
    
        return kpiService;
    }
    
    
    /**
     * Initializes the service
     */
    @PostConstruct
    public void initLoadingService() {
    
    
        LOGGER.info("LOADING KPI FROM DATABASE");
        
        final List<Kpi> allKpis = kpiService.selectAll();
        LOGGER.info("found kpi in database  {}", allKpis.size());
        for (final Kpi existingKpi : allKpis) {
            kpiRegisterService.createOrUpdateQueryFromKpi(existingKpi);
        }
        LOGGER.info("----------------------------------------");
    }
    
    
    /**
     * @param _kpiRegisterService
     *            the kpiRegisterService to set
     */
    public void setKpiRegisterService(final IKpiQueryRegisterService _kpiRegisterService) {
    
    
        kpiRegisterService = _kpiRegisterService;
    }
    
    
    public void setKpiService(final IKPIService _kpiService) {
    
    
        kpiService = _kpiService;
    }
    
}
