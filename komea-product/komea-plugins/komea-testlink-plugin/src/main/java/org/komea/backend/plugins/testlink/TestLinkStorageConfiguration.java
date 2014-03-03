/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.testlink;

import java.util.List;

/**
 *
 * @author rgalerme
 */
public class TestLinkStorageConfiguration {
    
    private List<TestLinkServer> configurations;

    public List<TestLinkServer> getConfigurations() {
        return configurations;
    }

    public void setConfigurations(List<TestLinkServer> configurations) {
        this.configurations = configurations;
    }
    
}
