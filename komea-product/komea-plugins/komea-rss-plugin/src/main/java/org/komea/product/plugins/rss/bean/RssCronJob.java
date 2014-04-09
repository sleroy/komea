
package org.komea.product.plugins.rss.bean;



// https://forums.terracotta.org/forums/posts/list/2768.page

import java.util.Date;
import java.util.List;

import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.plugins.rss.model.RssFeed;
import org.komea.product.plugins.rss.repositories.api.IRssRepositories;
import org.komea.product.plugins.rss.utils.RssFeeder;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



@DisallowConcurrentExecution
public class RssCronJob implements Job
{
    
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RssProviderPlugin.class);
    
    
    @Autowired
    private IEventPushService   esperEngine;
    private Date                lastDate;
    
    @Autowired
    private IRssRepositories    rssRepositories;
    
    
    
    public RssCronJob() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(final JobExecutionContext _context) throws JobExecutionException {
    
    
        _context.getMergedJobDataMap();
        
        
        final Date launched = new Date();
        final List<RssFeed> feeds = rssRepositories.getDAO().selectAll();
        
        for (final RssFeed fetch : feeds) {
            LOGGER.debug("Fetching RSS feed  : {} {}", fetch.getFeedName(), fetch.getUrl());
            try {
                new RssFeeder(fetch, esperEngine).feed();
            } finally {
                rssRepositories.getDAO().saveOrUpdate(fetch);
            }
        }
        _context.put("lastDate", launched);
    }
    
    
    public IEventPushService getEsperEngine() {
    
    
        return esperEngine;
    }
    
    
    public Date getLastDate() {
    
    
        return lastDate;
    }
    
    
    public IRssRepositories getRssRepositories() {
    
    
        return rssRepositories;
    }
    
    
    public void setEsperEngine(final IEventPushService _esperEngine) {
    
    
        esperEngine = _esperEngine;
    }
    
    
    public void setLastDate(final Date _lastDate) {
    
    
        lastDate = _lastDate;
    }
    
    
    public void setRssRepositories(final IRssRepositories _rssRepositories) {
    
    
        rssRepositories = _rssRepositories;
    }
    
}
