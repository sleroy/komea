/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.utils;

import java.io.Serializable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 *
 * @author rgalerme
 */
public abstract class CustomColumn<T> implements Serializable {

    public abstract String getValueDisplay(T type);

    public IColumn<T, String> build(String _columnName) {
        PropertyColumn<T, String> countColumn;
        countColumn = new PropertyColumn<T, String>(Model.of(_columnName), "toString") {

            @Override
            public void populateItem(Item<ICellPopulator<T>> item, String componentId, IModel<T> rowModel) {
                T entity = rowModel.getObject();
                String numberdisplay = getValueDisplay(entity);
                IModel<String> nModel = new Model<String>(numberdisplay);
                PropertyModel<Object> propertyModel = new PropertyModel<Object>(nModel, this.getPropertyExpression());
                item.add(new Label(componentId, propertyModel));
            }

        };
        return countColumn;
    }

}
