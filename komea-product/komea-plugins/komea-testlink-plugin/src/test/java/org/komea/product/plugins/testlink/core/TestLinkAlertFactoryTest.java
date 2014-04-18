/**
 * 
 */

package org.komea.product.plugins.testlink.core;


import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.backend.service.plugins.IPluginIntegrationService;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author sleroy
 */
public class TestLinkAlertFactoryTest extends AbstractSpringIntegrationTestCase {
    
    private final TestLinkAlertFactory alertFactory = new TestLinkAlertFactory();
    
    @Autowired
    private IEventPushService          eventPushService;
    
    @Autowired
    private IPluginIntegrationService  gitPlugin;
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.core.TestLinkAlertFactory#newBlockedTests(long, java.lang.String)}.
     */
    @Test
    public final void testNewBlockedTests() throws Exception {
    
        eventPushService.sendEventDto(alertFactory.newBlockedTests(1, "TRUC"));
    }
    
    @Test
    public final void testAlertFactoryMessage() throws Exception {
    
        EventSimpleDto alert = alertFactory.newBlockedTests(1, "TRUC");
        Assert.assertEquals("1 tests are unexecuted tests in testlink for project TRUC", alert.getMessage());
    }
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.core.TestLinkAlertFactory#newFailedTests(long, java.lang.String)}.
     */
    @Test
    public final void testNewFailedTests() throws Exception {
    
        eventPushService.sendEventDto(alertFactory.newFailedTests(1, "TRUC"));
    }
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.core.TestLinkAlertFactory#newRequirements(long, java.lang.String)}.
     */
    @Test
    public final void testNewRequirements() throws Exception {
    
        eventPushService.sendEventDto(alertFactory.newRequirements(1, "TRUC"));
    }
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.core.TestLinkAlertFactory#newSuccessfultest(long, java.lang.String)}.
     */
    @Test
    public final void testNewSuccessfultest() throws Exception {
    
        eventPushService.sendEventDto(alertFactory.newSuccessfultest(1, "TRUC"));
    }
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.core.TestLinkAlertFactory#newTested(long, java.lang.String)}.
     */
    @Test
    public final void testNewTested() throws Exception {
    
        eventPushService.sendEventDto(alertFactory.newTested(1, "TRUC"));
    }
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.core.TestLinkAlertFactory#newTotalTests(long, java.lang.String)}.
     */
    @Test
    public final void testNewTotalTests() throws Exception {
    
        eventPushService.sendEventDto(alertFactory.newTotalTests(1, "TRUC"));
    }
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.core.TestLinkAlertFactory#newUnassociedTest(long, java.lang.String)}.
     */
    @Test
    public final void testNewUnassociedTest() throws Exception {
    
        eventPushService.sendEventDto(alertFactory.newUnassociedTest(1, "TRUC"));
    }
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.core.TestLinkAlertFactory#newUnexecutedTest(long, java.lang.String)}.
     */
    @Test
    public final void testNewUnexecutedTest() throws Exception {
    
        eventPushService.sendEventDto(alertFactory.newUnexecutedTest(1, "TRUC"));
    }
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.core.TestLinkAlertFactory#newUntested(long, java.lang.String)}.
     */
    @Test
    public final void testNewUntested() throws Exception {
    
        eventPushService.sendEventDto(alertFactory.newUntested(1, "TRUC"));
    }
    
}
