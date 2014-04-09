/**
 * 
 */

package org.komea.product.wicket.cronpage;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.cron.CronDetails;
import org.komea.product.backend.service.cron.ICronRegistryService;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;
import org.quartz.Trigger.TriggerState;

import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class CronPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    
    
    @Before
    public void before() {
    
    
        final ICronRegistryService cronRegistryService = Mockito.mock(ICronRegistryService.class);
        final List<CronDetails> cronDetails = new ArrayList<CronDetails>();
        final CronDetails cronDetail = new CronDetails("CRON_NAME", "CRON_EXP");
        cronDetail.setNextTime(new Date());
        cronDetail.setStatus(TriggerState.BLOCKED);
        cronDetail.setTriggerState(TriggerState.COMPLETE);
        cronDetails.add(cronDetail);
        when(cronRegistryService.getCronTasks()).thenReturn(cronDetails);
        wicketRule.getApplicationContextMock().putBean(cronRegistryService);
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.cronpage.CronPage#CronPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testCronPage() throws Exception {
    
    
        wicketRule.testStart(CronPage.class);
    }
    
    
}
