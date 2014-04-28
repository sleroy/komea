/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.wicket.customer;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.komea.product.backend.service.alert.IAlertTypeService;
import org.komea.product.backend.service.customer.ICustomerService;
import org.komea.product.wicket.alert.AlertPage;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;

/**
 *
 * @author rgalerme
 */
public class CustomerPageTest {
        @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();

    @Before
    public void before() {

        wicketRule.getApplicationContextMock().putBean(Mockito.mock(ICustomerService.class));

    }

    @Test
    public final void testCustomerPage() throws Exception {

        wicketRule.testStart(CustomerPage.class);
    }
  
}
