/**
 * 
 */

package org.komea.product.backend;



import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.komea.product.backend.api.IWicketAdminService;
import org.komea.product.backend.api.MountAdminPages;
import org.komea.product.backend.api.PluginAdminPages;
import org.komea.product.backend.api.PluginMountPage;
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
public class WicketAdminService implements IWicketAdminService, ApplicationContextAware
{
    
    
    private static final Logger                         LOGGER      =
                                                                            LoggerFactory
                                                                                    .getLogger(WicketAdminService.class);
    
    private final Map<String, Class<? extends WebPage>> mountPages  =
                                                                            new HashMap<String, Class<? extends WebPage>>();
    
    private final Map<String, Class<? extends WebPage>> pluginPages =
                                                                            new HashMap<String, Class<? extends WebPage>>();
    
    
    
    /**
     * Plugin admin service.
     */
    public WicketAdminService() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.api.IWicketAdminService#getPluginPage(java.lang.String)
     */
    @Override
    public Class<? extends WebPage> getPluginPage(final String _name) {
    
    
        LOGGER.info("Request admin page for plugin  {}", _name);
        return pluginPages.get(_name);
    }
    
    
    /**
     * Returns the plugin pages.
     * 
     * @return the plugin pages.
     */
    @Override
    public Map<String, Class<? extends WebPage>> getPluginPages() {
    
    
        return pluginPages;
    }
    
    
    /**
     * Returns the registered pages.
     * 
     * @return the registered pages.
     */
    @Override
    public Map<String, Class<? extends WebPage>> getRegisteredPages() {
    
    
        return mountPages;
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
            final Class<? extends WebPage> requiredPage = mountPages.get(_mountPath);
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
    
    
        LOGGER.debug("Register a new mount {} -> {}", _mountPage, _webPageClass.getName());
        mountPages.put(_mountPage, _webPageClass);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.admin.plugins.IPluginAdminService#register(java.lang.String, java.lang.Class)
     */
    @Override
    public void registerPlugin(
            final String _pluginName,
            final Class<? extends WebPage> _webPageClass) {
    
    
        LOGGER.info("Register a new plugin {} -> {}", _pluginName, _webPageClass.getName());
        pluginPages.put(_pluginName, _webPageClass);
        register("/plugin" + _pluginName.hashCode(), _webPageClass);
    }
    
    
    @Override
    public void setApplicationContext(final ApplicationContext _applicationContext) {
    
    
        for (final MountAdminPages pages : SpringUtils.findAnnotations(_applicationContext,
                MountAdminPages.class)) {
            for (final org.komea.product.backend.api.MountPage page : pages.value()) {
                register(page.mount(), page.page());
            }
        }
        for (final PluginAdminPages pages : SpringUtils.findAnnotations(_applicationContext,
                PluginAdminPages.class)) {
            for (final PluginMountPage page : pages.value()) {
                registerPlugin(page.pluginName(), page.page());
            }
        }
    }
    
}
