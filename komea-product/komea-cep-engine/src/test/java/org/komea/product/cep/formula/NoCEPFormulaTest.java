/**
 * 
 */

package org.komea.product.cep.formula;



import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.cep.api.ICEPResult;
import org.komea.product.cep.api.ICEPStatement;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class NoCEPFormulaTest
{
    
    
    @SuppressWarnings("unchecked")
    @Test @Ignore
    public void testFormula() {
    
    
        final NoCEPFormula noCEPFormula = new NoCEPFormula();
        final ICEPResult compute =
                noCEPFormula.compute(Mockito.mock(ICEPStatement.class), Collections.EMPTY_MAP);
        final Object asType = compute.asType();
        Assert.assertNotNull(asType);
    }
}
