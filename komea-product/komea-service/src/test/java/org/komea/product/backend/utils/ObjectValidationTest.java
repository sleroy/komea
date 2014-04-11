
package org.komea.product.backend.utils;



import org.junit.Test;
import org.komea.product.backend.exceptions.KomeaConstraintViolationException;
import org.komea.product.database.model.Person;



public class ObjectValidationTest
{
    
    
    @Test @Ignore(expected = KomeaConstraintViolationException.class)
    public final void testValidateObject() throws Exception {
    
    
        final ObjectValidation objectValidation = new ObjectValidation();
        objectValidation.validateObject(new String());
        objectValidation.validateObject(new Person());
        
    }
    
}
