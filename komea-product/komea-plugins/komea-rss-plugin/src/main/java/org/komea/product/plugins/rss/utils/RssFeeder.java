
package org.komea.product.plugins.rss.utils;



import java.net.URL;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.plugins.rss.bean.RssEventFactory;
import org.komea.product.plugins.rss.model.RssFeed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;



/**
 * Fetchs the new feeds and produces alerts.
 * 
 * @author sleroy
 */
public class RssFeeder
{
    
    
    private static final Logger     LOGGER          = LoggerFactory.getLogger("rss-feeder");
    
    private final IEventPushService esperEngine;
    private final RssFeed           fetch;
    
    private final Date              lastDate;
    
    
    private final RssEventFactory   rssEventFactory = new RssEventFactory();
    
    
    
    /**
     * Builds the object
     * 
     * @param _fetch
     *            the rss fedd
     * @param _lastDate
     *            the last fetched date.
     */
    
    public RssFeeder(final RssFeed _fetch, final IEventPushService _esperEngine) {
    
    
        fetch = _fetch;
        lastDate = _fetch.getLastFetchDate();
        esperEngine = _esperEngine;
        _fetch.setLastFetchDate(new Date());
        
    }
    
    
    /**
     * Feed a stream
     */
    public boolean feed() {
    
    
        boolean res = false;
        
        LOGGER.info("Checking update from {}", fetch.getFeedName());
        
        XmlReader reader = null;
        final DateTime lastFetchTime = lastDate == null ? null : new DateTime(lastDate);
        try {
            final URL url = new URL(fetch.getUrl());
            reader = new XmlReader(url);
            final SyndFeed feed = new SyndFeedInput().build(reader);
            LOGGER.debug("Feed Title: " + feed.getAuthor());
            LOGGER.debug("Feed Copyright: " + feed.getCopyright());
            LOGGER.debug("Feed Description: " + feed.getDescription());
            
            for (final Object synd : feed.getEntries()) {
                final SyndEntry entry = (SyndEntry) synd;
                
                final Date publishedDate = getFeedTime(entry);
                if (publishedDate == null) {
                    continue;
                }
                /**
                 * Only posterior events will be published.
                 */
                final DateTime articleTime = new DateTime(publishedDate);
                
                if (articleTime.isBefore(lastFetchTime)) {
                    continue; /* We skip older events */
                }
                
                final StringBuilder message = new StringBuilder();
                
                for (final Object syndCnt : entry.getContents()) {
                    final SyndContent syndContent = (SyndContent) syndCnt;
                    
                    message.append(syndContent.getValue());
                }
                final EventSimpleDto rssNews =
                        rssEventFactory.sendRssNews(message.toString(), publishedDate,
                                entry.getUri());
                // if (!fetch.getProjectAssociated().isEmpty()) {
                // builder.setProject(fetch.getProjectAssociated());
                // }
                esperEngine.sendEventDto(rssNews);
                
            }
            res = true;
        } catch (final Exception e) {
            LOGGER.error("Rss fetching failed for " + fetch.getFeedName() + " : " + e.getMessage(),
                    e);
            res = false;
        } finally {
            if (reader != null) {
                IOUtils.closeQuietly(reader);
            }
            
        }
        return res;
    }
    
    
    private Date getFeedTime(final SyndEntry _entry) {
    
    
        if (_entry.getUpdatedDate() != null) { return _entry.getUpdatedDate(); }
        return _entry.getPublishedDate();
        
    }
}
