/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.esper.test.EsperQueryTester;
import org.komea.product.database.alert.IEvent;



/**
 * Integration tests of the component EventViewer, validation of sending/receiving alerts.
 * 
 * @author sleroy
 */
public class EventViewerServiceIT
{
    
    
    private EsperEngineBean     esperEngine;
    private EventViewerService  eventViewerService;
    private JenkinsEventFactory jenkinsEventFactory;
    
    
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    
    
        esperEngine = new EsperEngineBean();
        esperEngine.init();
        eventViewerService = new EventViewerService();
        eventViewerService.setEsperService(esperEngine);
        eventViewerService.initialize();
        jenkinsEventFactory = new JenkinsEventFactory();
        
    }
    
    
    /**
     * @throws java.lang.Exception
     */
    @After
    public void setUpAfter() throws Exception {
    
    
        esperEngine.destroy();
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventViewerService#getDayEvents()}.
     */
    @Test
    public final void testGetDayEvents() {
    
    
        final EsperQueryTester newTest = EsperQueryTester.newTest("TEST");
        newTest.sendEvent(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 0, "BRANCH_NAME"));
        newTest.sendEvent(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 0, "BRANCH_NAME"));
        newTest.sendEvent(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 0, "BRANCH_NAME"));
        for (final IEvent event : newTest.getEvents()) {
            esperEngine.sendEvent(event);
        }
        Assert.assertEquals(3, eventViewerService.getDayEvents().size());
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventViewerService#getHourEvents()}.
     */
    @Test
    public final void testGetHourEvents() {
    
    
        final EsperQueryTester newTest = EsperQueryTester.newTest("TEST");
        newTest.sendEvent(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 0, "BRANCH_NAME"));
        newTest.sendEvent(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 0, "BRANCH_NAME"));
        newTest.sendEvent(jenkinsEventFactory.sendBuildComplete("SCERTIFY", 0, "BRANCH_NAME"));
        for (final IEvent event : newTest.getEvents()) {
            esperEngine.sendEvent(event);
        }
        Assert.assertEquals(3, eventViewerService.getHourEvents().size());
    }
    
    
}
