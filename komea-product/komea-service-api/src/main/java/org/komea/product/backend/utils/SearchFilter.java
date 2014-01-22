
package org.komea.product.backend.utils;



public interface SearchFilter<T>
{
    
    
    boolean match(T _object);
}
