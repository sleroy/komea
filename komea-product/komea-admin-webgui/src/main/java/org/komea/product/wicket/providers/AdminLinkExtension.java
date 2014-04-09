/**
 * 
 */
package org.komea.product.wicket.providers;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

final class AdminLinkExtension extends Link<String>
{
    
    
    private final Class<? extends WebPage> pluginPageClass;
    
    
    
    /**
     * @param _id
     * @param _pluginPageClass
     */
    AdminLinkExtension(final String _id, final Class<? extends WebPage> _pluginPageClass) {
    
    
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