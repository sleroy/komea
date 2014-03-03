
package org.komea.product.plugins.rss.bean;



import java.io.File;
import java.io.IOException;

import org.komea.product.backend.esper.test.DSLEventBuilder;
import org.komea.product.backend.esper.test.EventFactoryBuilder;



/**
 */
public class RssEventFactoryBuilder
{
    
    
    /**
     * Method main.
     * 
     * @param args
     *            String[]
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
    
    
        final EventFactoryBuilder eventFactoryBuilder =
                new EventFactoryBuilder("RssEventFactory", "org.komea.product.plugins.rss.bean",
                        new File("src/main/java"));
        
        
        eventFactoryBuilder.register(DSLEventBuilder
                .newEvent("sendRssNews", "/rssnews", "rss-news").withURL().build());
        
        eventFactoryBuilder.generate();
    }
    
    
    public RssEventFactoryBuilder() {
    
    
        super();
        
    }
}
