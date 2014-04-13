/**
 * 
 */

package org.komea.eventory.utils;



import org.komea.eventory.api.errors.CouldNotCreateBeanException;



/**
 * @author sleroy
 */
public class ClassUtils
{
    
    
    /**
     * Instantiate a class
     * 
     * @param _class
     *            the class
     * @return the created pojo
     */
    public static <T> T instantiate(final Class<T> _class) {
    
    
        try {
            return _class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CouldNotCreateBeanException(_class, e);
            
        }
        
    }
}
