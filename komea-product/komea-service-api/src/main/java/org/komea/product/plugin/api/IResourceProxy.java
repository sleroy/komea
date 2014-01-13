
package org.komea.product.plugin.api;



import java.io.Serializable;



/**
 * This interface defines a proxy over an provider resource.
 * 
 * @author sleroy
 */
public interface IResourceProxy<T extends Serializable>
{
    
    
    /**
     * Returns the resource.
     * 
     * @return the resource
     */
    T get();
    
    
    /**
     * Saves the modification of the resource.
     */
    void update(IUpdateAction _action);
}
