
package org.komea.product.backend.auth;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



public class PasswordEncoderTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private IPasswordEncoder passwordEncoder;
    
    
    
    @Test
    public final void testEncodePassword() {
    
    
        final String encodePassword = passwordEncoder.encodePassword("rawpassword");
        Assert.assertEquals("{SSHA}BtRYzBwUuMlAUmsFn/B3a3Jb94Frb21lYXNhbHQ=", encodePassword);
        Assert.assertEquals("{SSHA}u5xgqbC6jhCkpZlknRR4iLLF5kJrb21lYXNhbHQ=",
                passwordEncoder.encodePassword("admin"));
    }
}
