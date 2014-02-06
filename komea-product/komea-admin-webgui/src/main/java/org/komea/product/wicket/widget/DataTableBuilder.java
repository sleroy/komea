
package org.komea.product.wicket.widget;



import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.wicket.model.ListDataModel;



public class DataTableBuilder<T, S>
{
    
    
    private static final class DefaultTableModelWithCaption<T, S> extends
            AjaxFallbackDefaultDataTable<T, S>
    {
        
        
        private final String captionName;
        
        
        
        private DefaultTableModelWithCaption(
                final String _id,
                final List<? extends IColumn<T, S>> _columns,
                final ISortableDataProvider<T, S> _dataProvider,
                final int _rowsPerPage,
                final String _captionName) {
        
        
            super(_id, _columns, _dataProvider, _rowsPerPage);
            captionName = _captionName;
        }
        
        
        @Override
        protected IModel<String> getCaptionModel() {
        
        
            return Model.of(captionName);
        }
    }
    
    
    
    public static <T, S> DataTableBuilder<T, S> newTable(final String _id) {
    
    
        return new DataTableBuilder<T, S>(_id);
        
    }
    
    
    
    private String                      id          = "";
    private List<IColumn<T, S>>         columns     = new ArrayList<IColumn<T, S>>(20);
    private ISortableDataProvider<T, S> dataProvider;
    private int                         rowsPerPage = 5;
    private String                      caption;
    private boolean                     headers;
    
    
    
    private DataTableBuilder(final String _id) {
    
    
        super();
        id = _id;
    }
    
    
    public DataTableBuilder addColumn(final IColumn<T, S> _abstractColumn) {
    
    
        columns.add(_abstractColumn);
        return this;
    }
    
    
    public DataTableBuilder<T, S> addColumn(final String _columName, final String _property) {
    
    
        final PropertyColumn<T, S> propertyColumn =
                new PropertyColumn<T, S>(Model.of(_columName), _property);
        
        columns.add(propertyColumn);
        return this;
    }
    
    
    public DataTableBuilder<T, S> addColumn(
            final String _columName,
            final String _property,
            final S _sortProperty) {
    
    
        columns.add(new PropertyColumn<T, S>(Model.of(_columName), _sortProperty, _property));
        return this;
    }
    
    
    public DataTableBuilder<T, S> addColumn(
            final String _columName,
            final String _property,
            final String _cssClass) {
    
    
        columns.add(new PropertyColumn<T, S>(Model.of(_columName), _property)
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
    
    
        columns.add(new PropertyColumn<T, S>(Model.of(_columName), _sortProperty, _property)
        {
            
            
            @Override
            public String getCssClass() {
            
            
                return _cssClass;
                
            }
        });
        return this;
    }
    
    
    public DataTable<T, S> build() {
    
    
        DataTable<T, S> dataTable = null;
        
        final String captionName = caption;
        if (captionName != null) {
            dataTable =
                    new DefaultTableModelWithCaption<T, S>(id, columns, dataProvider, rowsPerPage,
                            captionName);
        } else {
            dataTable =
                    new AjaxFallbackDefaultDataTable<T, S>(id, columns, dataProvider, rowsPerPage);
        }
        if (headers) {
            dataTable.add(new HeadersToolbar<S>(dataTable, null));
        }
        id = null;
        this.caption = null;
        this.columns = new ArrayList<IColumn<T, S>>();
        this.dataProvider = null;
        this.headers = false;
        
        
        return dataTable;
    }
    
    
    public DataTableBuilder<T, S> displayRows(final int _numberRows) {
    
    
        rowsPerPage = _numberRows;
        return this;
    }
    
    
    public DataTableBuilder<T, S> withCaption(final String _captionName) {
    
    
        caption = _captionName;
        return this;
    }
    
    
    public DataTableBuilder<T, S> withData(final ISortableDataProvider<T, S> _dataProvider) {
    
    
        dataProvider = _dataProvider;
        return this;
    }
    
    
    public DataTableBuilder<T, S> withHeaders() {
    
    
        headers = true;
        return this;
    }
    
    
    public DataTableBuilder withListData(final List<T> _receivedAlertTypesIn24LastHours) {
    
    
        dataProvider = new ListDataModel(_receivedAlertTypesIn24LastHours);
        return this;
    }
    
    
}
