/**
 * 
 */

package org.komea.product.wicket.person;



import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.komea.product.database.model.Person;



/**
 * First name colum with logo
 * 
 * @author sleroy
 */
public class LoginColumn extends AbstractColumn<Person, String>
{
    
    
    /**
     * Add a column
     * 
     * @param _displayModel
     */
    public LoginColumn(final IModel<String> _displayModel) {
    
    
        super(_displayModel);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator#populateItem(org.apache.wicket.markup.repeater.Item,
     * java.lang.String, org.apache.wicket.model.IModel)
     */
    @Override
    public void populateItem(
            final Item<ICellPopulator<Person>> _cellItem,
            final String _componentId,
            final IModel<Person> _rowModel) {
    
    
        _cellItem.add(new LoginColumnPanel(_componentId, _rowModel));
        
    }
    
}
