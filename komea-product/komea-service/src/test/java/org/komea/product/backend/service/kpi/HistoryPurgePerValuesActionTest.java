/**
 * 
 */

package org.komea.product.backend.service.kpi;



import org.junit.runner.RunWith;
import org.komea.product.database.dao.MeasureDao;
import org.komea.product.database.model.Kpi;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class HistoryPurgePerValuesActionTest
{
    
    
    @InjectMocks
    private HistoryPurgePerValuesAction historyPurgePerValuesAction;
    
    
    @Mock
    private Kpi                         kpi;
    @Mock
    private MeasureDao                  measureDAO;
    
}
