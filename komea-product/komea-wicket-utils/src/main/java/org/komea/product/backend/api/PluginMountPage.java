/**
 * 
 */

package org.komea.product.backend.api;



import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.wicket.markup.html.WebPage;
import org.springframework.stereotype.Component;



/**
 * @author sleroy
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(value = {
    ElementType.TYPE })
public @interface PluginMountPage {
    
    
    /**
     * Page
     * 
     * @return the mount page.
     */
    Class<? extends WebPage> page();
    
    
    /**
     * Plugin name
     * 
     * @return the plugin name
     */
    String pluginName();
}
