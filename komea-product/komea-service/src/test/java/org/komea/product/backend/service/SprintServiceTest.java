/**
 * 
 */

package org.komea.product.backend.service;



import org.joda.time.DateTime;
import org.junit.Test;
import org.komea.product.service.dto.Sprint;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNull;



/**
 * @author sleroy
 */
public class SprintServiceTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private SprintService sprintService;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.SprintService#getSprintOnPeriod(java.lang.String, org.joda.time.DateTime, org.joda.time.DateTime)}
     * .
     */
    @Test
    public final void testGetSprintOnPeriod() throws Exception {
    
    
        final DateTime beginTime = new DateTime().minusDays(1);
        final DateTime endTime = beginTime;
        final Sprint sprintOnPeriod = sprintService.getSprintOnPeriod("GNI", beginTime, endTime);
        assertNull(sprintOnPeriod);
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.SprintService#getSprintsAssociatedToAProject(java.lang.String)}.
     */
    @Test
    public final void testGetSprintsAssociatedToAProject() throws Exception {
    
    
        assertNull(sprintService.getSprintsAssociatedToAProject("projectABC"));
    }
    
}
