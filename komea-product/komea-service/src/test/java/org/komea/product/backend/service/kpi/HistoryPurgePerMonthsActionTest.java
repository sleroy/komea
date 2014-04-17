/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HistoryPurgePerMonthsActionTest
{
    
    
    @Mock
    private Kpi                         kpi;
    
    
    @Mock
    private MeasureDao                  measureDAO;
    @InjectMocks
    private HistoryPurgePerMonthsAction historyPurgePerMonthsAction;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.HistoryPurgePerMonthsAction#purgeHistory()}.
     */
    @Test
    public final void testPurgeHistory() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
