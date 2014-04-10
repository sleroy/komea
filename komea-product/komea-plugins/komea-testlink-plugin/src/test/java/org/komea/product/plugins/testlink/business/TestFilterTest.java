/**
 * 
 */

package org.komea.product.plugins.testlink.business;



import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.komea.product.plugins.testlink.model.TestCaseStatus;
import org.komea.product.plugins.testlink.model.TestLinkTestCase;

import static org.junit.Assert.assertEquals;



/**
 * @author sleroy
 */
public class TestFilterTest
{
    
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.business.TestFilter#getBlockedTests()}.
     */
    @Test
    public final void testGetBlockedTests() throws Exception {
    
    
        final List<TestLinkTestCase> totalTests = new ArrayList<TestLinkTestCase>();
        totalTests.add(new TestLinkTestCase(1, TestCaseStatus.BLOCKED));
        final TestFilter filter = TestFilter.filter(totalTests);
        assertEquals("Expected size 1", 1, filter.getBlockedTests().size());
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.business.TestFilter#getFailedTests()}.
     */
    @Test
    public final void testGetFailedTests() throws Exception {
    
    
        final List<TestLinkTestCase> totalTests = new ArrayList<TestLinkTestCase>();
        totalTests.add(new TestLinkTestCase(1, TestCaseStatus.FAILED));
        final TestFilter filter = TestFilter.filter(totalTests);
        assertEquals("Expected size 1", 1, filter.getFailedTests().size());
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.business.TestFilter#getSuccessfulTests()}.
     */
    @Test
    public final void testGetSuccessfulTests() throws Exception {
    
    
        final List<TestLinkTestCase> totalTests = new ArrayList<TestLinkTestCase>();
        totalTests.add(new TestLinkTestCase(1, TestCaseStatus.SUCCESSFUL));
        final TestFilter filter = TestFilter.filter(totalTests);
        assertEquals("Expected size 1", 1, filter.getSuccessfulTests().size());
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.business.TestFilter#getUnassociedTest()}.
     */
    @Test
    public final void testGetUnassociedTest() throws Exception {
    
    
        final List<TestLinkTestCase> totalTests = new ArrayList<TestLinkTestCase>();
        totalTests.add(new TestLinkTestCase(1, TestCaseStatus.UNASSOCIATED));
        final TestFilter filter = TestFilter.filter(totalTests);
        assertEquals("Expected size 1", 1, filter.getUnassociedTest().size());
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.business.TestFilter#getUnexecutedTests()}.
     */
    @Test
    public final void testGetUnexecutedTests() throws Exception {
    
    
        final List<TestLinkTestCase> totalTests = new ArrayList<TestLinkTestCase>();
        totalTests.add(new TestLinkTestCase(1, TestCaseStatus.UNEXECUTED));
        final TestFilter filter = TestFilter.filter(totalTests);
        assertEquals("Expected size 1", 1, filter.getUnexecutedTests().size());
    }
    
}
