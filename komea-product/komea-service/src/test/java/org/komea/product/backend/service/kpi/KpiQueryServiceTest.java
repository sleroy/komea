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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class KpiQueryServiceTest
{
    
    
    @Mock
    private ICronRegistryService    cronRegistry;
    
    
    @Mock
    private IEntityService          entityService;
    
    
    @Mock
    private IEventEngineService     esperEngine;
    
    
    @InjectMocks
    private KpiQueryService kpiQueryRegisterService;
    
    
    @Mock
    private ProjectDao              projectDao;
    @Mock
    private KpiDao                  requiredDAO;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.tester.KpiQueryService#createOrUpdateQueryFromKpi(org.komea.product.database.model.Kpi)} .
     */
    @Test
    public final void testCreateOrUpdateQueryFromKpi() throws Exception {
    
    
        // TODO
        // org.junit.Assert.assertTrue("not yet implemented", false);
    }
    
    
    /**
         * Test method for
         * {@link org.komea.product.cep.tester.KpiQueryService#updateFormulaOfKpi(org.komea.product.database.model.Kpi)}.
         */
        @Test
        public final void testUpdateFormulaOfKpi() throws Exception {
        
        
            // TODO
            // org.junit.Assert.assertTrue("not yet implemented", false);
        }
    
    
    /**
         * Test method for
         * {@link org.komea.product.cep.tester.KpiQueryService#evaluateRealTimeValues(org.komea.product.database.model.Kpi)}.
         */
        @Test
        public final void testEvaluateRealTimeValues() throws Exception {
        
        
            // TODO
            // org.junit.Assert.assertTrue("not yet implemented", false);
        }
    
}
