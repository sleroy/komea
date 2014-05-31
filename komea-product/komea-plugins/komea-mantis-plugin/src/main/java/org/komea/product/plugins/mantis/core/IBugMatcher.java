/**
 * 
 */

package org.komea.product.plugins.mantis.core;



/**
 * @author sleroy
 */
public interface IBugMatcher<T>
{
    
    
    boolean matches(T _item);
}
