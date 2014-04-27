/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import org.junit.Test;
import org.komea.product.cep.tester.CEPQueryTester;



/**
 * @author sleroy
 */
public class AverageCommitMessageLengthTest
{
    
    
    @Test
    public final void testCompute() throws Exception {
    
    
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        newTest.sendCustomEvent(ScmKpiTestingData.fakeCommit());
        newTest.sendCustomEvent(ScmKpiTestingData.fakeCommit());
        newTest.withQuery(new AverageCommitMessageLength());
        newTest.expectRows(1);
        newTest.expectStorageSize(2);
        newTest.hasResults(new Object[][]
            {
                {
                        ScmKpiTestingData.AUTHOR.getEntityKey(),
                        Double.valueOf(ScmKpiTestingData.fakeCommit().getMessage().length()) } });
        newTest.runTest();
    }
}
