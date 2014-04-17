/**
 * 
 */

package org.komea.product.backend.criterias;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.KpiDao;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class FindKpiTest
{
    
    
    @InjectMocks
    private FindKpi findKpi;
    
    
    @Mock
    private KpiDao  kpiDao;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.criterias.FindKpi#find()}.
     */
    @Test
    public final void testFind() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
