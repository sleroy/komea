/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class KpiLoadingServiceTest
{
    
    
    @Mock
    private IKpiQueryRegisterService kpiRegisterService;
    
    
    @Mock
    private IKPIService              kpiService;
    @InjectMocks
    private KpiLoadingService        kpiLoadingService;
    
    
    
    /**
     * Test method for {@link org.komea.product.cep.tester.KpiLoadingService#initLoadingService()}.
     */
    @Test
    public final void testInitLoadingService() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
