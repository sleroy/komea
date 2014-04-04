/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.product.plugins.testlink.userinterface;

import org.komea.product.plugins.testlink.core.TestLinkServer;
import org.komea.product.wicket.widget.api.IEditAction;

/**
 *
 * @author rgalerme
 */
public class TestLinkEditAction implements IEditAction<TestLinkServer> {

    private final TestLinkPage tpage;

    public TestLinkEditAction(TestLinkPage tpage) {
        this.tpage = tpage;
    }

    @Override
    public void selected(TestLinkServer _tserv) {
        this.tpage.setResponsePage(new TestLinkEditPage(this.tpage.getPageParameters(),_tserv));
    }

}