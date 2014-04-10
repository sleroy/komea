/**
 * 
 */

package org.komea.product.plugins.testlink.business;



import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.komea.product.plugins.testlink.model.TestLinkRequirement;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class RequirementFilterTest
{
    
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.business.RequirementFilter#RequirementFilter(java.util.List)}.
     */
    @Test
    public void testRequirementFilter() throws Exception {
    
    
        final List<TestLinkRequirement> requirements = new ArrayList<TestLinkRequirement>();
        final RequirementFilter requirementFilter = new RequirementFilter(requirements);
        assertEquals(requirements, requirementFilter.getTotalRequirements());
    }
    
}
