
package org.komea.product.backend.plugin.api;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;



/**
 * This interface defines an annotation indicates a property.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = {
    ElementType.FIELD })
public @interface Property {
    
    
    /**
     * Provides a description of the property
     * 
    
     * @return the description. */
    String description() default "";
    
    
    /**
     * Provides the property key.
     * 
    
     * @return String
     */
    String key();
    
    
    /**
     * Method type.
     * @return Class
     */
    Class type() default String.class;
    
    
    /**
     * Method value.
     * @return String
     */
    String value() default "";
    
}
