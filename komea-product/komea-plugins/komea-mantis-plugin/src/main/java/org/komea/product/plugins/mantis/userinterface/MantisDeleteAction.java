/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.mantis.userinterface;

import java.util.List;

import org.komea.product.plugins.mantis.api.IMantisConfigurationDAO;
import org.komea.product.plugins.mantis.model.MantisServerConfiguration;
import org.komea.product.wicket.LayoutPage;
import org.komea.product.wicket.utils.AbstractDeleteAction;

/**
 * @author rgalerme
 */
public class MantisDeleteAction extends AbstractDeleteAction<MantisServerConfiguration> {

    private final IMantisConfigurationDAO bugZillaService;
    private final List<MantisServerConfiguration> serverAffiche;

    public MantisDeleteAction(
            final List<MantisServerConfiguration> _listAffichage,
            final IMantisConfigurationDAO bugZillaService,
            LayoutPage page) {

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
