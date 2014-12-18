/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.jiraconnector.core;

import com.tocea.jiraconnector.generalplugin.BadConfigurationException;
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;

/**
 *
 * @author rgalerme
 */
public class JiraConnectorFactory implements IJiraConnectorFactory {

    private static IJiraConnectorFactory INSTANCE = new JiraConnectorFactory();

    static IJiraConnectorFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public JiraServerContext getNewJiraServerContext(JiraConfiguration configuration) throws BadConfigurationException {
        if ("".equals(configuration.getUrl())) {
            throw new BadConfigurationException("Map of jira configuration deos not contain the correct field");
        }
        BasicCredentials bcred = null;
        if (containUser(configuration)) {
            bcred = new BasicCredentials(configuration.getLogin(), configuration.getPass());
        }
        JiraClient client = new JiraClient(configuration.getUrl(), bcred, true);

        return new JiraServerContext(client);
    }

    private boolean containUser(JiraConfiguration configuration) {

        boolean result;
        result = "".equals(configuration.getLogin()) || configuration.getLogin() == null || configuration.getPass() == null || "".equals(configuration.getPass());

        return !result;
    }

}
