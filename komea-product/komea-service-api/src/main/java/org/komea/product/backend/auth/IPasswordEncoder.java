
package org.komea.product.backend.auth;



public interface IPasswordEncoder
{
    
    
    public abstract String encodePassword(String _rawPassword);
    
}
