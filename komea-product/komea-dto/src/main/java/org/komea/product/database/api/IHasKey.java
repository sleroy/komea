/**
 *
 */

package org.komea.product.database.api;



import java.io.Serializable;



/**
 * This interface defines an object having a key.
 * 
 * @author sleroy
 */
public interface IHasKey extends Serializable
{
    
    
    /**
     * Accept a visitor implementing the key.
     * 
     * @param _visitor
     *            the visitor.
     */
    public void accept(IKeyVisitor _visitor);
    
    
    /**
     * Returns the name to display
     * 
     * @return the name to display.
     */
    String getDisplayName();
    
    
    /**
     * Returns the key.
     * 
     * @return the key.
     */
    Integer getId();
    
    
    /**
     * Returns the key as a string
     * 
     * @return
     */
    String getKey();
}
