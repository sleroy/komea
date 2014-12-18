
package org.komea.connectors.jira.utils;


import org.komea.connectors.jira.JiraConfiguration;
import org.komea.connectors.jira.exceptions.BadConfigurationException;

public interface IJiraServerFactory
{
    
    public JiraServerContext getNewJiraServerContext(JiraConfiguration configuration) throws BadConfigurationException;
}
