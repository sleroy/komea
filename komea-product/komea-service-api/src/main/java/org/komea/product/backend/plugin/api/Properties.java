
package org.komea.product.backend.plugin.api;



/**
 * This interface allows spring beans to define new properties inside Komea.
 * 
 * @author sleroy
 */
public @interface Properties {
    
    
    Property[] value();
}
