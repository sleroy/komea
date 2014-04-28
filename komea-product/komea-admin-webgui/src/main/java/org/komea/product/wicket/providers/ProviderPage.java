// $codepro.audit.disable

package org.komea.product.wicket.providers;



import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.api.IWicketAdminService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.database.model.Provider;
import org.komea.product.wicket.LayoutPage;
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
    
    
    
    public ProviderPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        final ISortableDataProvider<Provider, String> dataProvider =
                new ListDataModel(providerService.selectAll());
        
        
        add(DataTableBuilder
                .<Provider, String> newTable("table")
                .addColumn("Type of plugin", "providerType")
                .addColumn("Plugin name", "name")
                .addColumn("Icon", "icon")
                .addColumn(
                        new ProviderTableActionPanelColumn(Model.of("Actions"), providerService,
                                wicketAdminService)).displayRows(10).withData(dataProvider).build());
    }
    
       @Override
    public String getTitle() {
        return getString("administration.title.plugins");
    }
}
