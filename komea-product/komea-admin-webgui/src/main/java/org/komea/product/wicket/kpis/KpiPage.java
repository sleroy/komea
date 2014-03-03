
package org.komea.product.wicket.kpis;



import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.database.model.Kpi;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.widget.api.IDeleteAction;
import org.komea.product.wicket.widget.api.IEditAction;
import org.komea.product.wicket.widget.builders.DataTableBuilder;



/**
 * KPI admin page
 * 
 * @author sleroy
 */
public class KpiPage extends LayoutPage
{
    
    
    @SpringBean
    private IKPIService kpiService;
    
    
    
    public KpiPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        final IDeleteAction<Kpi> personDeleteAction = new KpiDeleteAction(kpiService);
        
        final IEditAction<Kpi> kpiEditAction = new KpiEditAction(this);
        
        final ISortableDataProvider<Kpi, String> dataProvider = new KpiDataModel(kpiService);
        final DataTable<Kpi, String> build =
                DataTableBuilder.<Kpi, String> newTable("table").addColumn("Kpi key", "KpiKey")
                        .addColumn("Name", "Name").addColumn("Description", "Description")
                        .withEditDeleteColumn(personDeleteAction, kpiEditAction).displayRows(10)
                        .withData(dataProvider).build();
        add(build);
        
        // DataTableBuilder.<Person, String> newTable("table").addColumn("Login", "login")
        // .addColumn("First name", "firstName").addColumn("Last name", "lastName")
        // .addColumn("Email", "email")
        // .withEditDeleteColumn(personDeleteAction, personEditAction).displayRows(10)
        // .withData(dataProvider).build()
        // add(new Label("label", "hello word"));
        // add(new DataTable<T, S>("montableau", columns, dataProvider, rowsPerPage));
    }
    
    
    @Override
    public String getTitle() {
    
    
        return "KPI / Metrics Administration";
    }
    
}
