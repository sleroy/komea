/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.plugins.bugzilla;

import org.junit.Test;
import org.komea.backend.plugins.bugzilla.BugZillaCheckerBean;
import org.komea.backend.plugins.bugzilla.api.IBugZillaConfiguration;
import org.komea.product.test.spring.AbstractSpringIntegrationTestCase;
import org.mockito.Mock;

/**
 *
 * @author rgalerme
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class BugZillaPluginTest extends AbstractSpringIntegrationTestCase {

//    @Mock
//    private IBugZillaConfiguration conf;

 

    @Test
    public void testPlugin() {
         System.out.println("fin du test");
//        Mockito.when(conf.getServers()).then(new Answer<List<I>)
        BugZillaCheckerBean bbean = new BugZillaCheckerBean();
        bbean.checkServers();
        System.out.println("fin du test");
    }

}
