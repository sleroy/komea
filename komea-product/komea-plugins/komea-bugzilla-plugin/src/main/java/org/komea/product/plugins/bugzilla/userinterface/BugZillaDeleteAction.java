/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.userinterface;

import java.util.List;

import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.wicket.StatelessLayoutPage;
import org.komea.product.wicket.utils.AbstractDeleteAction;

/**
 * @author rgalerme
 */
public class BugZillaDeleteAction extends AbstractDeleteAction<BZServerConfiguration> {

    private final IBZConfigurationDAO bugZillaService;
    private final List<BZServerConfiguration> serverAffiche;

    public BugZillaDeleteAction(
            final List<BZServerConfiguration> _listAffichage,
            final IBZConfigurationDAO bugZillaService,
            StatelessLayoutPage page) {

        super(page, "dialogdelete");
        serverAffiche = _listAffichage;
        this.bugZillaService = bugZillaService;
    }

    @Override
    public void deleteAction() {
         final boolean delete = bugZillaService.delete(getObject());
        if (delete) {
            serverAffiche.remove(getObject());
        }
    }

}
