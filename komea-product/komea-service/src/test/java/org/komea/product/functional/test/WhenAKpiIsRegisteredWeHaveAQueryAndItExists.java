/**
 * 
 */

package org.komea.product.functional.test;



import org.junit.Test;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.api.IQueryService;
import org.komea.product.backend.service.kpi.FormulaID;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.komea.product.backend.service.kpi.KpiBuilder;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.service.dto.EntityKey;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;



/**
 * This tests validates that we can collect real time values from a kpi.
 * 
 * @author sleroy
 */
public class WhenAKpiIsRegisteredWeHaveAQueryAndItExists extends AbstractSpringIntegrationTestCase
{
    
    
    public static class DemoDynamicQuery implements IDynamicDataQuery<KpiResult>
    {
        
        
        /**
         * 
         */
        public static final double VALUE = 2.0;
        
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.api.engine.IQuery#getBackupDelay()
         */
        @Override
        public BackupDelay getBackupDelay() {
        
        
            return BackupDelay.DAY;
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.api.engine.IQuery#getResult()
         */
        @Override
        public KpiResult getResult() {
        
        
            final KpiResult kpiResult = new KpiResult();
            kpiResult.put(entityKey, VALUE);
            return kpiResult;
        }
    }
    
    
    
    private static final EntityKey entityKey = EntityKey.of(EntityType.PROJECT, 1);
    
    
    @Autowired
    private IEventEngineService    engineService;
    
    @Autowired
    private IKPIService                kpiAPI;
    
    @Autowired
    private IQueryService       kpiQueryService;
    
    @Autowired
    private IKPIService            kpiService;
    
    
    @Autowired
    private IStatisticsAPI         statisticsAPI;
    
    
    
    @Test
    public void testGetRealTimeValueFromKPI() {
    
    
        final Kpi build =
                KpiBuilder.create().nameAndKey("kpidemovalue").dailyKPI()
                        .description("example of kpi").forProject()
                        .groupFormula(GroupFormula.AVG_VALUE).interval(0d, 100d)
                        .providerType(ProviderType.BUGTRACKER).dynamicQuery(DemoDynamicQuery.class)
                        .produceValue(ValueType.INT, ValueDirection.BETTER).build();
        
        // AND I REGISTER THIS KPI
        kpiService.saveOrUpdate(build);
        // AND KPI SHOULD EXISTS
        assertTrue(kpiService.exists(build.getKpiKey()));
        // AND QUERY SHOULD HAVE BEEN REGISTERED
        assertTrue(kpiQueryService.isQueryOfKpiRegistered(build));
        // AND QUERY SHOULD EXIST FROM BACKEND
        assertTrue(engineService.existQuery(FormulaID.of(build)));
        // AND RETURNS NOTNULL
        assertTrue(engineService.getQuery(FormulaID.of(build)) != null);
        
    }
}
