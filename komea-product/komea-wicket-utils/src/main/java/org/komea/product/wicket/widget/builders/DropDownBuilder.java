
package org.komea.product.wicket.widget.builders;



import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.PropertyModel;



public class DropDownBuilder
{
    
    
    /**
     * Builds the dropdown
     * 
     * @param _field
     * @param _instanceToStore
     * @param _dropDownid
     * @param _propertyName
     * @param _entries
     * @return the dropdown.
     */
    public static <T> DropDownChoice<T> buildDropdown(
            final String _field,
            final Object _instanceToStore,
            final String _dropDownid,
            final String _propertyName,
            final List<T> _entries) {
    
    
        final PropertyModel<T> selectionRoleModel = new PropertyModel<T>(_instanceToStore, _field);
        
        final DropDownChoice<T> dropDownChoice =
                new DropDownChoice<T>(_dropDownid, selectionRoleModel, _entries,
                        new ChoiceRenderer<T>(_propertyName));
        return dropDownChoice;
    }
}
