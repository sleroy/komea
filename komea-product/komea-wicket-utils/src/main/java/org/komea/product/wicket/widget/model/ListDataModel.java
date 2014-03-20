
package org.komea.product.wicket.widget.model;



import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.wicket.utils.IteratorUtil;



public final class ListDataModel<T extends Serializable> extends SortableDataProvider<T, String>
{
    
    
    private final List<T> modelList;
    
    
    
    public ListDataModel(final List<T> _modelList) {
    
    
        modelList = _modelList;
    }
    
    
    @Override
    public Iterator<? extends T> iterator(final long _first, final long _count) {
    
    
        return IteratorUtil.buildIterator(modelList, _first, _count);
    }
    
    
    @Override
    public IModel<T> model(final T _object) {
    
    
        return Model.of(_object);
    }
    
    
    @Override
    public long size() {
    
    
        return modelList.size();
    }
}
