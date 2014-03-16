/**
 * 
 */

package org.komea.product.cep.formula;



import org.junit.Assert;
import org.junit.Test;



/**
 * @author sleroy
 */
public class ElFormulaTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.ElFormula#getFormula()}.
     */
    @Test
    public final void testGetFormula() throws Exception {
    
    
        final ElFormula<Integer> elFormula = new ElFormula<Integer>("2+4+5", Integer.class);
        Assert.assertEquals(11, elFormula.getValue(null).intValue());
    }
    
    
    /**
     * Test method for {@link org.komea.product.cep.formula.ElFormula#getValue(java.lang.Object)}.
     */
    @Test
    public final void testGetValueObject() throws Exception {
    
    
        final String formula = "2+4+5";
        final ElFormula<Integer> elFormula = new ElFormula<Integer>(formula, Integer.class);
        Assert.assertEquals(formula, elFormula.getFormula());
    }
    
}
