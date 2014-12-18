
package org.komea.connectors.jira;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

import org.junit.Ignore;
import org.junit.Test;
import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.utils.JiraConnectorFactory;
import org.komea.connectors.jira.utils.JiraServerContext;
import org.mockito.Mockito;

public class JiraEventsConnectorTests
{
    
    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private final static String           DATE        = "2014/12/16 11:00";
    private final static int              OCCURENCE   = 5;
    
    @Test
    public void test() throws BadConfigurationException, ParseException {
    
        CountingStorage storage = new CountingStorage();
        JiraEventsConnector jc = new JiraEventsConnector(storage, JiraConnectorFactory.getInstance());
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
        JiraClient jiraClient = new JiraClient(config.getUrl(), null, true);
        
        Issue.SearchResult searchIssues = jiraClient.searchIssues("created > \"" + format + "\"", 10);
        assertTrue(searchIssues.issues.size() > 0);
        
    }
    
    @Test
    @Ignore
    public void testUpdateEvents() {
    
        testcoreUpdateEvents(3);
    }
    
    @Test
    @Ignore
    public void testUpdateEventsWay2() {
    
        testcoreUpdateEvents(1);
    }
    
    private void testcoreUpdateEvents(final int nb) {
    
        try {
            JiraConfiguration jconf = new JiraConfiguration("https://jira.atlassian.com/", null, null);
            // initialisation
            Date since = DATE_FORMAT.parse(DATE);
            
            // Mock de jiraClient
            JiraClient mockJiraClient = Mockito.mock(JiraClient.class);
            JiraConnectorFactory mockFactory = Mockito.mock(JiraConnectorFactory.class);
            JiraServerContext jcontext = new JiraServerContext(mockJiraClient);
            Mockito.when(mockFactory.getNewJiraServerContext(jconf)).thenReturn(jcontext);
            
            CountingStorage storage = new CountingStorage();
            
            JiraEventsConnector jc = new JiraEventsConnector(storage, mockFactory);
            
            // ----------------
            // Maitrise des valeurs retourner par le mock de jira client
            createOccurence(mockJiraClient, nb);
            
            jc.push(jconf, since);
            // verification
            long countEventsOfType = storage.counters.get(JiraEventsConnector.EVENT_NEW_BUG);
            assertTrue(countEventsOfType == nb * OCCURENCE);
        } catch (ParseException | BadConfigurationException | JiraException ex) {
            Logger.getLogger(JiraEventsConnectorTests.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }
    
    private void createOccurence(final JiraClient mockJiraClient, final int nombre) throws JiraException, ParseException {
    
        int total = nombre * OCCURENCE;
        Date since = DATE_FORMAT.parse(DATE);
        
        for (int i = 0; i < nombre; i++) {
            Issue.SearchResult rs = new Issue.SearchResult();
            rs.issues = createIssues(OCCURENCE, i * OCCURENCE);
            rs.start = i * OCCURENCE;
            rs.max = OCCURENCE;
            rs.total = total;
            
            // Mockito.when(mockJiraClient.searchIssues("created > \"" + format + "\"", OCCURENCE)).thenReturn(rs);
            String jql1 = "created > \"" + JiraServerContext.FORMATTER.format(since) + "\"";
            Mockito.when(mockJiraClient.searchIssues(jql1, null, OCCURENCE, i * OCCURENCE)).thenReturn(rs);
            String jql2 = "updated > \"" + JiraServerContext.FORMATTER.format(since) + "\"";
            Mockito.when(mockJiraClient.searchIssues(jql2, null, OCCURENCE, i * OCCURENCE)).thenReturn(rs);
        }
        
    }
    
    private List<Issue> createIssues(final int nb, final int start) {
    
        List<Issue> result = new ArrayList<>();
        
        for (int i = 0; i < nb; i++) {
            Issue mock = Mockito.mock(Issue.class);
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", "name" + (start + i));
            map.put("key", "key" + (start + i));
            Mockito.when(mock.getFields()).thenReturn(map);
            result.add(mock);
        }
        
        return result;
    }
}
