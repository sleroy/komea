/**
 * 
 */

package org.komea.product.wicket.dialogs;



import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.komea.product.database.model.PersonGroup;



/**
 * This is a renderer for multiple choice only display the team name.
 * 
 * @author sleroy
 */
public final class TeamEntityChoiceRenderer implements IChoiceRenderer<PersonGroup>
{
    
    
    /**
      * 
      */
    private static final long serialVersionUID = 4085367589942342089L;
    
    
    
    @Override
    public Object getDisplayValue(final PersonGroup _object) {
    
    
        return _object.getName();
    }
    
    
    @Override
    public String getIdValue(final PersonGroup _object, final int _index) {
    
    
        return _object.getId().toString();
    }
}
