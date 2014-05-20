/**
 *
 */

package org.komea.product.backend.service.kpi;



import java.util.List;

import javax.annotation.PostConstruct;

import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.IKpiLoadingService;
import org.komea.product.backend.api.IKpiQueryService;
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
public class KpiLoadingService implements IKpiLoadingService
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger("kpi-loader");
    
    @Autowired
    private IKpiQueryService    kpiRegisterService;
    
    @Autowired
    private IKPIService         kpiService;
    
    
    
    /**
     * @return the kpiRegisterService
     */
    public IKpiQueryService getKpiRegisterService() {
    
    
        return kpiRegisterService;
    }
    
    
    public IKPIService getKpiService() {
    
    
        return kpiService;
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.backend.service.kpi.IKpiLoadingService#initLoadingService()
     */
    @Override
    @PostConstruct
    public void initLoadingService() {
    
    
        LOGGER.info("LOADING KPI FROM DATABASE");
        
        final List<Kpi> allKpis = kpiService.selectAll();
        LOGGER.info("found kpi in database  {}", allKpis.size());
        for (final Kpi existingKpi : allKpis) {
            try {
                kpiRegisterService.createOrUpdateQueryFromKpi(existingKpi);
            } catch (final Exception _e) {
                LOGGER.error(_e.getMessage(), _e);
            }
        }
        LOGGER.info("----------------------------------------");
    }
    
    
    /**
     * @param _kpiRegisterService
     *            the kpiRegisterService to set
     */
    public void setKpiRegisterService(final IKpiQueryService _kpiRegisterService) {
    
    
        kpiRegisterService = _kpiRegisterService;
    }
    
    
    public void setKpiService(final IKPIService _kpiService) {
    
    
        kpiService = _kpiService;
    }
    
}
