/**
 * 
 */

package org.komea.product.wicket.settings;



import java.util.Collections;

import org.apache.wicket.util.tester.FormTester;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.ISettingService;
import org.komea.product.database.model.Setting;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */
public class SettingsPageTest
{
    
    
    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    private ISettingService             mock;
    
    
    
    @Before
    public void before() {
    
    
        mock = Mockito.mock(ISettingService.class);
        wicketRule.getApplicationContextMock().putBean(mock);
        final Setting setting = new Setting();
        setting.setSettingKey("KEY");
        setting.setType(String.class.getName());
        setting.setValue("1");
        setting.setDescription("Description");
        when(mock.getSettings()).thenReturn(Collections.singletonList(setting));
    }
    
    
    @Test
    public final void testForm() {
    
    
        final WicketTester newWicketTester = wicketRule.newWicketTester();
        try {
            newWicketTester.startPage(SettingsPage.class);
            newWicketTester.debugComponentTrees();
            final FormTester newFormTester = newWicketTester.newFormTester("form");
            
            assertEquals("KEY",
                    newWicketTester.getComponentFromLastRenderedPage("form:settings:0:settingKey")
                            .getDefaultModelObjectAsString());
            assertEquals("Description",
                    newWicketTester.getComponentFromLastRenderedPage("form:settings:0:description")
                            .getDefaultModelObjectAsString());
            
            newFormTester.setValue("settings:0:value", "GNI");
            newFormTester.submit();
            verify(mock, times(1)).update(Matchers.any(Setting.class));
            
            
            newWicketTester.assertRenderedPage(SettingsPage.class); // Submit returns on the same page
            
            
        } finally {
            newWicketTester.destroy();
        }
        
        
    }
    
    
    /**
     * Test method for
     * {@link org.komea.product.wicket.settings.SettingsPage#SettingsPage(org.apache.wicket.request.mapper.parameter.PageParameters)}.
     */
    @Test
    public final void testSettingsPage() throws Exception {
    
    
        wicketRule.testStart(SettingsPage.class);
        
    }
    
}
