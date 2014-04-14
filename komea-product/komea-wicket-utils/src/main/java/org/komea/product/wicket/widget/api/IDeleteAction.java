
package org.komea.product.wicket.widget.api;



import java.io.Serializable;
import org.apache.wicket.ajax.AjaxRequestTarget;



/**
 * Delete action to program to use with actions on table.
 * 
 * @author sleroy
 */
public interface IDeleteAction<T> extends Serializable
{
    
    
    public void delete(T _object, AjaxRequestTarget _target);
}
