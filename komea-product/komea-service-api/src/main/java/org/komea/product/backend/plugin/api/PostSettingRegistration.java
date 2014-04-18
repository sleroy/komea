
package org.komea.product.backend.plugin.api;



/**
 * This interface defines an annotation to mark a method to be launched once the settings are registered.
 * 
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public interface PostSettingRegistration
{
    
    
    /**
     * After settings are initialized
     */
    public void afterSettingInitialisation();
}
