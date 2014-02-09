
package org.komea.product.wicket.widget.api;



import java.io.Serializable;



/**
 * Delete action to program to use with actions on table.
 * 
 * @author sleroy
 */
public interface IEditAction<T> extends Serializable
{
    
    
    public void selected(T _object);
}
