
package org.komea.connectors.jira.utils;


import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;

import org.komea.connectors.jira.JiraConfiguration;
import org.komea.connectors.jira.exceptions.BadConfigurationException;

/**
 * @author rgalerme
 */
public class JiraServerFactory implements IJiraServerFactory
{
    
    private static IJiraServerFactory INSTANCE = new JiraServerFactory();
    
    public static IJiraServerFactory getInstance() {
    
        return INSTANCE;
    }
    
    @Override
    public JiraServerContext getNewJiraServerContext(final JiraConfiguration configuration) throws BadConfigurationException {
    
        if ("".equals(configuration.getUrl()) || configuration.getUrl() == null) {
            throw new BadConfigurationException("Map of jira configuration deos not contain the correct field");
        }
        BasicCredentials bcred = null;
        if (containUser(configuration)) {
            bcred = new BasicCredentials(configuration.getLogin(), configuration.getPassword());
        }
        JiraClient client = new JiraClient(configuration.getUrl(), bcred, true);
        
        return new JiraServerContext(client);
    }
    
    private boolean containUser(final JiraConfiguration configuration) {
    
        boolean tmp = "".equals(configuration.getLogin()) || configuration.getLogin() == null || configuration.getPassword() == null
                || "".equals(configuration.getPassword());
        
        return !tmp;
    }
    
}
