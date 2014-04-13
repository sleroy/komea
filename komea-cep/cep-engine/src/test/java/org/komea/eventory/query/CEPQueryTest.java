/**
 * 
 */

package org.komea.eventory.query;



import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.komea.eventory.cache.CacheStorageFactory;
import org.komea.eventory.filter.NoEventFilter;
import org.komea.eventory.formula.CountFormula;
import org.komea.eventory.utils.PluginUtils;
import org.komea.product.cep.api.ICEPQuery;

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
    @BenchmarkOptions(
        benchmarkRounds = 200,
        warmupRounds = 30)
    @Test
    public final void testCEPQuery() throws Exception {
    
    
        PluginUtils.setCacheStorageFactory(new CacheStorageFactory());
        
        final ICEPQuery cepQuery =
                CEPQueryBuilder.create(new CountFormula()).defineFilter(new NoEventFilter())
                        .build();
        
        cepQuery.getFormula();
    }
    
}
