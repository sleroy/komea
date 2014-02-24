
package org.komea.backend.plugins.rss.utils;



import java.net.URL;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.komea.backend.plugins.rss.bean.RssProviderBean;
import org.komea.backend.plugins.rss.model.RssFeed;
import org.komea.product.backend.service.esper.IEventPushService;
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
    
    
    private static final Logger     LOGGER = LoggerFactory.getLogger(RssFeeder.class);
    
    private final IEventPushService esperEngine;
    private final RssFeed           fetch;
    
    private final Date              lastDate;
    
    
    
    /**
     * Builds the object
     * 
     * @param _fetch
     *            the rss fedd
     * @param _lastDate
     *            the last fetched date.
     */
    
    public RssFeeder(
            final RssFeed _fetch,
            final Date _lastDate,
            final IEventPushService _esperEngine) {
    
    
        fetch = _fetch;
        lastDate = _lastDate;
        esperEngine = _esperEngine;
        
    }
    
    
    public void feed() {
    
    
        LOGGER.info("Checking update from {}", fetch.getFeedName());
        
        XmlReader reader = null;
        final DateTime fromTime = lastDate == null ? null : new DateTime(lastDate);
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
                final DateTime dateTime = new DateTime(publishedDate);
                ;
                if (fromTime != null && dateTime.isBefore(fromTime)) {
                    continue; /* We skip older events */
                }
                
                final StringBuilder message = new StringBuilder();
                
                for (final Object syndCnt : entry.getContents()) {
                    final SyndContent syndContent = (SyndContent) syndCnt;
                    
                    message.append(syndContent.getValue());
                }
                final AlertBuilder builder =
                        AlertBuilder.create().setCategory("NEWS")
                                .setCriticity(fetch.getDefaultCriticity().name())
                                .setDate(publishedDate).setFullMessage(message.toString())
                                .setIcon("/static/pics/rss.png").setShortMessage(entry.getTitle())
                                
                                .setProvider(RssProviderBean.PLUGIN_NAME).setAlertType("NEW_FEED")
                                .setURL(entry.getUri());
                if (!fetch.getProjectAssociated().isEmpty()) {
                    builder.setProject(fetch.getProjectAssociated());
                }
                builder.send(esperEngine);
                
            }
            
        } catch (final Exception e) {
            LOGGER.error("Rss fetching failed for " + fetch.getFeedName() + " : " + e.getMessage(),
                    e);
            
        } finally {
            if (reader != null) {
                IOUtils.closeQuietly(reader);
            }
        }
        
    }
    
    
    private Date getFeedTime(final SyndEntry _entry) {
    
    
        if (_entry.getUpdatedDate() != null) { return _entry.getUpdatedDate(); }
        return _entry.getPublishedDate();
        
    }
}
