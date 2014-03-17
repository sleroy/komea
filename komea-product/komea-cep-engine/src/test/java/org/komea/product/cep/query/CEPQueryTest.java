/**
 * 
 */

package org.komea.product.cep.query;



import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.komea.product.cep.api.ICEPQuery;
import org.komea.product.cep.filter.NoEventFilter;
import org.komea.product.cep.formula.CountFormula;

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
    
    
        final ICEPQuery cepQuery =
                CEPQueryBuilder.create(new CountFormula()).defineFilter(new NoEventFilter())
                        .build();
        
        cepQuery.getFormula();
    }
    
}
