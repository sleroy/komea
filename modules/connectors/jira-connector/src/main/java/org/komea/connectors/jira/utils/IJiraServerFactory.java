
package org.komea.connectors.jira.utils;


import org.komea.connectors.jira.IJiraConfiguration;
import org.komea.connectors.jira.exceptions.BadConfigurationException;

public interface IJiraServerFactory
{
    
    public JiraServerContext getNewJiraServerContext(IJiraConfiguration configuration) throws BadConfigurationException;
}
