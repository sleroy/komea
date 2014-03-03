/**
 * 
 */

package org.komea.product.backend.api;



import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.komea.product.backend.admin.plugins.IPluginAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;



/**
 * This class registers all admin pages for plugins.
 * 
 * @author sleroy
 */
public class PluginAdminService implements IPluginAdminService
{
    
    
    private static final Logger                         LOGGER      =
                                                                            LoggerFactory
                                                                                    .getLogger(PluginAdminService.class);
    
    
    private final Map<String, Class<? extends WebPage>> pluginPages =
                                                                            new HashMap<String, Class<? extends WebPage>>();
    
    
    
    /**
     * 
     */
    public PluginAdminService() {
    
    
        super();
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.backend.admin.plugins.IPluginAdminService#initializePage(java.lang.String, org.apache.wicket.request.mapper.parameter.PageParameters)
     */
    @Override
    public WebPage initializePage(final String _pluginPage, final PageParameters _pageParameters) {
    
    
        LOGGER.trace("Initializing page {} with parameters {}", _pluginPage, _pageParameters);
        try {
            final Class<? extends WebPage> requiredPage = pluginPages.get(_pluginPage);
            if (requiredPage == null) { return null; }
            return BeanUtils.instantiateClass(
            
            requiredPage.getConstructor(PageParameters.class), _pageParameters);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
        
    }
    
    
    /* (non-Javadoc)
     * @see org.komea.product.backend.admin.plugins.IPluginAdminService#register(java.lang.String, java.lang.Class)
     */
    @Override
    public void register(final String _providerName, final Class<? extends WebPage> _webPageClass) {
    
    
        pluginPages.put(_providerName, _webPageClass);
    }
    
}
