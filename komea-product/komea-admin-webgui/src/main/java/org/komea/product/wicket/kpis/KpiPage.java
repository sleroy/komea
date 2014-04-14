
package org.komea.product.wicket.kpis;



import java.util.ArrayList;
import java.util.List;

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
import org.komea.product.wicket.widget.model.ListDataModel;



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
        
        
        final IEditAction<Kpi> kpiEditAction = new KpiEditAction(this);
        
        final List<Kpi> listAllKpis = kpiService.selectAll();
        final List<Kpi> listKpisResult = new ArrayList<Kpi>();
        for (final Kpi kpi : listAllKpis) {
            if (!kpi.isGlobal()) {
                listKpisResult.add(kpi);
            }
        }
        final IDeleteAction<Kpi> kpiDeleteAction =
                new KpiDeleteAction(kpiService, listKpisResult, this);
        final ISortableDataProvider<Kpi, String> dataProvider =
                new ListDataModel<Kpi>(listKpisResult);
        final DataTable<Kpi, String> build =
                DataTableBuilder.<Kpi, String> newTable("table").addColumn("Kpi key", "KpiKey")
                        .addColumn("Name", "Name").addColumn("Description", "Description")
                        .withEditDeleteColumn(kpiDeleteAction, kpiEditAction)
                        .displayRows(listKpisResult.size() + 10).withData(dataProvider).build();
        add(build);
    }
    
    
    @Override
    public String getTitle() {
    
    
        return getString("KpiPage.title");
    }
    
}
