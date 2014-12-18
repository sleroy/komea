/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.jiraconnector.core;

import com.tocea.jiraconnector.generalplugin.BadConfigurationException;

/**
 *
 * @author rgalerme
 */
public interface IJiraConnectorFactory {
    
    public JiraServerContext getNewJiraServerContext(JiraConfiguration configuration)throws BadConfigurationException ;
            
}
