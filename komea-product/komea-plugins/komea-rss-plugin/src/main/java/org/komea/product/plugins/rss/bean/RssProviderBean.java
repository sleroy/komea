
package org.komea.product.plugins.rss.bean;



import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.komea.product.backend.api.IWicketAdminService;
import org.komea.product.backend.api.MountAdminPages;
import org.komea.product.backend.api.MountPage;
import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Setting;
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
        eventTypes = {
            @EventTypeDef(
                    category = "NEWS",
                    description = "Rss provider plugin",
                    enabled = true,
                    entityType = EntityType.PROJECT,
                    key = "rss-news",
                    name = "Rss news",
                    severity = Severity.MINOR) },
        icon = "rss",
        name = RssProviderBean.RSS_PROVIDER_PLUGIN,
        type = ProviderType.NEWS,
        url = "/rssnews")
@MountAdminPages(@MountPage(mount = "rssnews", page = RssRepositoryPage.class))
public class RssProviderBean implements org.komea.product.backend.service.ISettingListener
{
    
    
    private static final Logger  LOGGER              = LoggerFactory.getLogger("rss-provider");
    
    
    /**
     * 
     */
    private static final String  RSS_CRON_JOB        = "rss_cron_job";
    
    
    /**
     * 
     */
    private static final String  RSS_CRON_VALUE      = "0 0/5 * * * ?";
    
    
    private static final String  RSS_PROVIDER_PERIOD = "rss_refresh_period";
    
    
    /**
     * Rss Provider plugin name;
     */
    static final String          RSS_PROVIDER_PLUGIN = "Rss Provider plugin";
    
    
    @Autowired
    private ICronRegistryService cronRegistryService;
    
    
    @Autowired
    private IEventPushService    esperEngine;
    
    
    @Autowired
    private IRssExampleFeedBean  feed;
    
    
    @Autowired
    private IWicketAdminService  pluginAdminService;
    
    
    @Autowired
    private ISettingService      registry;
    
    
    @Autowired
    private IRssRepositories     rssRepository;
    
    
    
    /**
     * RSS Provider plugin.
     */
    public RssProviderBean() {
    
    
        super();
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
     * @return the feed
     */
    public IRssExampleFeedBean getFeed() {
    
    
        return feed;
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
    
    
    @PostConstruct
    public void initializeProvider() {
    
    
        LOGGER.info("Initialisation du plugin RSS");
        
        
        final JobDataMap properties = prepareJobMapForCron();
        //
        registry.create(RSS_PROVIDER_PERIOD, RSS_CRON_VALUE, "java.lang.String",
                "Defines the cron value to fetch rss feeds");
        registry.registerListener(RSS_PROVIDER_PERIOD, this);
        cronRegistryService.registerCronTask(RSS_CRON_JOB, RSS_CRON_VALUE, RssCronJob.class,
                properties);
        
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.ISettingListener#notifyPropertyChanged(org.komea.product.database.model.Setting)
     */
    @Override
    public void notifyPropertyChanged(final Setting _setting) {
    
    
        cronRegistryService.updateCronFrequency(RSS_CRON_JOB, _setting.getValue());
        
    }
    
    
    /**
     * Prepares job map for cron.
     * 
     * @return
     */
    public JobDataMap prepareJobMapForCron() {
    
    
        final JobDataMap properties = new JobDataMap();
        properties.put("lastDate", null);
        properties.put("esperEngine", esperEngine);
        properties.put("repository", rssRepository);
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
     * @param _feed
     *            the feed to set
     */
    public void setFeed(final IRssExampleFeedBean _feed) {
    
    
        feed = _feed;
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
