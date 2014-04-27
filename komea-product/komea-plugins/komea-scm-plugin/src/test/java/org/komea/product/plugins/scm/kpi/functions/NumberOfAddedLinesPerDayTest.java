/**
 * 
 */

package org.komea.product.plugins.scm.kpi.functions;



import org.junit.Test;
import org.komea.product.cep.tester.CEPQueryTester;



/**
 * @author sleroy
 */
public class NumberOfAddedLinesPerDayTest
{
    
    
    @Test
    public final void testCompute() throws Exception {
    
    
        final CEPQueryTester newTest = CEPQueryTester.newTest();
        newTest.sendCustomEvent(ScmKpiTestingData.fakeCommit());
        newTest.sendCustomEvent(ScmKpiTestingData.fakeCommit());
        newTest.withQuery(new NumberOfAddedLinesPerDay());
        newTest.expectRows(1);
        newTest.expectStorageSize(2);
        newTest.hasResults(new Object[][]
            {
                {
                        ScmKpiTestingData.AUTHOR.getEntityKey(),
                        20.0d } });
        newTest.runTest();
    }
    
}
