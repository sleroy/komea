// $codepro.audit.disable
/**
 * 
 */

package org.komea.product.wicket.providers;



import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.komea.product.backend.api.IWicketAdminService;
import org.komea.product.database.model.Provider;
import org.komea.product.wicket.widget.StaticImage;



/**
 * @author sleroy
 */
public class ProviderPanel extends Panel
{
    
    
    /**
     * @param _id
     * @param _model
     */
    public ProviderPanel(
            final String _id,
            final IModel<Provider> _model,
            final IWicketAdminService _wicketAdminService) {
    
    
        super(_id, new CompoundPropertyModel<Provider>(_model));
        final Provider providerBean = _model.getObject();
        add(new Label("providerName", providerBean.getName()));
        add(new Label("providerType", providerBean.getProviderType()));
        add(new StaticImage("icon", Model.of(providerBean.getIconURL())));
        final LinkExtension linkExtension = new LinkExtension("url", providerBean);
        linkExtension.setEnabled(providerBean.isValidURL());
        add(linkExtension);
        final Class<? extends WebPage> pluginPageClass =
                _wicketAdminService.getPluginPage(providerBean.getName());
        System.out.println("Plugin page " + pluginPageClass);
        final AdminLinkExtension adminLinkExtension =
                new AdminLinkExtension("adminpage", pluginPageClass);
        adminLinkExtension.setVisible(pluginPageClass != null);
        add(adminLinkExtension);
    }
}
