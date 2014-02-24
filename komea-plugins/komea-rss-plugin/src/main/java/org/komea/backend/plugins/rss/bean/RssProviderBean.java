
package org.komea.backend.plugins.rss.bean;



import java.util.Timer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.komea.backend.plugins.rss.repositories.api.IRssRepository;
import org.komea.product.backend.plugin.api.EventTypeDef;
import org.komea.product.backend.plugin.api.ProviderPlugin;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



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
    
    
    public static final String   PLUGIN_NAME         = "RSS";
    private static final long    DELAY_MIN           = 1500;
    private static final Logger  LOGGER              = LoggerFactory
                                                             .getLogger(RssProviderBean.class);
    private static final long    PERIOD_DEFAULT      = 20000;
    
    /**
     * 
     */
    private static final String  RSS_CRON_JOB        = "rss_cron_job";
    
    private static final String  RSS_PROVIDER_PERIOD = "rss_refresh_period";
    
    @Autowired
    private ICronRegistryService cronRegistryService;
    
    @Autowired
    private IEventPushService    esperEngine;
    
    @Autowired
    private RssExampleFeedBean   feed;
    
    @Autowired
    private ISettingService      registry;
    
    @Autowired
    private IRssRepository       repository;
    
    
    
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
        
        timerThread = new RssCronJob(repository, esperEngine);
        
        final Timer timer2 = new Timer(true);
        final long parseLong =
                Long.parseLong(registry.initProperty(RSS_PROVIDER_PERIOD,
                        Long.toString(PERIOD_DEFAULT)));
        timer2.scheduleAtFixedRate(timerThread, DELAY_MIN, parseLong);
        timerThread.setTimer(timer2);
        
    }
    
    
    @Override
    public void notify(final String _key, final String _value) {
    
    
        final Timer t = timerThread.getTimer();
        t.purge();
        t.cancel();
        final Timer timer2 = new Timer();
        timerThread.setTimer(timer2);
        timer2.scheduleAtFixedRate(timerThread, DELAY_MIN, Long.parseLong(_value));
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.backend.service.ISettingListener#notifyPropertyChanged(org.komea.product.database.model.Setting)
     */
    @Override
    public void notifyPropertyChanged(final Setting _setting) {
    
    
        // TODO Auto-generated method stub
        
    }
    
}
