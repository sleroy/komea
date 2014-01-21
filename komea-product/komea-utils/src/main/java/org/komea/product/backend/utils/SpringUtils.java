
package org.komea.product.backend.utils;



import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;



public class SpringUtils
{
    
    
    public static <A extends Annotation> List<A> findAnnotations(
            final ApplicationContext _context,
            final Class<A> _annotation) {
    
    
        final List<A> listofAnnotations = new ArrayList<A>();
        for (final String bean : _context.getBeanDefinitionNames()) {
            final A annot = _context.findAnnotationOnBean(bean, _annotation);
            if (annot != null) {
                listofAnnotations.add(annot);
            }
        }
        return listofAnnotations;
    }
    
    
    /**
     * This method performs th ecreation of an object of the type with the given value.
     * 
     * @param _typeName
     *            the type name
     * @param _value
     *            the value
     * @return the new object
     */
    public static <T> T reifySetting(final String _typeName, final String _value) {
    
    
        try {
            return (T) Thread.currentThread().getContextClassLoader().loadClass(_typeName)
                    .getConstructor(String.class).newInstance(_value);
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    
    public SpringUtils() {
    
    
        super();
    }
    
}
