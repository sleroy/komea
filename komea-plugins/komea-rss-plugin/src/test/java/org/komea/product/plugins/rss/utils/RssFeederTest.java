/**
 * 
 */

package org.komea.product.plugins.rss.utils;



import java.io.File;
import java.net.MalformedURLException;

import org.junit.Assert;
import org.junit.Test;
import org.komea.product.backend.service.esper.IEventPushService;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.plugins.rss.model.RssFeed;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;



/**
 * @author sleroy
 */
public class RssFeederTest
{
    
    
    /**
     * Test method for {@link org.komea.product.plugins.rss.utils.RssFeeder#feed()}.
     * 
     * @throws MalformedURLException
     */
    @Test
    public final void testFeedFetching() throws MalformedURLException {
    
    
        final RssFeed feed = new RssFeed();
        feed.setUrl(new File("src/test/resources/feeds/feed.xml").toURL().toString());
        feed.setFeedName("Sample feed");
        feed.setProjectAssociated("");
        feed.setTeamAssociated("");
        feed.setUserAssociated("");
        
        final IEventPushService eventPushEngine =
                Mockito.mock(IEventPushService.class, Mockito.withSettings().verboseLogging());
        final RssFeeder rssFeeder = new RssFeeder(feed, null, eventPushEngine);
        final ArgumentCaptor<EventSimpleDto> argumentCaptor =
                ArgumentCaptor.forClass(EventSimpleDto.class);
        Assert.assertTrue("Fetching a local rss feed should work", rssFeeder.feed());
        Mockito.verify(eventPushEngine, Mockito.times(25)).sendEventDto(argumentCaptor.capture());
        for (final EventSimpleDto eventDTO : argumentCaptor.getAllValues()) {
            Assert.assertNotNull(eventDTO.getUrl());
            Assert.assertNotNull(eventDTO.getEventType());
            Assert.assertNotNull(eventDTO.getMessage());
            Assert.assertNull(eventDTO.getPersonGroup());
            Assert.assertNull(eventDTO.getProject());
            Assert.assertNotNull(eventDTO.getProvider());
            Assert.assertNotNull(eventDTO.getDate());
            Assert.assertTrue(eventDTO.getPersons().isEmpty());
            Assert.assertTrue(eventDTO.getProperties().isEmpty());
            Assert.assertNotNull(eventDTO.getValue());
            
        }
    }
    
}
