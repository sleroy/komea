/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.jiraconnector.core;

import com.google.common.collect.Iterators;
import com.tocea.jiraconnector.generalplugin.BadConfigurationException;
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.event.query.impl.EventQueryManager;
import org.mockito.Mockito;

/**
 *
 * @author rgalerme
 */
public class JiraConnectorTest {

    public JiraConnectorTest() {
    }
    private JiraConfiguration jconf;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private String format = "2014/12/16 11:00";
    public static final int OCCURENCE = 5;

    @Before
    public void setUp() {

//        jconf = new JiraConfiguration("https://jira.mongodb.org/", "pierredutonerre", "pierre");
        jconf = new JiraConfiguration("https://jira.atlassian.com/", null, null);
    }

    private List<Issue> createIssues(int nb, int start) {
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

    private void createOccurence(JiraClient mockJiraClient, int nombre) throws JiraException {
        int total = nombre * OCCURENCE;

        for (int i = 0; i < nombre; i++) {
            Issue.SearchResult rs = new Issue.SearchResult();
            rs.issues = createIssues(OCCURENCE, i * OCCURENCE);
            rs.start = i * OCCURENCE;
            rs.max = OCCURENCE;
            rs.total = total;

//            Mockito.when(mockJiraClient.searchIssues("created > \"" + format + "\"", OCCURENCE)).thenReturn(rs);
            Mockito.when(mockJiraClient.searchIssues("created > \"" + format + "\"", null, OCCURENCE, i * OCCURENCE)).thenReturn(rs);
            Mockito.when(mockJiraClient.searchIssues("updated > \"" + format + "\"", null, OCCURENCE, i * OCCURENCE)).thenReturn(rs);
        }

    }

    @Test
    public void testUpdateEvents() {
        testcoreUpdateEvents(3);
    }

    private void testcoreUpdateEvents(int nb) {
        try {
            //initialisation
            Date parse = dateFormat.parse(format);
            JiraPluginConnector jc = new JiraPluginConnector();
            //Mock de jiraClient
            JiraClient mockJiraClient = Mockito.mock(JiraClient.class);
            JiraConnectorFactory mockFactory = Mockito.mock(JiraConnectorFactory.class);
            JiraServerContext jcontext = new JiraServerContext(mockJiraClient);
            Mockito.when(mockFactory.getNewJiraServerContext(jconf)).thenReturn(jcontext);
            jc.setFactory(mockFactory);
            // ----------------
            //Maitrise des valeurs retourner par le mock de jira client
            createOccurence(mockJiraClient, nb);
            // -----------------
            jc.initConnector(jconf);
            jc.setOccurence(OCCURENCE);
            
            //execution
            jc.updateEvents(parse);

            
            //verification
            EventQueryManager queryService = jc.getKomeaContext().getQueryService();
            long countEventsOfType = queryService.countEventsOfType(KomeaServerContext.EVENT_NEW_BUG);
            assertTrue(countEventsOfType == nb*OCCURENCE);
        } catch (ParseException | BadConfigurationException | JiraException ex) {
            Logger.getLogger(JiraConnectorTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }
    }

    @Test
    public void testUpdateEventsWay2() {
        testcoreUpdateEvents(1);
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

}
