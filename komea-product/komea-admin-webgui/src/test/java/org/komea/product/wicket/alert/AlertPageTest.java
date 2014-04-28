/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.alert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.komea.product.backend.service.alert.IAlertTypeService;
import org.komea.product.backend.service.entities.IPersonGroupService;
import org.komea.product.backend.service.entities.IPersonService;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.wicket.persongroup.department.DepartmentPage;
import org.komea.product.wicket.utils.WicketTesterMethodRule;
import org.mockito.Mockito;

/**
 *
 * @author rgalerme
 */
public class AlertPageTest {

    @Rule
    public final WicketTesterMethodRule wicketRule = new WicketTesterMethodRule();

    @Before
    public void before() {

        wicketRule.getApplicationContextMock().putBean(Mockito.mock(IAlertTypeService.class));

    }

    @Test
    public final void testAlertPage() throws Exception {

        wicketRule.testStart(AlertPage.class);
    }

}
