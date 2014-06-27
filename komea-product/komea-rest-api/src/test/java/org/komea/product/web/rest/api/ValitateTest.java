
package org.komea.product.web.rest.api;


import org.apache.commons.lang3.Validate;
import org.junit.Test;

public class ValitateTest {
    
    @Test(expected = IllegalArgumentException.class)
    public void testValidate_with_false() throws Exception {
    
        Validate.isTrue(false);
        
    }
}
