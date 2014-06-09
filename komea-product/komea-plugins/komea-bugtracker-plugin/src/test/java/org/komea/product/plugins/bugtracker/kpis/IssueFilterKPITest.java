/**
 * 
 */

package org.komea.product.plugins.bugtracker.kpis;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.backend.service.entities.IEntityService;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.service.kpi.IStatisticsAPI;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class IssueFilterKPITest
{
    
    
    @Mock
    private IEntityService entityService;
    
    
    @Mock
    private IKPIService    ikpiService;
    
    
    @Mock
    private ISpringService springService;
    
    
    @Mock
    private IStatisticsAPI statisticsAPI;
    @InjectMocks
    private IssueFilterKPI issueFilterKPI;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.groovy.AbstractDynamicQuery#getResult()}.
     */
    @Test
    public final void testGetResult() throws Exception {
    
    
        throw new RuntimeException("not yet implemented");
    }
    
}
