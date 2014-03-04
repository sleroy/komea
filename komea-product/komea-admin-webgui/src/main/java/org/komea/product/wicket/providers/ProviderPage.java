// $codepro.audit.disable

package org.komea.product.wicket.providers;



import java.util.List;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.komea.product.backend.api.IWicketAdminService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.database.model.Provider;
import org.komea.product.database.model.ProviderCriteria;
import org.komea.product.wicket.LayoutPage;



/**
 * Person admin page
 * 
 * @author sleroy
 */
public class ProviderPage extends LayoutPage
{
    
    
    private static final class ProviderListView extends ListView<Provider>
    {
        
        
        private final IWicketAdminService wicketAdmin;
        
        
        
        private ProviderListView(
                final String _id,
                final IModel<? extends List<Provider>> _model,
                final IWicketAdminService _wicketAdmin) {
        
        
            super(_id, _model);
            wicketAdmin = _wicketAdmin;
        }
        
        
        @Override
        protected void populateItem(final ListItem<Provider> _item) {
        
        
            final IModel<Provider> model = _item.getModel();
            _item.add(new ProviderPanel("providerone", model, wicketAdmin));
        }
    }
    
    
    
    @SpringBean
    private IProviderService    providerDAO;
    
    @SpringBean
    private IWicketAdminService wicketAdminService;
    
    
    
    public ProviderPage(final PageParameters _parameters) {
    
    
        super(_parameters);
        
        final List<Provider> providers = providerDAO.selectByCriteria(new ProviderCriteria());
        add(new ProviderListView("providerlist", new CompoundPropertyModel<List<Provider>>(
                providers), wicketAdminService));
    }
}
