
package org.komea.product.plugins.rss;



import org.junit.Assert;
import org.junit.Test;
import org.komea.product.plugins.rss.repositories.api.IRssRepositories;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



public class RssFeederIT extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private RssExampleFeedBean feedBean;
    @Autowired
    private IRssRepositories   repository;
    
    
    
    /**
     * Checks the default feeds are loaded.
     * 
     * @throws Exception
     */
    @Transactional
    @Test 
    public void verifFeeds() throws Exception {
    
    
        Assert.assertEquals(10, repository.getDAO().selectAll().size());
    }
}
