/**
 * 
 */

package org.komea.product.backend.service.settings;



import java.util.Collections;

import org.junit.Test;
import org.komea.product.backend.service.settings.SettingService;
import org.komea.product.database.dao.SettingDao;
import org.komea.product.database.model.SettingCriteria;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.mockito.Matchers;
import org.mockito.Mockito;



/**
 * @author sleroy
 * @version $Revision: 1.0 $
 */
public class SettingServiceTest extends AbstractSpringIntegrationTestCase
{
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.settings.SettingService#create(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * .
     */
    @Test @Ignore
    public final void testCreate() {
    
    
        final SettingService settingService = new SettingService();
        final SettingDao settingDAO = Mockito.mock(SettingDao.class);
        settingService.setSettingDAO(settingDAO);
        Mockito.when(settingDAO.selectByCriteria(Matchers.any(SettingCriteria.class))).thenReturn(
                Collections.EMPTY_LIST);
        settingService.create("KEY_DEMO", "KEY_VALUE", String.class.getName(), "Demo Key");
    }
    
}
