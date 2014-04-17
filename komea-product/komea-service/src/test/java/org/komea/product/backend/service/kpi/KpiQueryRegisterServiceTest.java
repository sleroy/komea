/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.database.dao.KpiDao;
import org.komea.product.database.dao.ProjectDao;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Project;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.JobDataMap;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class KpiQueryRegisterServiceTest
{
    
    
    @Mock
    private ICronRegistryService    cronRegistry;
    
    
    @Mock
    private IEntityService          entityService;
    
    
    @Mock
    private IEventEngineService     esperEngine;
    
    
    @InjectMocks
    private KpiQueryRegisterService kpiQueryRegisterService;
    
    
    @Mock
    private ProjectDao              projectDao;
    @Mock
    private KpiDao                  requiredDAO;
    
    
    
    /**
         * Test method for
         * {@link org.komea.product.cep.tester.KpiQueryRegisterService#evaluateFormulaAndRegisterQuery(org.komea.product.database.model.Kpi)}.
         */
        @Test
        public final void testEvaluateFormulaAndRegisterQuery() throws Exception {
        
        
            // TODO
            org.junit.Assert.assertTrue("not yet implemented", false);
        }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.tester.KPIService#createOrUpdateHistoryCronJob(org.komea.product.database.model.Kpi, org.komea.product.database.api.IEntity)}
     * .
     */
    @Test
    public void testCreateOrUpdateHistoryCronJob() throws Exception {
    
    
        final Kpi kpi = new Kpi();
        kpi.setCronExpression("0 0 0 0 0");
        
        kpiQueryRegisterService.createOrUpdateHistoryCronJob(kpi, new Project());
        final ArgumentCaptor<Class> argumentCaptor = ArgumentCaptor.forClass(Class.class);
        verify(kpiQueryRegisterService.getCronRegistry(), atLeastOnce()).registerCronTask(
                Matchers.anyString(), Matchers.anyString(), argumentCaptor.capture(),
                Matchers.any(JobDataMap.class));
        kpiQueryRegisterService.createOrUpdateHistoryCronJob(kpi, new Project());
        verify(kpiQueryRegisterService.getCronRegistry(), times(2)).existCron(Matchers.anyString());
        
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.tester.KpiQueryRegisterService#getQueryValueFromKpi(org.komea.product.database.model.Kpi)}.
     */
    @Test
    public final void testGetQueryValueFromKpi() throws Exception {
    
    
        // TODO
        org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.tester.KPIService#prepareKpiHistoryJob(org.komea.product.database.model.Kpi, org.komea.product.database.api.IEntity, java.lang.String)}
     * .
     */
    @Test
    public void testPrepareKpiHistoryJob() throws Exception {
    
    
        kpiQueryRegisterService.prepareKpiHistoryJob(new Kpi(), null, "KPI_CRON_NAME");
        final ArgumentCaptor<JobDataMap> argumentCaptor = ArgumentCaptor.forClass(JobDataMap.class);
        final ArgumentCaptor<Class> classCaptor = ArgumentCaptor.forClass(Class.class);
        verify(kpiQueryRegisterService.getCronRegistry(), times(1)).registerCronTask(
                Matchers.anyString(), Matchers.anyString(), classCaptor.capture(),
                argumentCaptor.capture());
        
        
        CronInstantiatorFactory.testCronInstantiation(classCaptor.getValue(),
                argumentCaptor.getValue());
    }
    
    
    /**
         * Test method for
         * {@link org.komea.product.cep.tester.KpiQueryRegisterService#createOrUpdateQueryFromKpi(org.komea.product.database.model.Kpi)}
         * .
         */
        @Test
        public final void testCreateOrUpdateQueryFromKpi() throws Exception {
        
        
            // TODO
            org.junit.Assert.assertTrue("not yet implemented", false);
        }
    
}
