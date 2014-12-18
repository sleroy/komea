
package org.komea.connectors.jira;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.Issue.SearchResult;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.Version;

import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.utils.JiraServerContext;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JiraEventsConnector
{
    
    private final static Logger LOGGER           = LoggerFactory.getLogger(JiraEventsConnector.class);
    
    public static final String  JIRA             = "jira";
    
    public static final String  EVENT_UPDATE_BUG = "issue_update";
    
    public static final String  EVENT_NEW_BUG    = "issue_new";
    
    private final IEventStorage storage;
    
    private JiraServerContext   jira;
    
    public JiraEventsConnector(final IEventStorage storage) {
    
        super();
        this.storage = storage;
        
    }
    
    public void push(final JiraConfiguration configuration, final Date since) throws BadConfigurationException {
    
        this.jira = new JiraServerContext(configuration);
        importNewIssue(since);
        importUpdateIssue(since);
    }
    
    public void push(final JiraConfiguration configuration, final Date since, final int max) throws BadConfigurationException {
    
        this.jira = new JiraServerContext(configuration);
        importNewIssueLimit(since, max);
        importUpdateIssueLimit(since, max);
    }
    
    private void importNewIssue(final Date date) {
    
        try {
            String format = JiraServerContext.FORMATTER.format(date);
            Issue.SearchResult searchIssues = this.jira.getClient().searchIssues("created > \"" + format + "\"",
                    JiraServerContext.GetOccurence);
            sendIssues(searchIssues.issues);
            
            if (searchIssues.total > searchIssues.max) {
                
                for (int i = searchIssues.max; i < searchIssues.total; i = i + searchIssues.max) {
                    
                    searchIssues = this.jira.getClient().searchIssues("created > \"" + format + "\"", null, JiraServerContext.GetOccurence,
                            i);
                    
                    sendIssues(searchIssues.issues);
                    log(searchIssues);
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
    
    private void importUpdateIssue(final Date date) {
    
        try {
            
            String format = JiraServerContext.FORMATTER.format(date);
            Issue.SearchResult searchIssues = this.jira.getClient().searchIssues("updated > \"" + format + "\"",
                    JiraServerContext.GetOccurence);
            sendUpdateIssue(searchIssues.issues);
            
            if (searchIssues.total > searchIssues.max) {
                for (int i = searchIssues.max; i < searchIssues.total; i = i + searchIssues.max) {
                    
                    Issue.SearchResult parcourIssues = this.jira.getClient().searchIssues("updated > \"" + format + "\"", null,
                            JiraServerContext.GetOccurence, i);
                    sendUpdateIssue(parcourIssues.issues);
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
        
        event.addField("id", bug.getId());
        event.addField("key", bug.getKey());

     
        event.addField("description", bug.getDescription());
        if(bug.getProject()!=null){
            event.addField("project", bug.getProject().getName());
        }
        if (bug.getStatus() != null) {
            event.addField("status", bug.getStatus().getName());
        }
        if (bug.getIssueType() != null) {
            event.addField("type", bug.getIssueType().getName());
        }
        this.storage.storeComplexEvent(event);
    }
    
    /**
     * @param _date
     * @param limit
     *            must be lower that 1000
     */
    private void importNewIssueLimit(final Date _date, final int limit) {
    
        try {
            String format = JiraServerContext.FORMATTER.format(_date);
            Issue.SearchResult searchIssues = this.jira.getClient().searchIssues("created > \"" + format + "\"", limit);
            sendIssues(searchIssues.issues);
        } catch (JiraException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
    
    /**
     * @param _date
     * @param limit
     *            must be lower that 1000
     */
    private void importUpdateIssueLimit(final Date _date, final int limit) {
    
        try {
            String format = JiraServerContext.FORMATTER.format(_date);
            Issue.SearchResult searchIssues = this.jira.getClient().searchIssues("updated > \"" + format + "\"", limit);
            sendUpdateIssue(searchIssues.issues);
        } catch (JiraException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}
