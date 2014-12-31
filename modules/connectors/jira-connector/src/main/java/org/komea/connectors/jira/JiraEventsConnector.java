
package org.komea.connectors.jira;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.Issue.SearchResult;
import net.rcarz.jiraclient.JiraException;

import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.utils.IJiraServerFactory;
import org.komea.connectors.jira.utils.JiraServerContext;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraEventsConnector
{
    
    private final static Logger      LOGGER           = LoggerFactory.getLogger(JiraEventsConnector.class);
    
    public static final String       JIRA             = "jira";
    
    public static final String       EVENT_UPDATE_BUG = "issue_update";
    
    public static final String       EVENT_NEW_BUG    = "issue_new";
    
    public static final String SELECT_FIELDS = "issuetype,status,assignee,resolution,project,created,updated,creator";

    private final IEventStorage storage;
    private int Occurence;

    private final IJiraServerFactory jiraServerFactory;
    private JiraServerContext        jira;
    
    public JiraEventsConnector(final IEventStorage storage, final IJiraServerFactory jiraServerFactory) {
    
        super();
        this.storage = storage;
        this.Occurence = JiraServerContext.GetOccurence;
        this.jiraServerFactory = jiraServerFactory;
	 storage.declareEventType(EVENT_NEW_BUG);
        storage.clearEventsOfType(EVENT_UPDATE_BUG);
        
    }

    public void setOccurence(int Occurence) {
        this.Occurence = Occurence;
    }

    public void push(final JiraConfiguration configuration, final Date since) throws BadConfigurationException {
    
        this.jira = this.jiraServerFactory.getNewJiraServerContext(configuration);
        importNewIssue(since, -1);
        importUpdateIssue(since, -1);
    }
    
    public void push(final JiraConfiguration configuration, final Date since, final int max) throws BadConfigurationException {
    
        this.jira = this.jiraServerFactory.getNewJiraServerContext(configuration);
        importNewIssue(since, max);
        importUpdateIssue(since, max);
    }
    
     private void importNewIssue(final Date date, final int max) {

        try {
            String type = EVENT_NEW_BUG;
            String jql = "created > \"" + JiraServerContext.FORMATTER.format(date) + "\"";
            getAndSendIssues(type, jql, max);
        } catch (JiraException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void log(final SearchResult searchIssues) {

        double percentage = ((double) searchIssues.start + searchIssues.max) / (searchIssues.total) * 100;

        LOGGER.info("Processing done at {}%", (int) percentage);

    }

    private void importUpdateIssue(final Date date, final int max) {

        try {
            String type = EVENT_UPDATE_BUG;
            String jql = "updated > \"" + JiraServerContext.FORMATTER.format(date) + "\"";
            getAndSendIssues(type, jql, max);

        } catch (JiraException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void getAndSendIssues(String type, String jql, int max) throws JiraException {

        int limit = (max >= 0 && max < this.Occurence) ? max : this.Occurence;

        Issue.SearchResult searchIssues = this.jira.getClient().searchIssues(jql, SELECT_FIELDS, limit, 0);
        sendIssues(searchIssues.issues, type);
        for (int i = searchIssues.max; i < searchIssues.total && (i < max || max == -1); i = i + searchIssues.max) {
            if (max != -1 && i + searchIssues.max > max) {
                limit = max - i;
            }
            Issue.SearchResult parcourIssues = this.jira.getClient().searchIssues(jql, SELECT_FIELDS, limit, i);
            sendIssues(parcourIssues.issues, type);
            log(searchIssues);
        }
    }

    private void sendIssues(final List<Issue> issues, String type) {

        for (Issue issue : issues) {
            
            sendEvent(issue, type);
        }

    }

    private void sendEvent(final Issue bug, final String eventName) {

       final ComplexEvent event = new ComplexEvent();
        event.setProvider(JIRA);
        event.setEventType(eventName);
        event.setDate(Field.getDate(bug.getField("created")));
        
        event.setProperties(bug.getFields());
        this.storage.storeComplexEvent(event);
    }

    private String getJiraIssueSelectedFields() {
    
        return "id,key,created,issuetype,priority,assignee,reporter,description,project,status,versions,resolution";
    }

}
