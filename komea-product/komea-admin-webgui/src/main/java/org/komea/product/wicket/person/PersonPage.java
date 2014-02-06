
package org.komea.product.wicket.person;



import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.CSVDataExporter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.ExportToolbar;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.database.dao.PersonDao;
import org.komea.product.database.model.Person;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.model.PersonDataModel;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class PersonPage extends LayoutPage
{
    
    
    @SpringBean
    private PersonDao personDAO;
    
    
    
    public PersonPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        final List<IColumn<Person, String>> columns = new ArrayList<IColumn<Person, String>>();
        
        columns.add(new AbstractColumn<Person, String>(new Model<String>("Actions"))
        {
            
            
            @Override
            public void populateItem(
                    final Item<ICellPopulator<Person>> cellItem,
                    final String componentId,
                    final IModel<Person> model) {
            
            
                // cellItem.add(new ActionPanel(componentId, model));
            }
        });
        
        columns.add(new PropertyColumn<Person, String>(new Model<String>("Login"), "login")
        {
            
            
            @Override
            public String getCssClass() {
            
            
                return "numeric";
            }
        });
        
        columns.add(new PropertyColumn<Person, String>(new Model<String>("First Name"),
                "firstName", "firstName"));
        columns.add(new PropertyColumn<Person, String>(new Model<String>("Last Name"), "lastName",
                "lastName"));
        columns.add(new PropertyColumn<Person, String>(new Model<String>("Email"), "email", "email"));
        
        
        final DataTable<Person, String> dataTable =
                new DefaultDataTable<Person, String>("table", columns, new PersonDataModel(
                        personDAO), 8);
        dataTable.setTableBodyCss("table");
        dataTable.addBottomToolbar(new ExportToolbar(dataTable)
                .addDataExporter(new CSVDataExporter()));
        
        add(dataTable);
    }
}
