/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.testlink.userinterface;

import java.util.List;
import org.apache.wicket.ajax.AjaxRequestTarget;

import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.komea.product.wicket.widget.api.IDeleteAction;

/**
 *
 * @author rgalerme
 */
public class TestLinkDeleteAction implements IDeleteAction<TestLinkServer> {

    private final List<TestLinkServer> serverAffiche;
    private final ITestLinkServerDAO testlinkService;

    public TestLinkDeleteAction(List<TestLinkServer> serverAffiche, ITestLinkServerDAO testLinkService) {
        this.serverAffiche = serverAffiche;
        this.testlinkService = testLinkService;
    }


    @Override
    public void delete(TestLinkServer _object, AjaxRequestTarget _target) {
         boolean delete = this.testlinkService.delete(_object);
        if (delete) {
            this.serverAffiche.remove(_object);
        }
    }

}
