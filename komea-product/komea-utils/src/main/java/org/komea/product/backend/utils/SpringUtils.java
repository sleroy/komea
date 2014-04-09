
package org.komea.product.backend.utils;



import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;



public class SpringUtils
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringUtils.class);
    
    
    
    /**
     * Find annotation
     * 
     * @param _context
     *            the context
     * @param _beanName
     *            the bean name
     * @param _annotation
     *            the annotation
     * @return the annotation.
     */
    public static <A extends Annotation> A findAnnotation(
            final ApplicationContext _context,
            final String _beanName,
            final Class<A> _annotation) {
    
    
        A findAnnotationOnBean = null;
        try {
            findAnnotationOnBean = _context.findAnnotationOnBean(_beanName, _annotation);
        } catch (final Exception e) {
            LOGGER.debug(e.getMessage(), e);
        }
        return findAnnotationOnBean;
    }
    
    
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
    @SuppressWarnings("unchecked")
    public static <T> T reifySetting(final String _typeName, final String _value) {
    
    
        try {
            final Class referenceType =
                    Thread.currentThread().getContextClassLoader().loadClass(_typeName);
            if (referenceType.isEnum()) {
                
                return (T) Enum.valueOf(referenceType, _value);
            } else {
                return (T) referenceType.getConstructor(String.class).newInstance(_value);
            }
            
        } catch (final Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    
    public SpringUtils() {
    
    
        super();
    }
    
}
