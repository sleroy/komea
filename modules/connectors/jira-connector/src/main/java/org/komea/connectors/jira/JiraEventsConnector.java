
package org.komea.connectors.jira;


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
    
    private final IEventStorage      storage;
    
    private final IJiraServerFactory jiraServerFactory;
    private JiraServerContext        jira;
    
    public JiraEventsConnector(final IEventStorage storage, final IJiraServerFactory jiraServerFactory) {
    
        super();
        this.storage = storage;
        this.jiraServerFactory = jiraServerFactory;
        storage.declareEventType(EVENT_NEW_BUG);
        storage.clearEventsOfType(EVENT_UPDATE_BUG);
    }
    
    public void push(final JiraConfiguration configuration, final Date since) throws BadConfigurationException {
    
        this.jira = this.jiraServerFactory.getNewJiraServerContext(configuration);
        importNewIssue(since, -1);
       // importUpdateIssue(since, -1);
    }
    
    public void push(final JiraConfiguration configuration, final Date since, final int max) throws BadConfigurationException {
    
        this.jira = this.jiraServerFactory.getNewJiraServerContext(configuration);
        importNewIssue(since, max);
       // importUpdateIssue(since, max);
    }
    
    private void importNewIssue(final Date date, final int max) {
    
        try {
            String format = JiraServerContext.FORMATTER.format(date);
            
            int limit = max >= 0 ? max : JiraServerContext.GetOccurence;
            String jql = "created > \"" + format + "\"";
            Issue.SearchResult searchIssues = this.jira.getClient().searchIssues(jql, getJiraIssueSelectedFields(), limit, 0);
            sendIssues(searchIssues.issues);
            if (max == -1 || searchIssues.total < max) {
                if (searchIssues.total > searchIssues.max) {
                    
                    for (int i = searchIssues.max; i < searchIssues.total; i = i + searchIssues.max) {
                        
                        searchIssues = this.jira.getClient().searchIssues(jql, getJiraIssueSelectedFields(), limit, i);
                        
                        sendIssues(searchIssues.issues);
                        log(searchIssues);
                    }
                }
            }
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
            int limit = max >= 0 ? max : JiraServerContext.GetOccurence;
            String format = JiraServerContext.FORMATTER.format(date);
            Issue.SearchResult searchIssues = this.jira.getClient().searchIssues("updated > \"" + format + "\"", null, limit, 0);
            sendUpdateIssue(searchIssues.issues);
            
            if (max == -1 || searchIssues.total < max) {
                if (searchIssues.total > searchIssues.max) {
                    for (int i = searchIssues.max; i < searchIssues.total; i = i + searchIssues.max) {
                        
                        Issue.SearchResult parcourIssues = this.jira.getClient().searchIssues("updated > \"" + format + "\"", null, limit,
                                i);
                        sendUpdateIssue(parcourIssues.issues);
                    }
                }
            }
            
        } catch (JiraException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
    
    private void sendIssues(final List<Issue> issues) {
    
        for (Issue issue : issues) {
            sendEvent(issue, EVENT_NEW_BUG);
        }
        
    }
    
    private void sendUpdateIssue(final List<Issue> issues) {
    
        for (Issue issue : issues) {
            sendEvent(issue, EVENT_UPDATE_BUG);
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
