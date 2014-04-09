
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.plugins.bugzilla.core;

import java.util.ArrayList;
import org.komea.product.plugins.bugzilla.data.BugZillaServer;
import java.util.List;
import org.komea.product.plugins.bugzilla.api.IBugZillaServerConfiguration;

/**
 *
 * @author rgalerme
 * @version $Revision: 1.0 $
 */
public class BugZillaConfiguration {

    private List<BugZillaServer> configurations;

    public BugZillaConfiguration() {
        configurations = new ArrayList<BugZillaServer>();
    }

    /**
     * Method getConfigurations.
     *
     * @return List<BugZillaServer>
     */
    public List<BugZillaServer> getConfigurations() {
        return configurations;
    }

    /**
     * Method setConfigurations.
     *
     * @param configurations List<BugZillaServer>
     */
    public void setConfigurations(List<BugZillaServer> configurations) {
        this.configurations = configurations;
    }
}
