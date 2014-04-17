/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.backend.api.IMeasureHistoryService;
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
public class KPIValueServiceTest
{
    
    
    @Mock
    private IEntityService           entityService;
    
    
    @Mock
    private IEventEngineService      esperEngine;
    
    
    @Mock
    private KpiDao                   kpiDAO;
    
    
    @Mock
    private IKpiQueryRegisterService kpiQueryRegistry;
    
    
    @InjectMocks
    private KPIValueService          kPIValueService;
    
    
    @Mock
    private IMeasureHistoryService   measureService;
    @Mock
    private ProjectDao               projectDao;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.tester.KPIValueService#getLastMeasureOfKpi(org.komea.product.database.model.Kpi, org.komea.product.database.api.IEntity)}
     * .
     */
    @Test
    public final void testGetLastMeasureOfKpi() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.tester.KPIValueService#getRealTimeMeasure(org.komea.product.service.dto.KpiKey)}.
     */
    @Test
    public final void testGetRealTimeMeasure() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.tester.KPIValueService#getRealTimeMeasuresFromEntities(java.util.List, java.util.List)}.
     */
    @Test
    public final void testGetRealTimeMeasuresFromEntities() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.tester.KPIValueService#getSingleValue(org.komea.product.service.dto.KpiKey)}.
     */
    @Test
    public final void testGetSingleValue() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.tester.KPIValueService#storeMeasureOfAKpiInDatabase(org.komea.product.service.dto.KpiKey, java.lang.Number)}
     * .
     */
    @Test
    public final void testStoreMeasureOfAKpiInDatabase() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.cep.tester.KPIValueService#storeValueInHistory(org.komea.product.service.dto.KpiKey)}.
     */
    @Test
    public final void testStoreValueInHistory() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
