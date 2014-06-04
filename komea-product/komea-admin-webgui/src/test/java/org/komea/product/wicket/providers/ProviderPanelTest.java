/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.providers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.entities.IProviderService;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;

/**
 *
 * @author rgalerme
 */
public class ProviderPanelTest {

    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();
    


        
    @Before
    public void before() {
    
    
        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IProviderService.class));
        // wicketRule.getApplicationContextMock().putBean(Mockito.mock(IWicketAdminService.class));
//        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IWicketAdminService.class));
        
    }
    public ProviderPanelTest() {
    }

    @Test
    public void testSomeMethod() {
//        ProviderPanel panel = new ProviderPanel("id", Model.of(new Provider()), null);
//        wicketRule.newWicketTester().startComponentInPage(ProviderTableActionPanel.class);
//        WicketTester newWicketTester = wicketRule.newWicketTester();
//       WicketTester wicketTester = new WicketTester(new WicketApplication());
       
    }

}
