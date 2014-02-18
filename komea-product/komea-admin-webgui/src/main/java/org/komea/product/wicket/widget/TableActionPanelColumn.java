
package org.komea.product.wicket.widget;



import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;



public final class TableActionPanelColumn<T, S> extends AbstractColumn<T, S>
{
    
    
    private final IEditAction<T>   editAction;
    private final IDeleteAction<T> deleteAction;
    
    
    
    public TableActionPanelColumn(
            final IModel<String> _displayModel,
            final IEditAction<T> _editAction,
            final IDeleteAction<T> _deleteAction) {
    
    
        super(_displayModel);
        editAction = _editAction;
        deleteAction = _deleteAction;
    }
    
    
    @Override
    public void populateItem(
            final Item<ICellPopulator<T>> _cellItem,
            final String _componentId,
            final IModel<T> _rowModel) {
    
    
        _cellItem.add(new TableActionPanel<T>(_componentId, _rowModel, deleteAction, editAction));
        
    }
}
