/**
 * 
 */

package org.komea.product.plugins.model;



import java.io.Serializable;



/**
 * @author sleroy
 */
public interface IDynamicData extends Serializable
{
    
    
    /**
     * Returns the custom fields
     * 
     * @return the custom fields.
     */
    IPluginDataCustomFields getCustomFields();
    
    
}
