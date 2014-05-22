
package org.komea.product.backend.service.kpi;



import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.query.FilterDefinition;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.IQueryService;
import org.komea.product.backend.api.exceptions.KpiAlreadyExistingException;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.esper.IEventStatisticsService;
import org.komea.product.backend.service.esper.IEventViewerService;
import org.komea.product.cep.formula.EventCountFormula;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.Kpi;
import org.komea.product.plugins.kpi.formula.ProjectFormula;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



/**
 */
public class KPIServiceITest extends AbstractSpringIntegrationTestCase
{
    
    
    public static class DemoKPI implements ICEPQueryImplementation
    {
        
        
        /**
         *
         */
        public DemoKPI() {
        
        
            //
        }
        
        
        @Override
        public BackupDelay getBackupDelay() {
        
        
            return BackupDelay.DAY;
        }
        
        
        /*
         * (non-Javadoc)
         * @see
         * org.komea.product.cep.api.ICEPQueryImplementation#getFilterDefinitions
         * ()
         */
        @Override
        public List<IFilterDefinition> getFilterDefinitions() {
        
        
            final IFilterDefinition filterDefinition = FilterDefinition.create();
            return Collections.singletonList(filterDefinition);
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.product.cep.api.ICEPQueryImplementation#getFormula()
         */
        @Override
        public ICEPFormula getFormula() {
        
        
            return new ProjectFormula(new EventCountFormula());
        }
        
    }
    
    
    
    private static final String     TEST_QUERY = "testQuery";
    
    @Autowired
    private IEventEngineService     esperEngine;
    
    @Autowired
    private IEventPushService       eventPushService;
    
    @Autowired
    private IEventStatisticsService eventStatisticsService;
    
    @Autowired
    private IKPIService             kpiService;
    
    @Autowired
    private IQueryService        kpiValueService;
    
    
    @Autowired
    private IEventViewerService     viewerService;
    
    
    
    public KPIServiceITest() {
    
    
        super();
    }
    
    
    @Test
    public void testBug() {
    
    
        final String key = "testBugKPI";
        final Kpi kpi =
                KpiBuilder.createAscending().nameAndKeyDescription(key)
                        .entityType(EntityType.PROJECT).providerType(ProviderType.OTHER)
                        .query(DemoKPI.class).hourly().build();
        
        kpiService.saveOrUpdate(kpi);
        
        final KpiResult measure = kpiValueService.evaluateRealTimeValues(kpi.getKey());
        Assert.assertNotNull("Should be filtered and work", measure);
        
    }
    
    
    @Test(expected = KpiAlreadyExistingException.class)
    public void testBugAlreadyExistingKPI() {
    
    
        final String key = "testBugKPI3";
        final Kpi kpi =
                KpiBuilder.createAscending().nameAndKeyDescription(key)
                        .entityType(EntityType.PROJECT).providerType(ProviderType.OTHER)
                        .query(DemoKPI.class).hourly().build();
        
        kpiService.saveOrUpdate(kpi);
        final Kpi kpi2 =
                KpiBuilder.createAscending().nameAndKeyDescription(key)
                        .entityType(EntityType.PROJECT).providerType(ProviderType.OTHER)
                        .query(DemoKPI.class).hourly().build();
        
        kpiService.saveOrUpdate(kpi2);
    }
    
}
