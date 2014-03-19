
package org.komea.product.wicket;



import java.util.Map.Entry;
import java.util.Set;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.settings.IExceptionSettings.ThreadDumpStrategy;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.komea.product.backend.api.IWicketAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see com.mycompany.Start#main(String[])
 */
public class WicketApplicationTest extends WebApplication
{
    
    
    private static Logger          LOGGER = LoggerFactory.getLogger(WicketApplicationTest.class);
    private ApplicationContextMock ctx;
    
    
    
    /**
     * 
     */
    public WicketApplicationTest() {
    
    
        ctx = new ApplicationContextMock();
    }
    
    
    /**
     * Returns the application context.
     * 
     * @return the application context.
     */
    public ApplicationContextMock getAppCtx() {
    
    
        return ctx;
    }
    
    
    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    @Override
    public Class<? extends WebPage> getHomePage() {
    
    
        return HomePage.class;
    }
    
    
    /**
     * @see org.apache.wicket.Application#init()
     */
    @Override
    public void init() {
    
    
        super.init();
        
        // Bootstrap.install(Application.get(), new BootstrapSettings());
        
        
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));
        // don't throw exceptions for missing translations
        getResourceSettings().setThrowExceptionOnMissingResource(false);
        
        // enable ajax debug etc.
        getDebugSettings().setDevelopmentUtilitiesEnabled(true);
        // getDebugSettings().setComponentUseCheck(false);
        // make markup friendly as in deployment-mode
        
        getMarkupSettings().setStripWicketTags(true);
        
        
        // page mounts / SEO
        mountPage("/home", HomePage.class);
        LOGGER.info("#############################################################");
        final Set<Entry<String, Class<? extends WebPage>>> entrySet =
                getPluginAdminPage().getRegisteredPages().entrySet();
        LOGGER.debug("Wicket startup : {} pages", entrySet.size());
        for (final java.util.Map.Entry<String, Class<? extends WebPage>> entry : entrySet) {
            LOGGER.info("> mounting {}->{}", entry.getKey(), entry.getValue().getName());
            mountPage(entry.getKey(), entry.getValue());
        }
        LOGGER.info("#############################################################");
        
        
        getExceptionSettings().setThreadDumpStrategy(ThreadDumpStrategy.ALL_THREADS);
        getExceptionSettings()
                .setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_EXCEPTION_PAGE);
    }
    
    
    public void setApplicationContext(final ApplicationContextMock _context) {
    
    
        ctx = _context;
    }
    
    
    /**
     * Returns the plugin admin page.
     * 
     * @return the plugin admin page.
     */
    private IWicketAdminService getPluginAdminPage() {
    
    
        return getAppCtx().getBean(IWicketAdminService.class);
    }
    
    
}
