/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.enums.EvictionType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.MeasureCriteria;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        kpi.setId(1);
        kpi.setEvictionRate(1);
        kpi.setEvictionType(EvictionType.DAYS);
        final AbstractHistoryPurgePerTimeAction historyPurgePerDaysAction =
                new HistoryPurgePerDaysAction(measureDAO, kpi);
        historyPurgePerDaysAction.purgeHistory();
        verify(measureDAO).deleteByCriteria(Matchers.any(MeasureCriteria.class));
    }
    
    
}
