
package org.komea.product.plugins.demodata;



import javax.annotation.PostConstruct;

import org.komea.product.plugins.rss.bean.IRssExampleFeedBean;
import org.komea.product.plugins.rss.model.RssFeed;
import org.komea.product.plugins.rss.repositories.api.IRssRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * Inserts a default RSS Feed.
 * 
 * @author sleroy
 */
@Component
public class RssExampleFeedBean implements IRssExampleFeedBean
{
    
    
    private static final String DEFAULT_VALUE    = "1";
    
    private static final String RSS_SAMPLE_FEEDS = "RSS_SAMPLE_FEEDS";
    
    @Autowired
    private IRssRepositories    repository;
    
    
    
    public RssExampleFeedBean() {
    
    
        super();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.product.plugins.rss.bean.IRssExampleFeedBean#initRssFeed()
     */
    @Override
    @PostConstruct
    public void initRssFeed() {
    
    
        repository.getDAO().enableSaveOnChange();
        
        registerFeed("Techcrunch RSS", "http://feeds.feedburner.com/TechCrunch/", "SCERTIFY");
        registerFeed("Java Lobby Zone", "http://feeds.dzone.com/dzone/frontpage?format=xml",
                "SCERTIFY");
        registerFeed("Martin Fowler Blog", "http://martinfowler.com/feed.atom", "SCERTIFY");
        registerFeed("Coding Horror", "http://feeds.feedburner.com/codinghorror?format=xml", "SRA");
        registerFeed("WebAppers", "http://feeds.feedburner.com/Webappers?format=xml", "SRA");
        registerFeed("Digg", "http://digg.com/rss/popular.rss");
        registerFeed("Ibm", "http://www.ibm.com/developerworks/views/java/rss/libraryview.jsp",
                "SCERTIFY");
        registerFeed("Oreilly Java", "http://feeds.feedburner.com/oreilly/java?format=xml",
                "SCERTIFY");
        registerFeed("How to do it in java : Design patterns",
                "http://howtodoinjava.com/category/design-patterns/feed/", "SRA");
        registerFeed("How to do it in java : For fun only",
                "http://howtodoinjava.com/category/for-fun-only/feed/", "SCERTIFY");
        
    }
    
    
    private void registerFeed(final String _feedName, final String _url) {
    
    
        registerFeed(_feedName, _url, "");
    }
    
    
    private void registerFeed(final String _feedName, final String _url, final String _project) {
    
    
        final RssFeed rssFeed = new RssFeed();
        rssFeed.setFeedName(_feedName);
        rssFeed.setUrl(_url);
        rssFeed.setProjectAssociated(_project);
        repository.getDAO().saveOrUpdate(rssFeed);
        
        
    }
}
