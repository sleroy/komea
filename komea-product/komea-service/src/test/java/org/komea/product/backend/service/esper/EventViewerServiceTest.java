/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.api.IEventEngineService;
import org.komea.product.backend.service.ISettingService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class EventViewerServiceTest
{
    
    
    @Mock
    private IEventEngineService esperService;
    
    
    @InjectMocks
    private EventViewerService  eventViewerService;
    @Mock
    private ISettingService     settingService;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.esper.EventViewerService#buildRetentionQuery(org.komea.product.database.enums.Severity, java.lang.String)}
     * .
     */
    @Test
    public final void testBuildRetentionQuery() throws Exception {
    
    
        // TODO
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventViewerService#getEsperService()}.
     */
    @Test
    public final void testGetEsperService() throws Exception {
    
    
        // TODO
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventViewerService#getGlobalActivity()}.
     */
    @Test
    public final void testGetGlobalActivity() throws Exception {
    
    
        // TODO
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventViewerService#getInstantView(java.lang.String)}.
     */
    @Test
    public final void testGetInstantView() throws Exception {
    
    
        // TODO
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventViewerService#getSettingService()}.
     */
    @Test
    public final void testGetSettingService() throws Exception {
    
    
        // TODO
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventViewerService#initialize()}.
     */
    @Test
    public final void testInitialize() throws Exception {
    
    
        // TODO
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.esper.EventViewerService#notifyPropertyChanged(org.komea.product.database.model.Setting)}.
     */
    @Test
    public final void testNotifyPropertyChanged() throws Exception {
    
    
        // TODO
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.esper.EventViewerService#setEsperService(org.komea.product.backend.api.IEventEngineService)}
     * .
     */
    @Test
    public final void testSetEsperService() throws Exception {
    
    
        // TODO
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.esper.EventViewerService#setSettingService(org.komea.product.backend.service.ISettingService)}
     * .
     */
    @Test
    public final void testSetSettingService() throws Exception {
    
    
        // TODO
        
    }
}
