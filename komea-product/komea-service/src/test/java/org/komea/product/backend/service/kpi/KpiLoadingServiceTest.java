/**
 * 
 */

package org.komea.product.backend.service.kpi;



import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IKPIService;
import org.komea.product.backend.api.IKpiLoadingService;
import org.komea.product.backend.api.IKpiQueryService;
import org.komea.product.database.model.Kpi;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class KpiLoadingServiceTest
{
    
    
    @InjectMocks
    private IKpiLoadingService        kpiLoadingService;
    
    
    @Mock
    private IKpiQueryService kpiRegisterService;
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
