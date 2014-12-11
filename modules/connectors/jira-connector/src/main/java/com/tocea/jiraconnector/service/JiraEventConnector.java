/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.jiraconnector.service;

import com.google.common.collect.Maps;
import com.tocea.jiraconnector.api.JiraServerAPITest;
import com.tocea.jiraconnector.core.JiraServerContext;
import com.tocea.jiraconnector.core.KomeaServerContext;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraException;
import org.komea.event.model.beans.ComplexEvent;

/**
 *
 * @author rgalerme
 */
public class JiraEventConnector {

    private final JiraServerContext jiraContext;
    private final KomeaServerContext komeaContext;

    public JiraEventConnector(JiraServerContext jiraContext, KomeaServerContext komeaContext) {
        this.jiraContext = jiraContext;
        this.komeaContext = komeaContext;
    }

    
    public void importNewIssue(Date date) {
        try {
            String format = JiraServerContext.FORMATTER.format(date);
            Issue.SearchResult searchIssues = jiraContext.getClient().searchIssues("created > \"" + format + "\"", JiraServerContext.GetOccurence);
            sendNewIssue(searchIssues.issues);

            if (searchIssues.total > searchIssues.max) {
                for (int i = searchIssues.max; i < searchIssues.total; i = i + searchIssues.max) {

                    Issue.SearchResult parcourIssues = jiraContext.getClient().searchIssues("created > \"" + format + "\"", null, Integer.MAX_VALUE, i);
                    sendNewIssue(parcourIssues.issues);
                }
            }
        } catch (JiraException ex) {
            Logger.getLogger(JiraEventConnector.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void importUpdateIssue(Date date) {
        try {

            String format = JiraServerContext.FORMATTER.format(date);
            Issue.SearchResult searchIssues = jiraContext.getClient().searchIssues("updated > \"" + format + "\"", JiraServerContext.GetOccurence);
            sendUpdateIssue(searchIssues.issues);

            if (searchIssues.total > searchIssues.max) {
                for (int i = searchIssues.max; i < searchIssues.total; i = i + searchIssues.max) {

                    Issue.SearchResult parcourIssues = jiraContext.getClient().searchIssues("updated > \"" + format + "\"", null, Integer.MAX_VALUE, i);
                    sendUpdateIssue(parcourIssues.issues);
                }
            }

        } catch (JiraException ex) {
            Logger.getLogger(JiraEventConnector.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    private void sendNewIssue(List<Issue> issues) {

        sendEvent(issues, KomeaServerContext.EVENT_NEW_BUG);
    }

    private void sendUpdateIssue(List<Issue> issues) {
        sendEvent(issues, KomeaServerContext.EVENT_UPDATE_BUG);
    }

    private void sendEvent(List<Issue> issues, String eventName) {
        ComplexEvent complexEventDto;
        for (Issue sue : issues) {

            complexEventDto = createBugEvent(sue, eventName);
            komeaContext.getEventStorage().storeComplexEvent(complexEventDto);
        }
    }

    private ComplexEvent createBugEvent(final Issue bug, final String eventName) {
        final ComplexEvent complexEventDto = new ComplexEvent();
        complexEventDto.setProvider(KomeaServerContext.PROVIDER_BUG);
        complexEventDto.setEventType(eventName);
        Map<String, Serializable> properties = Maps.newHashMap();

        try {
            java.lang.reflect.Field declaredField = Issue.class.getDeclaredField("fields");
            declaredField.setAccessible(true);
            properties = (Map<String, Serializable>) declaredField.get(bug);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(JiraServerAPITest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(JiraServerAPITest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(JiraServerAPITest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JiraServerAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }

        complexEventDto.setProperties(properties);
        return complexEventDto;
    }
}
