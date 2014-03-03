/**
 * 
 */

package org.komea.product.backend.admin.plugins;



import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;



/**
 * @author sleroy
 *
 */
public interface IPluginAdminService
{
    
    
    /**
     * Initializes a page
     * 
     * @param _pluginPage
     *            the plugin name
     * @param _pageParameters
     *            the page parameters
     * @return the web page.
     */
    public abstract WebPage initializePage(String _pluginPage, PageParameters _pageParameters);
    
    
    /**
     * Register the admin page
     * 
     * @param _providerName
     *            the provider name
     * @param _webPageClass
     *            the web page class.
     */
    public abstract void register(String _providerName, Class<? extends WebPage> _webPageClass);
    
}
