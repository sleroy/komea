
package org.komea.product.backend.plugin.api;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.komea.product.database.enums.ProviderType;
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
    ElementType.TYPE })
public @interface ProviderPlugin {
    
    EventTypeDef[] eventTypes();
    
    /**
     * Defines the path to obtain the icon of the plugin
     * 
     * @return the icon
     */
    String icon();
    
    /**
     * Defines the plugin's name
     * 
     * @return the plugin's name
     */
    String name();
    
    Property[] properties();
    
    /**
     * Define the type of provider (SONAR? JENKINS...)
     * 
     * @return the provider type
     */
    ProviderType type();
    
    /**
     * Defines the URL to access to the server
     * 
     * @return the URL to access to the server.
     */
    String url() default "";
    
}
