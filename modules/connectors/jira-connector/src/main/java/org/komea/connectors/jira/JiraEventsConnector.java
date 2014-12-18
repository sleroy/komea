
package org.komea.connectors.jira;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraException;

import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.utils.JiraServerContext;
import org.komea.event.model.beans.ComplexEvent;
import org.komea.event.storage.IEventStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class JiraEventsConnector
{
    
    private final static Logger LOGGER           = LoggerFactory.getLogger(JiraEventsConnector.class);
    
    public static final String PROVIDER_BUG     = "jira";
    
    public static final String EVENT_UPDATE_BUG = "issue_update";
    
    public static final String EVENT_NEW_BUG    = "issue_new";
    
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
                    
                    Issue.SearchResult parcourIssues = this.jira.getClient().searchIssues("created > \"" + format + "\"", null,
                            JiraServerContext.GetOccurence, i);
                    sendIssues(parcourIssues.issues);
                }
            }
        } catch (JiraException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
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
    
        sendEvent(issues, EVENT_NEW_BUG);
    }
    
    private void sendUpdateIssue(final List<Issue> issues) {
    
        sendEvent(issues, EVENT_UPDATE_BUG);
    }
    
    private void sendEvent(final List<Issue> issues, final String eventName) {
    
        ComplexEvent complexEventDto;
        for (Issue sue : issues) {
            
            complexEventDto = createBugEvent(sue, eventName);
            this.storage.storeComplexEvent(complexEventDto);
        }
    }
    
    @SuppressWarnings("unchecked")
    private ComplexEvent createBugEvent(final Issue bug, final String eventName) {
    
        final ComplexEvent complexEventDto = new ComplexEvent();
        complexEventDto.setProvider(PROVIDER_BUG);
        complexEventDto.setEventType(eventName);
        Map<String, Serializable> properties = Maps.newHashMap();
        
        try {
            java.lang.reflect.Field declaredField = Issue.class.getDeclaredField("fields");
            declaredField.setAccessible(true);
            properties = (Map<String, Serializable>) declaredField.get(bug);
        } catch (NoSuchFieldException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (SecurityException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (IllegalArgumentException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } catch (IllegalAccessException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        
        complexEventDto.setProperties(properties);
        return complexEventDto;
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
