/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.userinterface;

import java.util.List;

import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.AbstractDeleteAction;

/**
 *
 * @author rgalerme
 */
public class TestLinkDeleteAction extends AbstractDeleteAction<TestLinkServer> {

    private final List<TestLinkServer> serverAffiche;
    private final ITestLinkServerDAO testlinkService;

    public TestLinkDeleteAction(List<TestLinkServer> serverAffiche, ITestLinkServerDAO testLinkService, LayoutPage page) {
        super(page, "dialogdelete");
        this.serverAffiche = serverAffiche;
        this.testlinkService = testLinkService;
    }

    @Override
    public void deleteAction() {
        boolean delete = this.testlinkService.delete(getObject());
        if (delete) {
            this.serverAffiche.remove(getObject());
        }
    }

}
