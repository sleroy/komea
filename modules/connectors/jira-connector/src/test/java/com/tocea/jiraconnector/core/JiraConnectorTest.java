/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.jiraconnector.core;

import com.google.common.collect.Iterators;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.event.query.impl.EventQueryManager;

/**
 *
 * @author rgalerme
 */
public class JiraConnectorTest {

    public JiraConnectorTest() {
    }
    private JiraConfiguration jconf;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    @Before
    public void setUp() {

        jconf = new JiraConfiguration("https://jira.mongodb.org/", "pierredutonerre", "pierre");
//        jconf = new JiraConfiguration("https://jira.atlassian.com/", null, null);
    }

    
    @Test
    public void testUpdateEvents() {
        JiraPluginConnector jc = new JiraPluginConnector();

        try {
            Date parse = dateFormat.parse("2014/11/10 11:00");
            jc.initConnector(jconf);
            jc.updateEvents(parse, 50);

            EventQueryManager queryService = jc.getKomeaContext().getQueryService();
            long countEventsOfType = queryService.countEventsOfType(KomeaServerContext.EVENT_NEW_BUG);
            assertTrue(countEventsOfType == 50);
        } catch (ParseException ex) {
            Logger.getLogger(JiraConnectorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Test
    public void testUpdateProcess() {
        JiraPluginConnector jc = new JiraPluginConnector();
        jc.initConnector(jconf);
        jc.updateOrganisation();

        IKomeaGraphStorage newCompanyStorage = jc.getKomeaContext().getNewCompanyStorage();
        Iterable<IKomeaEntity> entities = newCompanyStorage.entities();
        int size = Iterators.size(entities.iterator());
        assertTrue(size > 0);
    }

    
    @Test
    public void testJiraApi() {
        try {
            Date _date = dateFormat.parse("2014/11/10 11:00");
            String format = JiraServerContext.FORMATTER.format(_date);
            JiraClient jiraClient = new JiraClient(jconf.getUrl(), new BasicCredentials(jconf.getLogin(), jconf.getPass()),true);;
            
            Issue.SearchResult searchIssues = jiraClient.searchIssues("created > \"" + format + "\"", 10);
            assertTrue(searchIssues.issues.size() > 0);

        } catch (ParseException | JiraException ex) {
            Logger.getLogger(JiraConnectorTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        } 

    }

}
