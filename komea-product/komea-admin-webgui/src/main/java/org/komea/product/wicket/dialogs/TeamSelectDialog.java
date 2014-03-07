
package org.komea.product.wicket.dialogs;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.model.Model;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.widget.builders.MultipleSelectBuilder;

import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;



/**
 * Select a team dialog.
 * 
 * @author sleroy
 */
public abstract class TeamSelectDialog extends AbstractDialog<Integer>
{
    
    
    private static final long            serialVersionUID = 1L;
    
    private final ArrayList<PersonGroup> choices          = new ArrayList<PersonGroup>();
    
    
    protected final DialogButton         btnCancel        = new DialogButton(LBL_CANCEL);
    
    protected final DialogButton         btnSure          = new DialogButton(LBL_OK);    // with a customized text
                                                                                          
                                                                                          
    
    public TeamSelectDialog(final String id, final String title, final List<PersonGroup> _teams) {
    
    
        super(id, title, new Model<Integer>(1), true); // we do not use a model here
        
        
        add(MultipleSelectBuilder.buildDropdown("teams", choices, _teams,
                new TeamEntityChoiceRenderer()));
        
    }
    
    
    /**
     * Returns the selected team
     * 
     * @return the selected team.
     */
    public PersonGroup getSelectedTeam() {
    
    
        return CollectionUtil.singleOrNull(choices);
    }
    
    
    @Override
    public boolean isResizable() {
    
    
        return true;
    }
    
    
    @Override
    protected List<DialogButton> getButtons() {
    
    
        return Arrays.asList(btnSure, btnCancel);
    }
}
