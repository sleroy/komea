/**
 * 
 */

package org.komea.product.backend.service;



/**
 * @author sleroy
 *
 */
public interface ISpringService
{
    
    
    /**
     * Performs autowring of a pojo
     * 
     * @param _pojo
     *            the pojo to autowire.
     */
    public abstract void autowirePojo(Object _pojo);
    
}
