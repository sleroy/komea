/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;

import com.tocea.core.generalplugin.BadConfigurationException;

/**
 *
 * @author rgalerme
 */
public class JiraServerAPI {

    private JiraClient jiraClient;
    private GreenHopperClient greenHopper;

    public static JiraServerAPI getNewInstance(JiraConfiguration configuration) throws BadConfigurationException {

        if ("".equals(configuration.getUrl())) {
            throw new BadConfigurationException("Map of jira configuration deos not contain the correct field");
        }
        JiraClient client = null;
        if (containUser(configuration)) {
            client = new JiraClient(configuration.getUrl(), new BasicCredentials(configuration.getLogin(), configuration.getPass()));
        } else {
            client = new JiraClient(configuration.getUrl());
        }
        return new JiraServerAPI(client);
    }

    private static boolean containUser(JiraConfiguration configuration) {
        
       boolean result;
        result = "".equals(configuration.getLogin()) || configuration.getLogin() == null || configuration.getPass() == null || "".equals(configuration.getPass());
        
        return !result;
    }

    private JiraServerAPI(JiraClient gh) {
        this.jiraClient = gh;
        greenHopper = new GreenHopperClient(gh);
    }

    public JiraClient getClient() {
        return jiraClient;
    }

    public GreenHopperClient getGreenHopper() {
        return greenHopper;
    }
}
