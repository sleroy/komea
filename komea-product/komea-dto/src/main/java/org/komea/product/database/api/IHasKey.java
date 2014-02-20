/**
 * 
 */

package org.komea.product.database.api;






/**
 * This interface defines an object having a key.
 * 
 * @author sleroy
 */
public interface IHasKey
{
    
    
    /**
     * Accept a visitor implementing the key.
     * 
     * @param _visitor
     *            the visitor.
     */
    public void accept(IKeyVisitor _visitor);
    
    
    /**
     * Returns the key.
     * 
     * @return the key.
     */
    Integer getId();
}
