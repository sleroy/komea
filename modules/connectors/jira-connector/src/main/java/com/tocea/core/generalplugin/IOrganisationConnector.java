/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core.generalplugin;


/**
 *
 * @author rgalerme
 */
public interface IOrganisationConnector<TConf> extends IConfigurationConnector<TConf> {
   
    
    public void initOrganisation();
    public void updateOrganisation();
}
