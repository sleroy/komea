/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.MeasureDao;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;



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
    
    
        assertTrue("Not yet implemented.", false);
    }
    
}
