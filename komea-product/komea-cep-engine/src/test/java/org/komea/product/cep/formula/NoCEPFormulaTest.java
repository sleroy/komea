/**
 * 
 */

package org.komea.product.cep.formula;



import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.formula.NoCEPFormula;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class NoCEPFormulaTest
{
    
    
    @SuppressWarnings("unchecked")
    @Test
    public void testFormula() {
    
    
        final NoCEPFormula noCEPFormula = new NoCEPFormula();
        final Object asType = noCEPFormula.compute(Mockito.mock(ICEPStatement.class), Collections.EMPTY_MAP);
        Assert.assertNotNull(asType);
    }
}
