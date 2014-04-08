/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.userinterface;

import java.util.List;
import org.komea.product.plugins.bugzilla.api.IBugZillaConfigurationService;
import org.komea.product.plugins.bugzilla.data.BugZillaServer;
import org.komea.product.database.model.KpiAlertType;
import org.komea.product.wicket.widget.api.IDeleteAction;

/**
 *
 * @author rgalerme
 */
public class BugZillaDeleteAction implements IDeleteAction<BugZillaServer> {

    private final List<BugZillaServer> serverAffiche;
    private final IBugZillaConfigurationService bugZillaService;

    public BugZillaDeleteAction(List<BugZillaServer> serverAffiche, IBugZillaConfigurationService bugZillaService) {
        this.serverAffiche = serverAffiche;
        this.bugZillaService = bugZillaService;
    }

    @Override
    public void delete(BugZillaServer _object) {
        boolean delete = this.bugZillaService.delete(_object);
        if (delete) {
            this.serverAffiche.remove(_object);
        }

    }

}
