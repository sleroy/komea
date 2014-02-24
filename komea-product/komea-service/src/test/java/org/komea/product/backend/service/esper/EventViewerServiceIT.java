/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.komea.event.factory.JenkinsEventFactory;
import org.komea.product.backend.api.IEsperEngine;
import org.komea.product.backend.esper.test.EsperQueryTester;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.enums.RetentionPeriod;
import org.komea.product.database.enums.Severity;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;



/**
 * @author sleroy
 */
public class EventViewerServiceIT extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private IEsperEngine        esperEngine;
    
    @Autowired
    private IEventViewerService service;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventViewerService#getGlobalActivity()}.
     */
    @Test
    public final void testGetGlobalActivity() {
    
    
        final IEvent convertEventDTO =
                EsperQueryTester.convertToEventDTO(new JenkinsEventFactory().sendBuildComplete(
                        "SCERTIFY", 12, "TRUC"));
        convertEventDTO.setDate(new Date());
        esperEngine.sendEvent(convertEventDTO);
        esperEngine.sendEvent(convertEventDTO);
        esperEngine.sendEvent(convertEventDTO);
        esperEngine.sendEvent(convertEventDTO);
        
        
        final List<IEvent> globalActivity = service.getGlobalActivity();
        Assert.assertNotNull(globalActivity);
        final UnmodifiableIterator<IEvent> filter =
                Iterators.filter(globalActivity.iterator(), new Predicate<IEvent>()
                {
                    
                    
                    @Override
                    public boolean apply(final IEvent _input) {
                    
                    
                        return _input.equals(convertEventDTO);
                    }
                    
                });
        Assert.assertTrue(filter.hasNext());
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.EventViewerService#getInstantView(java.lang.String)}.
     */
    @Test
    public final void testGetInstantView() {
    
    
        for (final RetentionPeriod retentionPeriod : RetentionPeriod.values()) {
            final RetentionQueryBuilder retentionQueryBuilder =
                    new RetentionQueryBuilder(Severity.INFO, retentionPeriod);
            final String query = retentionQueryBuilder.build();
            esperEngine.createEPL(new QueryDefinition(query, "DEMO_RETENTION"));
        }
    }
}
