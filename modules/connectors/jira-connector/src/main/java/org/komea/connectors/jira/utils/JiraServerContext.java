
package org.komea.connectors.jira.utils;


import java.text.SimpleDateFormat;

import org.komea.connectors.jira.JiraConfiguration;
import org.komea.connectors.jira.exceptions.BadConfigurationException;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;

/**
 * @author rgalerme
 */
public class JiraServerContext
{
    
    public final static SimpleDateFormat FORMATTER    = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    public final static Integer          GetOccurence = 1000;
    
    private JiraClient                   jiraClient;
    private GreenHopperClient            greenHopper;
    
    private static boolean containUser(final JiraConfiguration configuration) {
    
        boolean result;
        result = "".equals(configuration.getLogin()) || configuration.getLogin() == null || configuration.getPassword() == null
                || "".equals(configuration.getPassword());
        
        return !result;
    }
    
    public JiraServerContext(final JiraConfiguration configuration) throws BadConfigurationException {
    
        if ("".equals(configuration.getUrl())) {
            throw new BadConfigurationException("Map of jira configuration deos not contain the correct field");
        }
        
        if (containUser(configuration)) {
            this.jiraClient = new JiraClient(configuration.getUrl(), new BasicCredentials(configuration.getLogin(),
                    configuration.getPassword()), true);
        } else {
            this.jiraClient = new JiraClient(configuration.getUrl(), null, true);
        }
        
    }
    private JiraServerContext(final JiraClient gh) {
    
        this.jiraClient = gh;
        this.greenHopper = new GreenHopperClient(gh);
    }
    
    public JiraClient getClient() {
    
        return this.jiraClient;
    }
    
    public GreenHopperClient getGreenHopper() {
    
        return this.greenHopper;
    }
}
