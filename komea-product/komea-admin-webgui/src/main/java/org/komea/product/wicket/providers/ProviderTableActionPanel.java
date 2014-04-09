
package org.komea.product.wicket.providers;



import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.komea.product.backend.api.IWicketAdminService;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.database.model.Provider;



/**
 * Panel that houses row-actions
 */
public class ProviderTableActionPanel extends Panel
{
    
    
    private final IProviderService    providerService;
    private final IWicketAdminService wicketAdminService;
    
    
    
    /**
     * @param id
     *            component id
     * @param model
     *            model for contact
     */
    public ProviderTableActionPanel(
            final String id,
            final IModel<Provider> model,
            final IProviderService _providerService,
            final IWicketAdminService _wicketAdminService) {
    
    
        super(id, model);
        providerService = _providerService;
        wicketAdminService = _wicketAdminService;
        final Class<? extends WebPage> pluginPageClass =
                _wicketAdminService.getPluginPage(model.getObject().getName());
        final AdminLinkExtension adminLinkExtension =
                new AdminLinkExtension("admin", pluginPageClass);
        adminLinkExtension.setVisible(pluginPageClass != null);
        add(adminLinkExtension);
        
        final LinkExtension linkExtension = new LinkExtension("jump", model.getObject());
        linkExtension.setVisible(model.getObject().isValidURL()
                && model.getObject().getUrl().startsWith("http://"));
        add(linkExtension);
    }
}
