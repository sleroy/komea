/**
 * 
 */

package org.komea.product.backend.service.kpi;



import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IKpiQueryRegisterService;
import org.komea.product.database.model.Kpi;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class KpiLoadingServiceTest
{
    
    
    @InjectMocks
    private KpiLoadingService        kpiLoadingService;
    
    
    @Mock
    private IKpiQueryRegisterService kpiRegisterService;
    @Mock
    private IKPIService              kpiService;
    
    
    
    /**
     * Test method for {@link org.komea.product.cep.tester.KpiLoadingService#initLoadingService()}.
     */
    @Test
    public final void testInitLoadingService() throws Exception {
    
    
        final Kpi kpi = mock(Kpi.class);
        when(kpiService.selectAll()).thenReturn(Collections.singletonList(kpi));
        kpiLoadingService.initLoadingService();
        verify(kpiRegisterService, times(1)).createOrUpdateQueryFromKpi(kpi);
        
    }
}
