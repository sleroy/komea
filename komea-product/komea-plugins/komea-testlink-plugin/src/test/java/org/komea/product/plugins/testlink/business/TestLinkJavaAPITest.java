/**
 * 
 */

package org.komea.product.plugins.testlink.business;



import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.komea.product.plugins.testlink.api.TestLinkException;
import org.komea.product.plugins.testlink.model.TestCaseStatus;
import org.komea.product.plugins.testlink.model.TestLinkProject;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.mockito.Mockito;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.model.TestProject;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class TestLinkJavaAPITest
{
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.business.TestLinkJavaAPI#connexion(org.komea.product.plugins.testlink.model.TestLinkServer)}
     * .
     */
    @Test(
        expected = TestLinkException.class)
    public final void testConnexion() throws Exception {
    
    
        final TestLinkJavaAPI testLinkJavaAPI = new TestLinkJavaAPI();
        final TestLinkServer server = new TestLinkServer();
        server.setAddress("http://www.google.fr");
        server.setKey("key");
        server.setName("Nom du serveur");
        testLinkJavaAPI.connexion(server);
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.business.TestLinkJavaAPI#convertExecutionStatusIntoTestlinkStatus(org.komea.product.plugins.testlink.model.TestCaseStatus, br.eti.kinoshita.testlinkjavaapi.model.Execution)}
     * .
     */
    @Test
    public final void testConvertExecutionStatusIntoTestlinkStatus() throws Exception {
    
    
        assertEquals(TestCaseStatus.BLOCKED,
                new TestLinkJavaAPI()
                        .convertExecutionStatusIntoTestlinkStatus(ExecutionStatus.BLOCKED));
        assertEquals(TestCaseStatus.FAILED,
                new TestLinkJavaAPI()
                        .convertExecutionStatusIntoTestlinkStatus(ExecutionStatus.FAILED));
        assertEquals(TestCaseStatus.UNEXECUTED,
                new TestLinkJavaAPI()
                        .convertExecutionStatusIntoTestlinkStatus(ExecutionStatus.NOT_RUN));
        assertEquals(TestCaseStatus.SUCCESSFUL,
                new TestLinkJavaAPI()
                        .convertExecutionStatusIntoTestlinkStatus(ExecutionStatus.PASSED));
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.business.TestLinkJavaAPI#getLastExecutionResult(br.eti.kinoshita.testlinkjavaapi.model.TestPlan, br.eti.kinoshita.testlinkjavaapi.model.TestCase)}
     * .
     */
    @Test
    public final void testGetLastExecutionResult() throws Exception {
    
    
        final TestLinkJavaAPI testLinkJavaAPI = new TestLinkJavaAPI();
        final TestLinkAPI apiMock = Mockito.mock(TestLinkAPI.class);
        testLinkJavaAPI.setApi(apiMock);
        final TestCase testCase = new TestCase();
        testCase.setId(14);
        final TestPlan testPlan = new TestPlan();
        testPlan.setId(15);
        testLinkJavaAPI.getLastExecutionResult(testPlan, testCase);
        verify(apiMock, times(1)).getLastExecutionResult(testPlan.getId(), testCase.getId(),
                Integer.valueOf(1));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.testlink.business.TestLinkJavaAPI#getListProject()}.
     */
    @Test
    public final void testGetListProject() throws Exception {
    
    
        final TestLinkJavaAPI testLinkJavaAPI = new TestLinkJavaAPI();
        final TestLinkAPI apiMock = Mockito.mock(TestLinkAPI.class);
        testLinkJavaAPI.setApi(apiMock);
        final TestProject testProject = new TestProject();
        testProject.setId(12);
        when(apiMock.getProjects()).thenReturn(new TestProject[]
            { testProject });
        final List<TestLinkProject> listProject = testLinkJavaAPI.getListProject();
        assertEquals(1, listProject.size());
        assertEquals(listProject.get(0).getId(), testProject.getId());
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.business.TestLinkJavaAPI#getMetricTest(org.komea.product.plugins.testlink.model.TestLinkProject)}
     * .
     */
    @Test
    @Ignore
    public final void testGetMetricTest() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.business.TestLinkJavaAPI#getRequirements(org.komea.product.plugins.testlink.model.TestLinkProject)}
     * .
     */
    @Test
    @Ignore
    public final void testGetRequirements() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.business.TestLinkJavaAPI#getTotalTestForATestCase(java.util.List, br.eti.kinoshita.testlinkjavaapi.model.TestPlan, br.eti.kinoshita.testlinkjavaapi.model.TestCase)}
     * .
     */
    @Test
    @Ignore
    public final void testGetTotalTestForATestCase() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.business.TestLinkJavaAPI#getTotalTestForATestPlan(java.util.List, br.eti.kinoshita.testlinkjavaapi.model.TestPlan)}
     * .
     */
    @Test
    @Ignore
    public final void testGetTotalTestForATestPlan() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.business.TestLinkJavaAPI#getTotalTestForATestSuite(java.util.List, br.eti.kinoshita.testlinkjavaapi.model.TestPlan, br.eti.kinoshita.testlinkjavaapi.model.TestSuite)}
     * .
     */
    @Test
    @Ignore
    public final void testGetTotalTestForATestSuite() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.business.TestLinkJavaAPI#getTotalTests(org.komea.product.plugins.testlink.model.TestLinkProject)}
     * .
     */
    @Test
    @Ignore
    public final void testGetTotalTests() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.plugins.testlink.business.TestLinkJavaAPI#hasExecutionResult(br.eti.kinoshita.testlinkjavaapi.model.Execution)}
     * .
     */
    @Test
    @Ignore
    public final void testHasExecutionResult() throws Exception {
    
    
        // TODO
        throw new RuntimeException("not yet implemented");
    }
    
}
