/**
 * 
 */

package org.komea.product.wicket.console;


import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.ISettingProxy;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;

/**
 * @author sleroy
 */
public class ConsolePageTest {
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    
    @Before
    public void before() {
    
        final ISettingService settingService = Mockito.mock(ISettingService.class);
        wicketRule.getApplicationContextMock().putBean(settingService);
        final ISettingProxy settingProxy = Mockito.mock(ISettingProxy.class);
        when(settingService.getProxy("logfile_path")).thenReturn(settingProxy);
        when(settingProxy.getStringValue()).thenReturn("src/test/resources/console.log");
        // wicketRule.getApplicationContextMock().putBean(Mockito.mock(ISettingService.class));
    }
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.console.ConsolePage#ConsolePage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testConsolePage() throws Exception {
    
        wicketRule.testStart(ConsolePage.class);
        
    }
    
}
