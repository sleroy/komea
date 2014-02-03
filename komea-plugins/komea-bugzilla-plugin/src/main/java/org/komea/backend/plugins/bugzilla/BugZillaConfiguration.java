
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.bugzilla;

import java.util.List;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration;

/**
 *
 * @author rgalerme
 */
public class BugZillaConfiguration {
    
    private List<BugZillaServer> configurations;

    public List<BugZillaServer> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<BugZillaServer> configurations) {
        this.configurations = configurations;
    }
}
