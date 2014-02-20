
package org.komea.product.backend.utils;



/**
 */
public interface SearchFilter<T>
{
    
    
    /**
     * Method match.
     * @param _object T
     * @return boolean
     */
    boolean match(T _object);
}
