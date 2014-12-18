
package org.komea.connectors.jira.utils;


import java.text.SimpleDateFormat;

import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;

/**
 * @author rgalerme
 */
public class JiraServerContext
{
    
    public final static SimpleDateFormat FORMATTER    = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    public final static Integer          GetOccurence = 1000;
    
    private final JiraClient             jiraClient;
    private final GreenHopperClient      greenHopper;
    
    public JiraServerContext(final JiraClient gh) {
    
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
