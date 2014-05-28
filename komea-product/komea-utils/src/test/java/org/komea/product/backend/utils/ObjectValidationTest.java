
package org.komea.product.backend.utils;



import javax.validation.constraints.NotNull;

import org.junit.Test;
import org.komea.product.backend.api.exceptions.KomeaConstraintViolationException;



public class ObjectValidationTest
{
    
    
    public class ObjectA
    {
        
        
        @NotNull
        private String a;
    }
    
    
    
    @Test(expected = KomeaConstraintViolationException.class)
    public final void testValidateObject() throws Exception {
    
    
        final ObjectValidation objectValidation = new ObjectValidation();
        objectValidation.validateObject(new String());
        objectValidation.validateObject(new ObjectA());
        
        
    }
}
