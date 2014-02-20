
package org.komea.product.backend.auth;



/**
 */
public interface IPasswordEncoder
{
    
    
    /**
     * Method encodePassword.
     * @param _rawPassword String
     * @return String
     */
    public abstract String encodePassword(String _rawPassword);
    
}
