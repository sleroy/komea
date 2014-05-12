/**
 * 
 */
package org.komea.product.wicket.utils;

import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.model.IModel;
import org.komea.product.database.api.IHasKey;

/**
 * @author sleroy
 *
 */
final class IHasKeyChoiceRenderer extends ChoiceRenderer<IHasKey>
{
    
    
    @Override
    public Object getDisplayValue(final IHasKey t) {
    
    
        return t.getDisplayName();
    }
    
    
    @Override
    public String getIdValue(final IHasKey t, final int i) {
    
    
        return String.valueOf(t.getId());
    }
    
    
    @Override
    public IHasKey getObject(
            final String _id,
            final IModel<? extends List<? extends IHasKey>> _choices) {
    
    
        return _choices.getObject().get(Integer.parseInt(_id));
    }
}