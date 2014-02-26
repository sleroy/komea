
package org.komea.product.plugins.rss.bean;



import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
        name = "Rss Provider plugin",
        type = ProviderType.NEWS,
        url = "/rssnews")
public class RssProviderBean implements org.komea.product.backend.service.ISettingListener
{
    
    
    private static final Logger  LOGGER              = LoggerFactory
                                                             .getLogger(RssProviderBean.class);
    /**
     * 
     */
    private static final String  RSS_CRON_JOB        = "rss_cron_job";
    
    /**
     * 
     */
    private static final String  RSS_CRON_VALUE      = "0/5 * * * * ?";
    private static final String  RSS_PROVIDER_PERIOD = "rss_refresh_period";
    
    @Autowired
    private ICronRegistryService cronRegistryService;
    
    @Autowired
    private IEventPushService    esperEngine;
    
    @Autowired
    private IRssExampleFeedBean  feed;
    
    @Autowired
    private ISettingService      registry;
    
    @Autowired
    private IRssRepositories       rssRepository;
    
    
    
    /**
     * RSS Provider plugin.
     */
    public RssProviderBean() {
    
    
        super();
    }
    
    
    @PreDestroy
    public void destroy() {
    
    
        cronRegistryService.removeCronTask(RSS_CRON_JOB);
    }
    
    
    @PostConstruct
    public void initializeProvider() {
    
    
        LOGGER.info("Initialisation du plugin RSS");
        
        
        final JobDataMap properties = new JobDataMap();
        properties.put("lastDate", null);
        properties.put("esperEngine", esperEngine);
        properties.put("repository", this);
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
    
}
