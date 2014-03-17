
package org.komea.product.backend.plugin.api;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;



/**
 * This interface allows spring beans to define new properties inside Komea.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = {
    ElementType.TYPE })
public @interface Properties {
    
    
    /**
     * Provides a name to the group of properties
     * 
     * @return a name.
     */
    String group() default "";
    
    
    /**
     * Method value.
     * 
     * @return Property[]
     */
    Property[] value();
}
