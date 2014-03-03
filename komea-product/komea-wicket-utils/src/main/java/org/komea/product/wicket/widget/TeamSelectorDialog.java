/**
 * 
 */

package org.komea.product.wicket.widget;



import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.wicket.widget.builders.DataTableBuilder;

import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;



/**
 * @author sleroy
 */
public class TeamSelectorDialog extends AbstractDialog<Integer>
{
    
    
    /**
     * 
     */
    private static final long       serialVersionUID = 1L;
    
    private final List<PersonGroup> selected         = new ArrayList<PersonGroup>();
    
    private final List<PersonGroup> teams;
    
    
    
    /**
     * @param id
     * @param _selectedRoles
     */
    public TeamSelectorDialog(final List<PersonGroup> _teams, final String id) {
    
    
        super(id, "Select teams");
        teams = _teams;
    }
    
    
    @Override
    public boolean isResizable() {
    
    
        return true;
    }
    
    
    /*
     * (non-Javadoc)
     * @see com.googlecode.wicket.jquery.ui.widget.dialog.IDialogListener#onClose(org.apache.wicket.ajax.AjaxRequestTarget,
     * com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton)
     */
    @Override
    public void onClose(final AjaxRequestTarget _target, final DialogButton _button) {
    
    
        // TODO Auto-generated method stub
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.apache.wicket.Component#onInitialize()
     */
    @Override
    protected void onInitialize() {
    
    
        super.onInitialize();
        final DataTable build =
                DataTableBuilder.<PersonGroup, String> newTable("teamselector")
                        .addColumn("name", "name").addColumn("description", "description")
                        .displayRows(5).withCaption("Select teams").withListData(teams)
                        .withHeaders().build();
        add(build);
    }
}
