
package org.komea.product.backend.auth;



import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.stereotype.Service;



/**
 * This service defines the way to encode passwords.
 */
@Service
public class PasswordEncoder implements IPasswordEncoder
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncoder.class);
    
    
    @Value("#{authProperties.salt}")
    private String              salt;
    
    
    
    public PasswordEncoder() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.auth.IPasswordEncoder#encodePassword(java.lang.String)
     */
    @Override
    public String encodePassword(final String _rawPassword) {
    
    
        Validate.notEmpty(salt);
        Validate.notNull(_rawPassword);
        return new LdapShaPasswordEncoder().encodePassword(_rawPassword, salt.getBytes());
    }
    
    
    /**
     * Method getSalt.
     * 
     * @return String
     */
    public String getSalt() {
    
    
        return salt;
    }
    
    
    public void init() {
    
    
        LOGGER.debug("[AUTH] Loading salt from properties");
        
    }
    
    
    /**
     * Method setSalt.
     * 
     * @param _salt
     *            String
     */
    public void setSalt(final String _salt) {
    
    
        salt = _salt;
    }
}
