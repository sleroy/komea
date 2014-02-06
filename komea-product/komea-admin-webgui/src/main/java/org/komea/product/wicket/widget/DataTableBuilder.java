
package org.komea.product.wicket.widget;



import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.Model;



public class DataTableBuilder<T, S>
{
    
    
    public static <T, S> DataTableBuilder<T, S> newTable(final String _id) {
    
    
        return new DataTableBuilder<T, S>(_id);
        
    }
    
    
    
    private String                    id          = "";
    private final List<IColumn<T, S>> columns     = new ArrayList<IColumn<T, S>>(20);
    private IDataProvider<T>          dataProvider;
    private long                      rowsPerPage = 5;
    
    
    
    private DataTableBuilder(final String _id) {
    
    
        super();
        id = _id;
    }
    
    
    public DataTableBuilder<T, S> addColumn(final String _columName, final String _property) {
    
    
        final PropertyColumn<T, S> propertyColumn =
                new PropertyColumn<T, S>(new Model<String>(_columName), _property);
        columns.add(propertyColumn);
        return this;
    }
    
    
    public DataTableBuilder<T, S> addColumn(
            final String _columName,
            final String _property,
            final S _sortProperty) {
    
    
        columns.add(new PropertyColumn<T, S>(new Model<String>(_columName), _sortProperty,
                _property));
        return this;
    }
    
    
    public DataTableBuilder<T, S> addColumn(
            final String _columName,
            final String _property,
            final String _cssClass) {
    
    
        columns.add(new PropertyColumn<T, S>(new Model<String>(_columName), _property)
        {
            
            
            @Override
            public String getCssClass() {
            
            
                return _cssClass;
                
            }
        });
        return this;
    }
    
    
    public DataTableBuilder<T, S> addColumn(
            final String _columName,
            final String _property,
            final String _cssClass,
            final S _sortProperty) {
    
    
        columns.add(new PropertyColumn<T, S>(new Model<String>(_columName), _sortProperty,
                _property)
        {
            
            
            @Override
            public String getCssClass() {
            
            
                return _cssClass;
                
            }
        });
        return this;
    }
    
    
    public DataTable<T, S> build() {
    
    
        return new DataTable<T, S>(id, columns, dataProvider, rowsPerPage);
    }
    
    
    public DataTableBuilder<T, S> displayRows(final long _numberRows) {
    
    
        rowsPerPage = _numberRows;
        return this;
    }
    
    
    public DataTableBuilder<T, S> withData(final IDataProvider<T> _dataProvider) {
    
    
        dataProvider = _dataProvider;
        return this;
    }
    
}
