/**
 * 
 */

package org.komea.product.cep.query;



import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;



/**
 * @author sleroy
 */
public class CEPQueryTest
{
    
    
    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();
    
    
    
    /**
     * Test method for {@link org.komea.product.cep.query.CEPQuery#CEPQuery(org.komea.product.cep.query.CEPStatement)}.
     */
    @BenchmarkOptions(benchmarkRounds = 200, warmupRounds = 30)
    @Test
    public final void testCEPQuery() throws Exception {
    
    
        final CEPQuery cepQuery = new CEPQuery(new CEPStatement());
        cepQuery.getFormula();
    }
    
}
