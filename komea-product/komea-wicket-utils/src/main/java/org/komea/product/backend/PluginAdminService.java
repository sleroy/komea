/**
 * 
 */

package org.komea.product.backend;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.komea.product.backend.api.IPluginAdminService;
import org.komea.product.backend.api.MountAdminPages;
import org.komea.product.backend.api.MountPage;
import org.komea.product.backend.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;



/**
 * This class registers all admin pages for plugins.
 * 
 * @author sleroy
 */
@Service
public class PluginAdminService implements IPluginAdminService, ApplicationContextAware
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
    
    
    @Override
    public Map<String, Class<? extends WebPage>> getRegisteredPages() {
    
    
        return pluginPages;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.admin.plugins.IPluginAdminService#initializePage(java.lang.String,
     * org.apache.wicket.request.mapper.parameter.PageParameters)
     */
    @Override
    public WebPage initializePage(final String _mountPath, final PageParameters _pageParameters) {
    
    
        LOGGER.trace("Initializing page {} with parameters {}", _mountPath, _pageParameters);
        try {
            final Class<? extends WebPage> requiredPage = pluginPages.get(_mountPath);
            if (requiredPage == null) { return null; }
            return BeanUtils.instantiateClass(
            
            requiredPage.getConstructor(PageParameters.class), _pageParameters);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.admin.plugins.IPluginAdminService#register(java.lang.String, java.lang.Class)
     */
    @Override
    public void register(final String _mountPage, final Class<? extends WebPage> _webPageClass) {
    
    
        pluginPages.put(_mountPage, _webPageClass);
    }
    
    
    @Override
    public void setApplicationContext(final ApplicationContext _applicationContext) {
    
    
        final List<MountAdminPages> annotations =
                SpringUtils.findAnnotations(_applicationContext, MountAdminPages.class);
        for (final MountAdminPages pages : annotations) {
            for (final org.komea.product.backend.api.MountPage page : pages.value()) {
                register(page.mount(), page.page());
            }
        }
    }
    
}
