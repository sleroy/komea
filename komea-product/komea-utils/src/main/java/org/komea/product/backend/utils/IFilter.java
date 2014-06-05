/**
 * 
 */

package org.komea.product.backend.utils;



/**
 * Defines a filter to matches objects.
 * 
 * @author sleroy
 * @param <t>
 *            TYPE OF object to be filtered.
 */
public interface IFilter<T>
{
    
    
    /**
     * Defines the matcher.
     * 
     * @param _task
     */
    boolean matches(T _task);
}
