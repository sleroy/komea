/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.cronpage;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 *
 * @author rgalerme
 */
public class CustomColumnExpression<T, S> extends AbstractColumn<T, S> {

    private final IEditAction<T> editAction;

    public CustomColumnExpression(IModel<String> displayModel, final IEditAction<T> _editAction) {
        super(displayModel);
        this.editAction = _editAction;
    }

    @Override
    public void populateItem(Item<ICellPopulator<T>> item, String string, IModel<T> imodel) {
        item.add(new CustomPanelExpression(string, imodel, editAction));
    }

}
