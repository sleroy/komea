/**
 * 
 */

package org.komea.product.backend.service.esper;



import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.komea.product.database.alert.Event;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.model.Provider;
import org.komea.product.service.dto.AlertTypeStatistic;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public class AlertStatisticsServiceIT extends AbstractSpringIntegrationTestCase
{
    
    
    @Autowired
    private AlertStatisticsService alertStats;
    
    @Autowired
    private EsperEngineBean        esperEngine;
    
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.esper.AlertStatisticsService#getReceivedAlertTypesIn24LastHours()}.
     */
    @Test
    @Ignore
    public final void testGetReceivedAlertTypesIn24LastHours() {
    
    
        final Event event = new Event();
        event.setProvider(new Provider(0, ProviderType.OTHER, "TYPE1", "", ""));
        esperEngine.sendEvent(event);
        esperEngine.sendEvent(event);
        esperEngine.sendEvent(event);
        esperEngine.sendEvent(event);
        
        final List<AlertTypeStatistic> receivedAlertTypesIn24Hours =
                alertStats.getReceivedAlertTypesIn24LastHours();
        final AlertTypeStatistic alertTypeStatistic = receivedAlertTypesIn24Hours.get(0);
        Assert.assertTrue(4L <= alertTypeStatistic.getNumber());
        
        boolean found = false;
        for (final AlertTypeStatistic stat : receivedAlertTypesIn24Hours) {
            found |= stat.getType().equals("TYPE1");
        }
        Assert.assertTrue("Event is not found", found);
        ;
        
    }
}
