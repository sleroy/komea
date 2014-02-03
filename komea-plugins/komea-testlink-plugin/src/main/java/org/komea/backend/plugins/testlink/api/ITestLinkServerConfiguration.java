/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.komea.backend.plugins.testlink.api;

/**
 *
 * @author rgalerme
 */
public interface ITestLinkServerConfiguration {
    
       /**
     *
     * @return
     */
    public ITestLinkServerProxy openProxy();
}
