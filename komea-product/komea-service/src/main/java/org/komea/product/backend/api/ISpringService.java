/**
 * 
 */

package org.komea.product.backend.api;



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
     * Returns a bean by its name.
     * 
     * @param _dynamicSource
     *            the dynamic source.
     */
    public <T> T getBean(String _dynamicSource);
    
    
    /**
     * @return a bean registered inside spring.
     */
    <T> T getBean(Class<T> _bean);
    
}
