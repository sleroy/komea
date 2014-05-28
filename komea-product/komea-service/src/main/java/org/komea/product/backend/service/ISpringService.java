/**
 * 
 */

package org.komea.product.backend.service;



/**
 * @author sleroy
 */
public interface ISpringService
{
    
    
    /**
     * Performs autowring of a pojo
     * 
     * @param _pojo
     *            the pojo to autowire.
     */
    public void autowirePojo(Object _pojo);
    
    
    /**
     * @return a bean registered inside spring.
     */
    <T> T getBean(Class<T> _bean);
    
}
