/**
 * 
 */

package org.komea.eventory.formula;



import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.komea.eventory.api.engine.ICEPStatement;
import org.komea.eventory.api.formula.ICEPResult;
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
        final ICEPResult compute =
                noCEPFormula.compute(Mockito.mock(ICEPStatement.class), Collections.EMPTY_MAP);
        final Object asType = compute.asType();
        Assert.assertNotNull(asType);
    }
}
