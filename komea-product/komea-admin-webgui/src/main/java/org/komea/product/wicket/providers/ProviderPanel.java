// $codepro.audit.disable
/**
 * 
 */

package org.komea.product.wicket.providers;



import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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
    public ProviderPanel(final String _id, final IModel<Provider> _model) {
    
    
        super(_id, new CompoundPropertyModel<Provider>(_model));
        final Provider object = _model.getObject();
        add(new Label("providerName", object.getName()));
        add(new Label("providerType", object.getProviderType()));
        add(new StaticImage("icon", Model.of(object.getIconURL())));
        
        final Link<String> link = new Link<String>("url")
        {
            
            
            /**
             * 
             */
            private static final long serialVersionUID = 2062465165809427929L;
            
            
            
            @Override
            public void onClick() {
            
            
                setResponsePage(new RedirectPage(object.getUrl()));
            }
            
        };
        add(link);
        final Link<String> admlink = new Link<String>("adminpage")
        {
            
            
            @Override
            public void onClick() {
            
            
                setResponsePage(new RedirectPage(object.getUrl()));
            }
            
        };
        add(admlink);
        
        
    }
    
}
