/**
 * 
 */

package org.komea.product.backend.service.esper;



import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class ConvertELIntoQueryTest
{
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.ConvertELIntoQuery#isValidFormula(java.lang.String)}.
     */
    @Test
    public final void testIsValidFormula() throws Exception {
    
    
        assertFalse(ConvertELIntoQuery.isValidFormula("new gni()"));
        assertTrue(ConvertELIntoQuery
                .isValidFormula("new org.komea.product.cep.query.CEPQueryImplementation()"));
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.ConvertELIntoQuery#parseEL(java.lang.String)}.
     */
    @Test
    public final void testParseEL() throws Exception {
    
    
        assertNotNull(ConvertELIntoQuery
                .parseEL("new org.komea.product.cep.query.CEPQueryImplementation()"));
        
    }
    
}
