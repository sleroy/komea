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
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.komea.core.model.IKomeaEntity;
import org.komea.core.model.storage.IKomeaGraphStorage;
import org.komea.core.model.storage.impl.OKomeaGraphStorage;
import org.komea.event.query.impl.EventQueryManager;
import org.komea.software.model.impl.MinimalCompanySchema;

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

        jconf = new JiraConfiguration("https://jira.atlassian.com/", null, null);
    }

    @Test
    public void testUpdateEvents() {
        JiraPluginConnector jc = new JiraPluginConnector();

        try {
            Date parse = dateFormat.parse("2014/11/10 11:00");
            jc.initConnector(jconf);
            jc.updateEvents(parse,50);

            EventQueryManager queryService = jc.getKomeaContext().getQueryService();
            long countEventsOfType = queryService.countEventsOfType(KomeaServerContext.EVENT_NEW_BUG);
            System.out.println(countEventsOfType);
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
        assertTrue(size==38);
    }

}
