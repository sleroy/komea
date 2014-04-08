
package org.komea.product.wicket;



import java.util.Map.Entry;
import java.util.Set;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.settings.IExceptionSettings.ThreadDumpStrategy;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.komea.product.backend.api.IWicketAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 * 
 * @see com.mycompany.Start#main(String[])
 */
public class WicketApplication extends AuthenticatedWebApplication
{
    
    
    private static Logger                LOGGER      = LoggerFactory
                                                             .getLogger(WicketApplication.class);
    private final ApplicationContextMock contextMock = new ApplicationContextMock();
    private boolean                      debugMode;
    
    
    
    /**
     * Builds the wicket application
     */
    public WicketApplication() {
    
    
        super();
    }
    
    
    public WicketApplication(final boolean _debugMode) {
    
    
        debugMode = _debugMode;
    }
    
    
    /**
     * Returns the application context.
     * 
     * @return the application context.
     */
    public ApplicationContext getAppCtx() {
    
    
        if (isDebugMode()) { return getContextMock(); }
        return WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }
    
    
    public ApplicationContextMock getContextMock() {
    
    
        return contextMock;
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
        
        getDebugSettings().setAjaxDebugModeEnabled(isDebugMode());
        getDebugSettings().setOutputComponentPath(isDebugMode());
        getDebugSettings().setOutputMarkupContainerClassName(isDebugMode());
        getDebugSettings().setLinePreciseReportingOnNewComponentEnabled(isDebugMode());
        getDebugSettings().setLinePreciseReportingOnAddComponentEnabled(isDebugMode());
        getDebugSettings().setDevelopmentUtilitiesEnabled(isDebugMode());
        getDebugSettings().setComponentUseCheck(isDebugMode());
        
        if (!isDebugMode()) {
            getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        } else {
            getComponentInstantiationListeners()
                    .add(new SpringComponentInjector(this, contextMock));
        }
        getResourceSettings().setThrowExceptionOnMissingResource(false);
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
    
    
    public boolean isDebugMode() {
    
    
        return debugMode;
    }
    
    
    public void setDebugMode(final boolean _debugMode) {
    
    
        debugMode = _debugMode;
    }
    
    
    /**
     * Returns the plugin admin page.
     * 
     * @return the plugin admin page.
     */
    private IWicketAdminService getPluginAdminPage() {
    
    
        return getAppCtx().getBean(IWicketAdminService.class);
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.apache.wicket.authroles.authentication.AuthenticatedWebApplication#getSignInPageClass()
     */
    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
    
    
        return LoginPage.class;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.apache.wicket.authroles.authentication.AuthenticatedWebApplication#getWebSessionClass()
     */
    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
    
    
        return SecureWicketAuthenticatedWebSession.class;
    }
    
    
}
