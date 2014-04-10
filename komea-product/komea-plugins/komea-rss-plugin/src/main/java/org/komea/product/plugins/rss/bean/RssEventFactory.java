package org.komea.event.factory;
import org.komea.product.database.dto.EventSimpleDto;
public class RssEventFactory {
public EventSimpleDto sendRssNews(URL _url) {EventSimpleDto event = new EventSimpleDto();
event.setEventType("rss-news");
event.setProvider("/rssnews");
event.setURL(_url.toString());
return event;
}

}
