
package org.komea.product.wicket.providers;



import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.komea.product.backend.api.IWicketAdminService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.database.model.Provider;



/**
 * Defines the column of actions for the table with providers.
 * 
 * @author sleroy
 */
public final class ProviderTableActionPanelColumn extends AbstractColumn<Provider, String>
{
    
    
    private final IProviderService    providerService;
    private final IWicketAdminService wicketAdminService;
    
    
    
    public ProviderTableActionPanelColumn(
            final IModel<String> _displayModel,
            final IProviderService _providerService,
            final IWicketAdminService _wicketAdminService) {
    
    
        super(_displayModel);
        providerService = _providerService;
        wicketAdminService = _wicketAdminService;
    }
    
    
    @Override
    public void populateItem(
            final Item<ICellPopulator<Provider>> _cellItem,
            final String _componentId,
            final IModel<Provider> _rowModel) {
    
    
        _cellItem.add(new ProviderTableActionPanel(_componentId, _rowModel, providerService,
                wicketAdminService));
        
    }
}
