/**
 * 
 */

package org.komea.eventory.query;



import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.komea.eventory.CEPEngineTest;
import org.komea.eventory.api.engine.ICEPQuery;
import org.komea.eventory.filter.NoEventFilter;
import org.komea.eventory.formula.CountFormula;

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
     * Test method for {@link org.komea.eventory.query.CEPQuery#CEPQuery(org.komea.eventory.query.CEPStatement)}.
     */
    @BenchmarkOptions(benchmarkRounds = 200, warmupRounds = 30)
    @Test
    public final void testCEPQuery() throws Exception {
    
    
        final ICEPQuery cepQuery =
                CEPQueryBuilder.create(new CountFormula(), CEPEngineTest.buildFakeCache())
                        .defineFilter(new NoEventFilter()).build();
        
        cepQuery.getFormula();
    }
    
}
