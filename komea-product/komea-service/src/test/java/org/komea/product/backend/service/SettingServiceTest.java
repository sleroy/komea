/**
 * 
 */

package org.komea.product.backend.service;



import org.junit.Test;
import org.komea.product.database.dao.SettingDao;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.mockito.Mock;



/**
 * @author sleroy
 */
public class SettingServiceTest extends AbstractSpringIntegrationTestCase
{
    
    
    @Mock
    private SettingDao settingDAO;
    
    
    
    /**
     * Test method for
     * {@link org.komea.product.backend.service.SettingService#create(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
     * .
     */
    @Test
    public final void testCreate() {
    
    
        final SettingService settingService = new SettingService();
        
    }
    
    //
    // /**
    // * Test method for {@link org.komea.product.backend.service.SettingService#getProxy(java.lang.Integer)}.
    // */
    // @Test
    // public final void testGetProxyInteger() {
    //
    //
    // fail("Not yet implemented"); // TODO
    // }
    //
    //
    // /**
    // * Test method for {@link org.komea.product.backend.service.SettingService#getProxy(java.lang.String)}.
    // */
    // @Test
    // public final void testGetProxyString() {
    //
    //
    // fail("Not yet implemented"); // TODO
    // }
    //
    //
    // /**
    // * Test method for {@link org.komea.product.backend.service.SettingService#getSettingDAO()}.
    // */
    // @Test
    // public final void testGetSettingDAO() {
    //
    //
    // fail("Not yet implemented"); // TODO
    // }
    //
    //
    // /**
    // * Test method for {@link org.komea.product.backend.service.SettingService#getSettings()}.
    // */
    // @Test
    // public final void testGetSettings() {
    //
    //
    // fail("Not yet implemented"); // TODO
    // }
    //
    //
    // /**
    // * Test method for {@link org.komea.product.backend.service.SettingService#newSelectOnNameCriteria(java.lang.String)}.
    // */
    // @Test
    // public final void testNewSelectOnNameCriteria() {
    //
    //
    // fail("Not yet implemented"); // TODO
    // }
    //
    //
    // /**
    // * Test method for {@link org.komea.product.backend.service.SettingService#setSettingDAO(org.komea.product.database.dao.SettingDao)}.
    // */
    // @Test
    // public final void testSetSettingDAO() {
    //
    //
    // fail("Not yet implemented"); // TODO
    // }
    //
    //
    // /**
    // * Test method for {@link org.komea.product.backend.service.SettingService#update(org.komea.product.database.model.Setting)}.
    // */
    // @Test
    // public final void testUpdate() {
    //
    //
    // fail("Not yet implemented"); // TODO
    // }
    
}
