/**
 *
 */
package org.komea.product.backend.service.kpi;

import java.util.List;

import javax.annotation.PostConstruct;

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
public class KpiLoadingService {

    private static final Logger LOGGER = LoggerFactory.getLogger("kpi-loader");

    @Autowired
    private IKPIService kpiService;

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
            kpiService.refreshEsper(existingKpi);
        }
        LOGGER.info("----------------------------------------");
    }

    public void setKpiService(final IKPIService _kpiService) {

        kpiService = _kpiService;
    }

}
