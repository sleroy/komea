/**
 * 
 */

package org.komea.product.backend.api;



import org.springframework.context.ApplicationContext;



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
    
    
    ApplicationContext getApplicationContext();
    
    
    /**
     * @return a bean registered inside spring.
     */
    <T> T getBean(Class<T> _bean);
    
}
