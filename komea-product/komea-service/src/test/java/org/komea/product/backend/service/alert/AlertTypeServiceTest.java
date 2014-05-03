/**
 * 
 */

package org.komea.product.backend.service.alert;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.dao.KpiAlertTypeDao;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class AlertTypeServiceTest
{
    
    
    @InjectMocks
    private AlertTypeService alertTypeService;
    
    
    @Mock
    private IKPIService      kpiService;
    @Mock
    private KpiAlertTypeDao  requiredDAO;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.alert.AlertTypeService#createKeyCriteria(java.lang.String)}.
     */
    @Test
    public final void testCreateKeyCriteria() throws Exception {
    
    
        // TODO DBUnit test DBUnit
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.alert.AlertTypeService#getAlertTypes(org.komea.product.database.enums.EntityType)}.
     */
    @Test
    public final void testGetAlertTypesEntityType() throws Exception {
    
    
        // TODO DBUnit test
        
    }
    
}
