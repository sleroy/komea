
package org.komea.product.wicket.widget.builders;



import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.model.Model;



public class MultipleSelectBuilder
{
    
    
    /**
     * Builds a multiple list choice.
     */
    public static <T> ListMultipleChoice<T> buildDropdown(
            final String _id,
            final ArrayList<T> _entries,
            final List<T> _choices) {
    
    
        final ListMultipleChoice<T> choices =
                new ListMultipleChoice<T>(_id, new Model<ArrayList<T>>(_entries), _choices);
        return choices;
    }
    
    
    /**
     * Builds a multiple list choice.
     * 
     * @param id
     *            the component id
     * @param _choices
     *            the list of selected choices
     * @param _entries
     *            the list of selected entries
     * @param _renderer
     *            custom render for each entry
     */
    public static <T> ListMultipleChoice<T> buildDropdown(
            final String _id,
            final ArrayList<T> _entries,
            final List<T> _choices,
            final IChoiceRenderer<T> _choiceRenderer) {
    
    
        final ListMultipleChoice<T> choices =
                new ListMultipleChoice<T>(_id, new Model<ArrayList<T>>(_entries), _choices,
                        _choiceRenderer);
        return choices;
    }
    
}
