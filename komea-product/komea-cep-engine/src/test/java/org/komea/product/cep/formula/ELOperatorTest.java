/**
 * 
 */

package org.komea.product.cep.formula;



import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class ELOperatorTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.ELOperator#in(java.lang.Object, java.lang.Object[])}.
     */
    @Test
    public final void testIn() throws Exception {
    
    
        assertTrue(ELOperator.in("truc", "truc", "truc2"));
        assertFalse(ELOperator.in("truc3", "truc", "truc2"));
        
    }
    
}
