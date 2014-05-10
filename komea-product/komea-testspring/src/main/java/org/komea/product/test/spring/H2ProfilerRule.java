/**
 * 
 */

package org.komea.product.test.spring;



import org.h2.util.Profiler;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author sleroy
 */
public class H2ProfilerRule implements TestRule
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(H2ProfilerRule.class);
    
    
    
    /*
     * (non-Javadoc)
     * @see org.junit.rules.TestRule#apply(org.junit.runners.model.Statement, org.junit.runner.Description)
     */
    @Override
    public Statement apply(final Statement _base, final Description _description) {
    
    
        return new Statement()
        {
            
            
            private Profiler prof;
            
            
            
            @Override
            public void evaluate() throws Throwable {
            
            
                prof = new Profiler();
                prof.startCollecting();
                _base.evaluate();
                prof.stopCollecting();
                LOGGER.info("TOP 10 Slowest queries : {}", prof.getTop(10));
            }
        };
    }
}
