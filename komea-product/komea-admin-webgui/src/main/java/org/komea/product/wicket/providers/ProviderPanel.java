// $codepro.audit.disable
/**
 * 
 */

package org.komea.product.wicket.providers;



import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.pages.RedirectPage;
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
    
    
    private static final class AdminLinkExtension extends Link<String>
    {
        
        
        private final Class<? extends WebPage> pluginPageClass;
        
        
        
        /**
         * @param _id
         * @param _pluginPageClass
         */
        private AdminLinkExtension(final String _id, final Class<? extends WebPage> _pluginPageClass) {
        
        
            super(_id);
            pluginPageClass = _pluginPageClass;
        }
        
        
        @Override
        public void onClick() {
        
        
            if (pluginPageClass != null) {
                setResponsePage(pluginPageClass);
            }
        }
    }
    
    
    
    private static final class LinkExtension extends Link<String>
    {
        
        
        /**
         * 
         */
        private static final long serialVersionUID = 2062465165809427929L;
        /**
         * 
         */
        private final Provider    providerBean;
        
        
        
        /**
         * @param _id
         * @param _providerBean
         */
        private LinkExtension(final String _id, final Provider _providerBean) {
        
        
            super(_id);
            providerBean = _providerBean;
        }
        
        
        @Override
        public void onClick() {
        
        
            if (providerBean.isAbsoluteURL()) {
                setResponsePage(new RedirectPage(providerBean.getUrl()));
            }
            
        }
    }
    
    
    
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
        add(new LinkExtension("url", providerBean));
        final Class<? extends WebPage> pluginPageClass =
                _wicketAdminService.getPluginPages().get(providerBean.getName());
        System.out.println("Plugin page " + pluginPageClass);
        final AdminLinkExtension adminLinkExtension =
                new AdminLinkExtension("adminpage", pluginPageClass);
        adminLinkExtension.setVisible(pluginPageClass != null);
        add(adminLinkExtension);
    }
}
