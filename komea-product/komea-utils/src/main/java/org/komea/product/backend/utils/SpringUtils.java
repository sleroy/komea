
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
    
    
    public SpringUtils() {
    
    
        super();
    }
    
}
