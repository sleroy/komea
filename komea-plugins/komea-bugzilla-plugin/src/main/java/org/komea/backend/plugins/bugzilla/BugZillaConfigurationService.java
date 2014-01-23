/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.bugzilla;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.komea.backend.plugins.bugzilla.api.IBugZillaConfiguration;
import org.komea.backend.plugins.bugzilla.api.IBugZillaServerConfiguration;
import org.springframework.stereotype.Service;



/**
 * @author rgalerme
 */
@Service
public class BugZillaConfigurationService implements IBugZillaConfiguration
{
    
    
    private List<IBugZillaServerConfiguration> servers = null;
    
    
    
    @Override
    public List<IBugZillaServerConfiguration> getServers() {
    
    
        return servers;
    }
    
    
    @PostConstruct
    public void initSampleData() {
    
    
        servers = new ArrayList<IBugZillaServerConfiguration>();
        
        final IBugZillaServerConfiguration server1 =
                new BugZillaServerConfiguration("https://bugzilla.mozilla.org/",
                        "darckloulou-face4@yahoo.fr", "0351Darkra");
        servers.add(server1);
        
    }
}
