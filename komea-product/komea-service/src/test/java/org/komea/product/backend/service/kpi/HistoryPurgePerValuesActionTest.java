/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyInt;

import static org.mockito.Mockito.verify;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class HistoryPurgePerValuesActionTest
{
    
    
    @Mock
    private MeasureDao measureDAO;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.kpi.HistoryPurgePerValuesAction#purgeHistory()}.
     */
    @Test
    public void testPurgeHistory() throws Exception {
    
    
        final Kpi kpi = new Kpi();
        final HistoryPurgePerDaysAction historyPurgePerDaysAction =
                new HistoryPurgePerDaysAction(measureDAO, kpi);
        historyPurgePerDaysAction.purgeHistory();
        verify(measureDAO).deleteByPrimaryKey(anyInt());
    }
    
}
