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
import org.komea.connectors.jira.utils.JiraServerFactory;
import org.komea.connectors.jira.utils.JiraServerContext;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class JiraEventsConnectorTests {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private final static String DATE = "2014/12/16 11:00";
    private final static int OCCURENCE = 5;

    @Test
    public void test() throws BadConfigurationException, ParseException {

        CountingStorage storage = new CountingStorage();
        JiraEventsConnector jc = new JiraEventsConnector(storage, JiraServerFactory.getInstance());
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
    public void testUpdateEvents1() {
        int nb = 3;
        int max = -1;
        int result = testcoreUpdateEvents(nb, max);
        assertTrue(result == 2 * (nb * OCCURENCE));
    }

    @Test
    public void testUpdateEvents2() {

        int nb = 1;
        int max = -1;
        int result = testcoreUpdateEvents(nb, max);
        assertTrue(result == 2 * (nb * OCCURENCE));
    }

    @Test
    public void testUpdateEvents3() {

        int nb = 3;
        int max = 20;
        int result = testcoreUpdateEvents(nb, max);
        assertTrue(result == 2 * (nb * OCCURENCE));
    }

    @Test
    public void testUpdateEvents4() {

        int nb = 1;
        int max = 20;
        int result = testcoreUpdateEvents(nb, max);
        assertTrue(result == 2 * (nb * OCCURENCE));
    }

    @Test
    public void testUpdateEvents5() {

        int nb = 5;
        int max = 16;
        int result = testcoreUpdateEvents(nb, max);
        assertTrue(result == 2 * max);
    }

    @Test
    public void testUpdateEvents6() {

        int nb = 1;
        int max = 2;
        int result = testcoreUpdateEvents(nb, max);
        assertTrue(result == 2 * max);
    }

    private int testcoreUpdateEvents(final int nb, final int max) {

        try {
            JiraConfiguration jconf = new JiraConfiguration("https://jira.atlassian.com/", null, null);
            // initialisation
            Date since = DATE_FORMAT.parse(DATE);

            // Mock de jiraClient
            JiraClient mockJiraClient = Mockito.mock(JiraClient.class);
            JiraServerFactory mockFactory = Mockito.mock(JiraServerFactory.class);
            JiraServerContext jcontext = new JiraServerContext(mockJiraClient);
            Mockito.when(mockFactory.getNewJiraServerContext(jconf)).thenReturn(jcontext);

            CountingStorage storage = new CountingStorage();

            JiraEventsConnector jc = new JiraEventsConnector(storage, mockFactory);

            // ----------------
            // Maitrise des valeurs retourner par le mock de jira client
            createOccurence(mockJiraClient, nb, max);
            jc.setOccurence(OCCURENCE);
            if (max == -1) {
                jc.push(jconf, since);
            } else {
                jc.push(jconf, since, max);
            }

            // verification
            int countEventsNew = storage.counters.get(JiraEventsConnector.EVENT_NEW_BUG);
            int countEventsUpdate = storage.counters.get(JiraEventsConnector.EVENT_UPDATE_BUG);
            return countEventsNew + countEventsUpdate;
        } catch (ParseException | BadConfigurationException | JiraException ex) {
            Logger.getLogger(JiraEventsConnectorTests.class.getName()).log(Level.SEVERE, null, ex);
            return -1;

        }
    }

    private void createOccurence(final JiraClient mockJiraClient, final int nombre, final int max) throws JiraException, ParseException {

        int total = nombre * OCCURENCE;

        if (max != -1 && max < OCCURENCE) {
            Issue.SearchResult rs = new Issue.SearchResult();
            rs.issues = createIssues(max, 0);
            rs.start = 0;
            rs.max = max;
            rs.total = total;
            createReturnIssue(mockJiraClient, max, 0, rs);
        } else if (max != -1 && max < total) {

            for (int i = 0; i < nombre; i++) {

                if (max < (i + 1) * OCCURENCE) {
                    int valLIm = max - (i * OCCURENCE);

                    Issue.SearchResult rs = new Issue.SearchResult();
                    rs.issues = createIssues(valLIm, i * OCCURENCE);
                    rs.start = i * OCCURENCE;
                    rs.max = valLIm;
                    rs.total = total;
                    createReturnIssue(mockJiraClient, valLIm, i * OCCURENCE, rs);
                    break;

                } else {
                    Issue.SearchResult rs = new Issue.SearchResult();
                    rs.issues = createIssues(OCCURENCE, i * OCCURENCE);
                    rs.start = i * OCCURENCE;
                    rs.max = OCCURENCE;
                    rs.total = total;
                    createReturnIssue(mockJiraClient, OCCURENCE, i * OCCURENCE, rs);
                }
            }

        } else {
            for (int i = 0; i < nombre; i++) {
                Issue.SearchResult rs = new Issue.SearchResult();
                rs.issues = createIssues(OCCURENCE, i * OCCURENCE);
                rs.start = i * OCCURENCE;
                rs.max = OCCURENCE;
                rs.total = total;
                createReturnIssue(mockJiraClient, OCCURENCE, i * OCCURENCE, rs);
            }
        }

    }

    private void createReturnIssue(final JiraClient mockJiraClient, int occurence, int start, Issue.SearchResult rs) throws ParseException, JiraException {
        Date since = DATE_FORMAT.parse(DATE);
        String jql1 = "created > \"" + JiraServerContext.FORMATTER.format(since) + "\"";
        Mockito.when(mockJiraClient.searchIssues(jql1, null, occurence, start)).thenReturn(rs);
        String jql2 = "updated > \"" + JiraServerContext.FORMATTER.format(since) + "\"";
        Mockito.when(mockJiraClient.searchIssues(jql2, null, occurence, start)).thenReturn(rs);
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
