/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.backend.plugins.bugzilla;

import java.util.ArrayList;
import java.util.List;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration;
import org.komea.backend.plugins.bugzilla.api.IBugZillaConfiguration;
import org.springframework.stereotype.Component;

/**
 *
 * @author rgalerme
 */
@Component
public class BugZillaConfiguration implements IBugZillaConfiguration {

    private List<IBugZillaServerConfiguration> servers = null;

    @Override
    public List<IBugZillaServerConfiguration> getServers() {

        if (servers == null) {
            this.servers = constantServer();
        }
        return servers;
    }

    public static List<IBugZillaServerConfiguration> constantServer() {
        List<IBugZillaServerConfiguration> serveurConfiguration = new ArrayList<IBugZillaServerConfiguration>();

        IBugZillaServerConfiguration server1 = new BugZillaServerConfiguration("https://bugzilla.mozilla.org/", "darckloulou-face4@yahoo.fr", "0351Darkra");
        serveurConfiguration.add(server1);

        return serveurConfiguration;
    }
}
