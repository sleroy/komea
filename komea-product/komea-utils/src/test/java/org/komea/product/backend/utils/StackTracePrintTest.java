/**
 *
 */

package org.komea.product.backend.utils;



import org.junit.Test;

import static org.junit.Assert.assertFalse;



/**
 * @author sleroy
 */
public class StackTracePrintTest
{
    
    
    /**
     * Test method for {@link org.komea.product.backend.utils.StackTracePrint#printTrace(java.lang.Throwable)}.
     */
    @Test
    public final void testPrintTrace() throws Exception {
    
    
        assertFalse(StackTracePrint.printTrace(new RuntimeException()).isEmpty());
    }
    
}
