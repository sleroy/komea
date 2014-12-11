/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.jiraconnector.generalplugin;

import java.util.Date;

/**
 *
 * @author rgalerme
 */
public interface IEventConnector<TConf> extends IConfigurationConnector<TConf> {

    public void updateEvents(Date _lastLaunchDate);

}
