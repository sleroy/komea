/**
 * 
 */

package org.komea.product.backend.service.settings;



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.database.dao.SettingDao;
import org.komea.product.database.model.Setting;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * @author sleroy
 */
@RunWith(MockitoJUnitRunner.class)
public class SettingProxyTest
{
    
    
    private Setting        setting;
    
    @Mock
    private SettingDao     settingDao;
    
    @Mock
    private SettingService settingService;
    
    
    
    @Before
    public void before() {
    
    
        when(settingService.getSettingDAO()).thenReturn(settingDao);
        
        setting = new Setting();
        setting.setValue("1");
        setting.setType(Integer.class.getName());
        when(settingDao.selectByPrimaryKey(Matchers.anyInt())).thenReturn(setting);
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.settings.SettingProxy#getStringValue()}.
     */
    @Test
    public final void testGetStringValue() throws Exception {
    
    
        final SettingProxy settingProxy = new SettingProxy(settingService, 1);
        assertEquals("1", settingProxy.getStringValue());
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.settings.SettingProxy#getValue()}.
     */
    @Test
    public final void testGetValue() throws Exception {
    
    
        final SettingProxy settingProxy = new SettingProxy(settingService, 1);
        assertEquals(Integer.valueOf(1), settingProxy.getValue());
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.settings.SettingProxy#setStringValue(java.lang.String)}.
     */
    @Test
    public final void testSetStringValue() throws Exception {
    
    
        final SettingProxy settingProxy = new SettingProxy(settingService, 1);
        settingProxy.setStringValue("2");
    }
    
    
    /**
     * Test method for {@link org.komea.product.backend.service.settings.SettingProxy#setValue(java.lang.Object)}.
     */
    @Test
    public final void testSetValue() throws Exception {
    
    
        final SettingProxy settingProxy = new SettingProxy(settingService, 1);
        settingProxy.setValue(2);
        verify(settingService, times(1)).update(setting);
        
    }
}
