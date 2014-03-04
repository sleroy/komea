/**
 * 
 */

package org.komea.product.backend.api;



import java.util.Map;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;



/**
 * @author sleroy
 */
public interface IWicketAdminService
{
    
    
    /**
     * @return
     */
    Map<String, Class<? extends WebPage>> getPluginPages();
    
    
    /**
     * Return the registered pages.
     * 
     * @return
     */
    Map<String, Class<? extends WebPage>> getRegisteredPages();
    
    
    /**
     * Initializes a page
     * 
     * @param _pluginPage
     *            the plugin name
     * @param _pageParameters
     *            the page parameters
     * @return the web page.
     */
    WebPage initializePage(String _pluginPage, PageParameters _pageParameters);
    
    
    /**
     * Register the admin page
     * 
     * @param _providerName
     *            the provider name
     * @param _webPageClass
     *            the web page class.
     */
    void register(String _providerName, Class<? extends WebPage> _webPageClass);
    
    
    /**
     * Plugin name
     * 
     * @param _pluginName
     *            the plugin name
     * @param _webPageClass
     *            the web page
     */
    void registerPlugin(String _pluginName, Class<? extends WebPage> _webPageClass);
    
}
