
package org.komea.product.plugins.rss.bean;



import javax.annotation.PreDestroy;

import org.komea.product.backend.api.IWicketAdminService;
import org.komea.product.backend.api.PluginAdminPages;
import org.komea.product.backend.api.PluginMountPage;
import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.PostSettingRegistration;
import org.komea.product.backend.plugin.api.Properties;
import org.komea.product.backend.plugin.api.Property;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.plugins.rss.repositories.api.IRssRepositories;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * Main class to define the RSS Plugin.
 * 
 * @author sleroy
 */
@ProviderPlugin(
    eventTypes =
        { @EventTypeDef(
            providerType = ProviderType.NEWS,
            description = "Rss provider plugin",
            enabled = true,
            entityType = EntityType.PROJECT,
            key = "rss-news",
            name = "Rss news",
            severity = Severity.MINOR) },
    icon = "rss",
    name = RssProviderPlugin.RSS_PROVIDER_PLUGIN,
    type = ProviderType.NEWS,
    url = "/rssnews")
@PluginAdminPages(@PluginMountPage(
    pluginName = RssProviderPlugin.RSS_PROVIDER_PLUGIN,
    page = RssRepositoryPage.class))
@Properties(
    group = "RSS Plugin",
    value =
        { @Property(
            description = "Defines the cron value to fetch rss feeds",
            key = RssProviderPlugin.RSS_SETTING_CRON_NAME,
            type = String.class,
            value = RssProviderPlugin.RSS_SETTING_CRON_VALUE) })
public class RssProviderPlugin implements PostSettingRegistration
{
    
    
    private static final Logger   LOGGER                 = LoggerFactory.getLogger("rss-provider");
    
    /**
     *
     */
    protected static final String RSS_CRON_JOB           = "rss_cron_job";
    
    protected static final String RSS_SETTING_CRON_NAME  = "rss_refresh_period";
    
    /**
     *
     */
    protected static final String RSS_SETTING_CRON_VALUE = "0 0/5 * * * ?";
    
    /**
     * Rss Provider plugin name;
     */
    static final String           RSS_PROVIDER_PLUGIN    = "Rss Provider plugin";
    
    @Autowired
    private ICronRegistryService  cronRegistryService;
    
    @Autowired
    private IEventPushService     esperEngine;
    
    @Autowired
    private IWicketAdminService   pluginAdminService;
    
    @Autowired
    private ISettingService       registry;
    
    @Autowired
    private IRssRepositories      rssRepository;
    
    
    
    /**
     * RSS Provider plugin.
     */
    public RssProviderPlugin() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.plugin.api.PostSettingRegistration#afterSettingInitialisation()
     */
    @Override
    public void afterSettingInitialisation() {
    
    
        LOGGER.info("Initialisation du plugin RSS");
        
        final JobDataMap properties = prepareJobMapForCron();
        
        cronRegistryService.removeCronTask(RSS_CRON_JOB);
        cronRegistryService.registerCronTask(RSS_CRON_JOB, RSS_SETTING_CRON_VALUE,
                RssCronJob.class, properties);
        cronRegistryService.updateCronFrequency(RSS_CRON_JOB,
                registry.getProxy(RSS_SETTING_CRON_NAME).getStringValue());
        
    }
    
    
    @PreDestroy
    public void destroy() {
    
    
        LOGGER.debug("Removing RSS Cron");
        cronRegistryService.removeCronTask(RSS_CRON_JOB);
    }
    
    
    /**
     * @return the cronRegistryService
     */
    public ICronRegistryService getCronRegistryService() {
    
    
        return cronRegistryService;
    }
    
    
    /**
     * @return the esperEngine
     */
    public IEventPushService getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    /**
     * @return the pluginAdminService
     */
    public IWicketAdminService getPluginAdminService() {
    
    
        return pluginAdminService;
    }
    
    
    /**
     * @return the registry
     */
    public ISettingService getRegistry() {
    
    
        return registry;
    }
    
    
    /**
     * @return the rssRepository
     */
    public IRssRepositories getRssRepository() {
    
    
        return rssRepository;
    }
    
    
    /**
     * Prepares job map for cron.
     * 
     * @return
     */
    public JobDataMap prepareJobMapForCron() {
    
    
        final JobDataMap properties = new JobDataMap();
        properties.put("lastDate", null);
        
        return properties;
    }
    
    
    /**
     * @param _cronRegistryService
     *            the cronRegistryService to set
     */
    public void setCronRegistryService(final ICronRegistryService _cronRegistryService) {
    
    
        cronRegistryService = _cronRegistryService;
    }
    
    
    /**
     * @param _esperEngine
     *            the esperEngine to set
     */
    public void setEsperEngine(final IEventPushService _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    /**
     * @param _pluginAdminService
     *            the pluginAdminService to set
     */
    public void setPluginAdminService(final IWicketAdminService _pluginAdminService) {
    
    
        pluginAdminService = _pluginAdminService;
    }
    
    
    /**
     * @param _registry
     *            the registry to set
     */
    public void setRegistry(final ISettingService _registry) {
    
    
        registry = _registry;
    }
    
    
    /**
     * @param _rssRepository
     *            the rssRepository to set
     */
    public void setRssRepository(final IRssRepositories _rssRepository) {
    
    
        rssRepository = _rssRepository;
    }
    
}
