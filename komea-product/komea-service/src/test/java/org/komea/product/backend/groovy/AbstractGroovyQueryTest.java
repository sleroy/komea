/**
 *
 */

package org.komea.product.backend.groovy;



import org.junit.Test;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.eventory.api.engine.IQuery;
import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.api.IMeasureStorageService;
import org.komea.product.backend.service.kpi.GroovyScriptLoader;
import org.komea.product.backend.service.kpi.IEntityKpiFormula;
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
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
@Transactional
public class AbstractGroovyQueryTest extends AbstractSpringIntegrationTestCase
{
    
    
    public class AlwaysReturn5 extends AbstractGroovyQuery implements IEntityKpiFormula,
            IDynamicDataQuery<KpiResult>
    {
        
        
        public AlwaysReturn5() {
        
        
            super();
        }
        
        
        @Override
        public Number evaluate(final EntityKey _entityKey) {
        
        
            return 5d;
        }
        
        
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
        
        
            return forEachEntity(EntityType.PERSON, this);
        }
    }
    
    
    
    @Autowired
    private IGroovyEngineService   groovyEngineService;
    
    @Autowired
    private IKPIService            kpiService;
    
    @Autowired
    private IMeasureStorageService measureStorageService;
    
    @Autowired
    private IStatisticsAPI         statisticsAPI;
    
    
    
    @Test
    public void testGroovyScript() {
    
    
        final Kpi fakeKpi = fakeKpi("DEMO_KPI_GROOVY1");
        kpiService.saveOrUpdate(fakeKpi);
        final Kpi fakeKpi2 = fakeKpi("DEMO_KPI_GROOVY2");
        kpiService.saveOrUpdate(fakeKpi2);
        measureStorageService.storeActualValueInHistory(fakeKpi.getId(), BackupDelay.DAY);
        measureStorageService.storeActualValueInHistory(fakeKpi2.getId(), BackupDelay.DAY);
        final GroovyScriptLoader groovyScriptLoader =
                new GroovyScriptLoader("scripts/groovyScript.groovy");
        
        final Kpi kpi = new Kpi();
        kpi.setEntityType(EntityType.PERSON);
        kpi.setEsperRequest(groovyScriptLoader.load());
        final IQuery<KpiResult> parseQuery = groovyEngineService.parseQuery(kpi);
        final KpiResult result = parseQuery.getResult();
        assertTrue(result.size() >= 1);
    }
    
    
    /**
     * Fake kpi
     *
     * @param _string
     * @return
     */
    private Kpi fakeKpi(final String _string) {
    
    
        return KpiBuilder.create().nameAndKey(_string).description("").forMembers()
                .providerType(ProviderType.QUALITY).groupFormula(GroupFormula.SUM_VALUE)
                .dynamicQuery(AlwaysReturn5.class)
                .interval(0.0d, 10.0d, ValueDirection.BETTER, ValueType.INT).build();
        
    }
}
