
package org.komea.product.plugins.rss.bean;



import java.util.Date;

import org.komea.product.database.dto.EventSimpleDto;



public class RssEventFactory
{
    
    
    /**
     * Send rss news.
     * 
     * @param _message
     *            the message
     * @param _publishedDate
     *            the date
     * @param _uri
     *            the uri
     * @return
     */
    public EventSimpleDto sendRssNews(
            final String _message,
            final Date _publishedDate,
            final String _uri) {
    
    
        final EventSimpleDto event = new EventSimpleDto();
        event.setEventType("rss-news");
        event.setProvider("/rssnews");
        event.setUrl(_uri);
        event.setMessage(_message.isEmpty() ? " " : _message);
        event.setDate(_publishedDate);
        return event;
        
    }
    
}
