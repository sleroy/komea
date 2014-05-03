/**
 * 
 */

package org.komea.product.backend.service;



import org.junit.Test;
import org.komea.product.database.dao.EventTypeDao;
import org.mockito.InjectMocks;
import org.mockito.Mock;



/**
 * @author sleroy
 */
public class EventTypeServiceTest
{
    
    
    @Mock
    private EventTypeDao           eventTypeDAO;
    
    @InjectMocks
    private final EventTypeService eventTypeService = new EventTypeService();
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.EventTypeService#getEventTypes(org.komea.product.database.enums.EntityType)}
     * .
     */
    @Test
    public final void testGetEventTypes() throws Exception {
    
    
        // TODO:: DBUNIT Test
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.EventTypeService#newCriteriaSelectByKey(org.komea.product.database.model.EventType)}.
     */
    @Test
    public final void testNewCriteriaSelectByKey() throws Exception {
    
    
        // TODO:: DBUNIT Test
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.EventTypeService#registerEvent(org.komea.product.database.model.EventType)}.
     */
    @Test
    public final void testRegisterEvent() throws Exception {
    
    
        // TODO:: DBUNIT Test
    }
    
}
