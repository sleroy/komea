/**
 * 
 */

package org.komea.product.backend.service.demodata;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.test.EsperQueryTester;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Project;
import org.komea.product.service.dto.KPIValueTable;
import org.komea.product.service.dto.KpiKey;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class KpiDemoServiceTest extends AbstractSpringIntegrationTestCase
{
    
    
    private static final JenkinsEventFactory jenkinsEventFactory = new JenkinsEventFactory();
    
    private static final EsperQueryTester    newTest             = EsperQueryTester
                                                                         .newTest("BUILDER");
    @Autowired
    private IEsperEngine                     engine;
    
    
    @Autowired
    private KpiDemoService                   kpiDemoService;
    
    
    @Autowired
    private IKPIService                      kpiService;
    
    
    
    @Before
    public void setupBefore() {
    
    
        engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildComplete("SYSTEM", 1.0,
                "dev")));
        engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildComplete("SYSTEM", 1.0,
                "dev")));
        engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildComplete("SCERTIFY",
                1.0, "dev")));
        engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildFailed("SCERTIFY", 1.0,
                "dev")));
        engine.sendEvent(newTest.convertDto(jenkinsEventFactory.sendBuildFailed("SCERTIFY", 1.0,
                "dev")));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#numberBuildPerDay()}.
     * 
     * @throws KPINotFoundException
     */
    @Test
    public final void testNumberBuildPerDay() throws KPINotFoundException {
    
    
        final Kpi kpi = kpiDemoService.numberBuildPerDay();
        final KPIValueTable<Project> valueTable =
                kpiService.getKpiRealTimeValues(KpiKey.ofKpi(kpi));
        valueTable.dump();
        kpiService.storeValueInHistory(KpiKey.ofKpi(kpi));
        
        Assert.assertEquals(2, valueTable.getNumberOfRecords());
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#numberBuildPerMonth()}.
     * 
     * @throws KPINotFoundException
     */
    @Test
    public final void testNumberBuildPerMonth() throws KPINotFoundException {
    
    
        final Kpi kpi = kpiDemoService.numberBuildPerMonth();
        final KPIValueTable<Project> valueTable =
                kpiService.getKpiRealTimeValues(KpiKey.ofKpi(kpi));
        valueTable.dump();
        kpiService.storeValueInHistory(KpiKey.ofKpi(kpi));
        Assert.assertEquals(2, valueTable.getNumberOfRecords());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#numberSuccessBuildPerDay()}.
     * 
     * @throws KPINotFoundException
     */
    @Test
    public final void testNumberSuccessBuildPerDay() throws KPINotFoundException {
    
    
        final Kpi kpi = kpiDemoService.numberSuccessBuildPerDay();
        final KPIValueTable<Project> valueTable =
                kpiService.getKpiRealTimeValues(KpiKey.ofKpi(kpi));
        valueTable.dump();
        kpiService.storeValueInHistory(KpiKey.ofKpi(kpi));
        Assert.assertEquals(2, valueTable.getNumberOfRecords());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.demodata.KpiDemoService#numberSuccessBuildPerWeek()}.
     * 
     * @throws KPINotFoundException
     */
    @Test
    public final void testNumberSuccessBuildPerWeek() throws KPINotFoundException {
    
    
        final Kpi kpi = kpiDemoService.numberSuccessBuildPerWeek();
        final KPIValueTable<Project> valueTable =
                kpiService.getKpiRealTimeValues(KpiKey.ofKpi(kpi));
        valueTable.dump();
        kpiService.storeValueInHistory(KpiKey.ofKpi(kpi));
        Assert.assertEquals(2, valueTable.getNumberOfRecords());
    }
    
}
