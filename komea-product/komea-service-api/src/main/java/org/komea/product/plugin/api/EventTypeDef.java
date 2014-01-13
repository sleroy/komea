
package org.komea.product.plugin.api;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.Severity;
import org.springframework.stereotype.Component;



/**
 * This interface defines a spring component defining a plugin (internal
 * provider) inside Komea. Several fields are required to describe the provider.
 * 
 * @author sleroy
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = {
        ElementType.TYPE, ElementType.FIELD })
public @interface EventTypeDef {
    
    
    /**
     * This field defines the category of alert.
     */
    String category() default "";
    
    
    /**
     * This field provides a description of the event triggered by the plugin
     */
    String description() default "";
    
    
    /**
     * This field enables /disables the alert.
     */
    boolean enabled() default true;
    
    
    /**
     * This field describes the entity associated to this alert.
     */
    EntityType entityType() default EntityType.PROJECT;
    
    
    /**
     * This field describes the key of the alert.
     */
    String key() default "";
    
    
    /**
     * This field describes the alert name;
     */
    String name() default "";
    
    
    /**
     * This field describes the priority of the alert.
     */
    Severity severity() default Severity.MINOR;
    
}
