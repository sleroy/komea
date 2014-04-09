/**
 * 
 */
package org.komea.product.wicket.widget;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;

/**
 * Display a popup to pick a team
 * 
 * @author sleroy
 */
public class AjaxDialogButton extends AjaxLink<String>
{
    
    
    private final AbstractDialog dialog;
    
    
    
    public AjaxDialogButton(
            final String _id,
            final String _titleLink,
            final AbstractDialog _dialog) {
    
    
        super(_id, Model.of(_titleLink));
        dialog = _dialog;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.apache.wicket.ajax.markup.html.AjaxLink#onClick(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    public void onClick(final AjaxRequestTarget _target) {
    
    
        dialog.open(_target);
        
    }
    
    
}