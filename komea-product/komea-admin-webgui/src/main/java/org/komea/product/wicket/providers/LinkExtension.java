/**
 * 
 */
package org.komea.product.wicket.providers;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.komea.product.database.model.Provider;

final class LinkExtension extends Link<String>
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
    LinkExtension(final String _id, final Provider _providerBean) {
    
    
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