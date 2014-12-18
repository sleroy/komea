
package org.komea.connectors.jira;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

import org.junit.Test;
import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.utils.JiraServerContext;

public class JiraEventsConnectorTests
{
    
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    
    @Test
    public void test() throws BadConfigurationException, ParseException {
    
        CountingStorage storage = new CountingStorage();
        JiraEventsConnector jc = new JiraEventsConnector(storage);
        JiraConfiguration config = new JiraConfiguration("https://jira.mongodb.org/");
        
        Date since = DATE_FORMAT.parse("2014/11/10 11:00");
        jc.push(config, since, 3);
        
        assertEquals(3, storage.counters.get(JiraEventsConnector.EVENT_NEW_BUG).intValue());
        
    }
    
    @Test
    public void testJiraApi() throws ParseException, JiraException {
    
        Date _date = DATE_FORMAT.parse("2014/11/10 11:00");
        String format = JiraServerContext.FORMATTER.format(_date);
        JiraConfiguration config = new JiraConfiguration("https://jira.mongodb.org/");
        JiraClient jiraClient =  new JiraClient(config.getUrl(), null, true);
        
        Issue.SearchResult searchIssues = jiraClient.searchIssues("created > \"" + format + "\"", 10);
        assertTrue(searchIssues.issues.size() > 0);
        
    }
    
}
