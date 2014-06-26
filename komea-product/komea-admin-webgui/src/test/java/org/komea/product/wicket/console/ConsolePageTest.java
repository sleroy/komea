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
    private ISettingService             settingService;
    private ISettingProxy               settingProxy;
    
    @Before
    public void before() {
    
        settingService = Mockito.mock(ISettingService.class);
        wicketRule.getApplicationContextMock().putBean(settingService);
        settingProxy = Mockito.mock(ISettingProxy.class);
        when(settingService.getProxy("logfile_path")).thenReturn(settingProxy);
        
        // wicketRule.getApplicationContextMock().putBean(Mockito.mock(ISettingService.class));
    }
    
    @Test
    public final void testConsolePage() throws Exception {
    
        when(settingService.getProxy("logfile_path")).thenReturn(settingProxy);
        when(settingProxy.getStringValue()).thenReturn("src/test/resources/console.log");
        wicketRule.testStart(ConsolePage.class);
        
    }
    
    @Test
    public final void testConsolePage_wrong_path() throws Exception {
    
        when(settingService.getProxy("logfile_path")).thenReturn(settingProxy);
        when(settingProxy.getStringValue()).thenReturn("src/test/resources/WRONG_PATH");
        wicketRule.testStart(ConsolePage.class);
        
    }
    
}
