package org.komea.product.wicket;

<<<<<<< HEAD:komea-product/komea-wicket-utils/src/main/java/org/komea/product/wicket/WicketApplication.java


import java.util.Map.Entry;
import java.util.Set;

=======
>>>>>>> add department and teamé:komea-product/komea-admin-webgui/src/main/java/org/komea/product/wicket/WicketApplication.java
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.settings.IExceptionSettings.ThreadDumpStrategy;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
<<<<<<< HEAD:komea-product/komea-wicket-utils/src/main/java/org/komea/product/wicket/WicketApplication.java
import org.komea.product.backend.api.IPluginAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
=======
import org.komea.product.wicket.console.ConsolePage;
import org.komea.product.wicket.cronpage.CronPage;
import org.komea.product.wicket.events.EventsPage;
import org.komea.product.wicket.kpis.KpiEditPage;
import org.komea.product.wicket.kpis.KpiPage;
import org.komea.product.wicket.person.PersonAddPage;
import org.komea.product.wicket.person.PersonPage;
import org.komea.product.wicket.persongroup.department.DepartmentEditPage;
import org.komea.product.wicket.persongroup.department.DepartmentPage;
import org.komea.product.wicket.persongroup.team.TeamEditPage;
import org.komea.product.wicket.persongroup.team.TeamPage;
import org.komea.product.wicket.project.ProjectEditPage;
import org.komea.product.wicket.project.ProjectPage;
import org.komea.product.wicket.providers.ProviderPage;
import org.komea.product.wicket.settings.SettingsPage;
import org.komea.product.wicket.statistics.StatPage;
>>>>>>> add department and teamé:komea-product/komea-admin-webgui/src/main/java/org/komea/product/wicket/WicketApplication.java

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 * @see com.mycompany.Start#main(String[])
 */
<<<<<<< HEAD:komea-product/komea-wicket-utils/src/main/java/org/komea/product/wicket/WicketApplication.java
public class WicketApplication extends WebApplication
{
    
    
    private static Logger LOGGER = LoggerFactory.getLogger(WicketApplication.class);
    
    
    
    /**
     * Returns the application context.
     * 
     * @return the application context.
     */
    public ApplicationContext getAppCtx() {
    
    
        return WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    }
    
    
=======
public class WicketApplication extends WebApplication {

>>>>>>> add department and teamé:komea-product/komea-admin-webgui/src/main/java/org/komea/product/wicket/WicketApplication.java
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

        // best place to do this is in Application#init()
        // Bootstrap.install(Application.get(), new BootstrapSettings());
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        // don't throw exceptions for missing translations
        getResourceSettings().setThrowExceptionOnMissingResource(false);

        // enable ajax debug etc.
        getDebugSettings().setDevelopmentUtilitiesEnabled(true);
        // getDebugSettings().setComponentUseCheck(false);
        // make markup friendly as in deployment-mode
        
        
        getMarkupSettings().setStripWicketTags(true);
        // page mounts / SEO
        mountPage("/teams", TeamPage.class);
        mountPage("/teamedit", TeamEditPage.class);
        mountPage("/departments", DepartmentPage.class);
        mountPage("/departmentedit", DepartmentEditPage.class);
        mountPage("/home", HomePage.class);
<<<<<<< HEAD:komea-product/komea-wicket-utils/src/main/java/org/komea/product/wicket/WicketApplication.java
        LOGGER.info("#############################################################");
        final Set<Entry<String, Class<? extends WebPage>>> entrySet =
                getPluginAdminPage().getRegisteredPages().entrySet();
        LOGGER.info("Wicket startup : {} pages", entrySet.size());
        for (final java.util.Map.Entry<String, Class<? extends WebPage>> entry : entrySet) {
            LOGGER.info("> mounting {}->{}", entry.getKey(), entry.getValue().getName());
            mountPage(entry.getKey(), entry.getValue());
        }
        LOGGER.info("#############################################################");
        
        
=======
        mountPage("/kpiedit", KpiEditPage.class);
        mountPage("/kpis", KpiPage.class);
        mountPage("/projectedit", ProjectEditPage.class);
        mountPage("/projects", ProjectPage.class);
        mountPage("/settings", SettingsPage.class);
        mountPage("/viewlog", ConsolePage.class);
        mountPage("/stats", StatPage.class);
        mountPage("/login", LoginPage.class);
        mountPage("/cronpage", CronPage.class);
        mountPage("/logout", LoginPage.class);
        mountPage("/accessdenied", UnauthorizedPage.class);
        mountPage("/users", PersonPage.class);
        mountPage("/useradd", PersonAddPage.class);
        mountPage("/events", EventsPage.class);
        mountPage("/plugins", ProviderPage.class);

>>>>>>> add department and teamé:komea-product/komea-admin-webgui/src/main/java/org/komea/product/wicket/WicketApplication.java
        getExceptionSettings().setThreadDumpStrategy(ThreadDumpStrategy.ALL_THREADS);
        getExceptionSettings()
                .setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_EXCEPTION_PAGE);
    }
    
    
    /**
     * Returns the plugin admin page.
     * 
     * @return the plugin admin page.
     */
    private IPluginAdminService getPluginAdminPage() {
    
    
        return getAppCtx().getBean(IPluginAdminService.class);
    }
    
    
}
