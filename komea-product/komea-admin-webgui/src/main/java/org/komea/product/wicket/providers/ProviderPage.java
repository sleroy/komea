// $codepro.audit.disable

package org.komea.product.wicket.providers;



import java.util.List;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.service.kpi.IKPIService;
import org.komea.product.backend.api.IWicketAdminService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Person;
import org.komea.product.database.model.PersonGroup;
import org.komea.product.database.model.Provider;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.CountColumn;
import org.komea.product.wicket.widget.builders.DataTableBuilder;
import org.komea.product.wicket.widget.model.ListDataModel;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class ProviderPage extends LayoutPage
{
    
    
    @SpringBean
    private IProviderService    providerService;
    
    @SpringBean
    private IWicketAdminService wicketAdminService;
    
    @SpringBean
    private IKPIService kpiService;
    
    
    
    public ProviderPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        accordion.setActiveTab(ADMIN_INDEX);
        final ISortableDataProvider<Provider, String> dataProvider =
                new ListDataModel(providerService.selectAll());
        
                CountColumn<Provider> countUsers = new CountColumn<Provider>() {

            @Override
            public Integer getNumberdisplay(Provider type) {
//                type.getProviderType()
                List<Kpi> personsOfPersonGroup = kpiService.getKpisOfProviderType(type.getProviderType());
                int result = 0;
                if (personsOfPersonGroup != null) {
                    result = personsOfPersonGroup.size();
                }
                return Integer.valueOf(result);
            }
        };
        
        add(DataTableBuilder
                .<Provider, String> newTable("table")
                .addColumn("Type of plugin", "providerType")
                .addColumn("Plugin name", "name")
                .addColumn(countUsers.build("Kpis associate"))
                .addColumn(
                        new ProviderTableActionPanelColumn(Model.of("Actions"), providerService,
                                wicketAdminService)).displayRows(10).withData(dataProvider).build());
    }
    
       @Override
    public String getTitle() {
        return getString("administration.title.plugins");
    }
}
