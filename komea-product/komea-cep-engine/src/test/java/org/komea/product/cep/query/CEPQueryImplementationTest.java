/**
 * 
 */

package org.komea.product.cep.query;



import org.junit.Test;



/**
 * @author sleroy
 */
public class CEPQueryImplementationTest
{
    
    
    @Test 
    public void testGetterAndSetter() {
    
    
        final BasicUnitTests basicUnitTests = new BasicUnitTests(CEPQueryImplementation.class);
        basicUnitTests.testGetterSetter();
        basicUnitTests.testToString();
        
    }
}
