/**
 * 
 */

package org.komea.product.backend.api;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;



/**
 * Defines a component spring that references the web pages.
 * 
 * @author sleroy
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = {
    ElementType.TYPE })
public @interface PluginAdminPages {
    
    
    /**
     * List of plugin pages to mount
     * 
     * @return the list of plugin pages.
     */
    PluginMountPage[] value();
    
}
