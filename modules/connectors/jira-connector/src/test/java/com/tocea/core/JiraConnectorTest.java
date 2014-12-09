/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.komea.event.query.service.EventQueryManagerService;

/**
 *
 * @author rgalerme
 */
public class JiraConnectorTest {
    
    public JiraConnectorTest() {
    }
    
    @Before
    public void setUp() {
    }

    @Test
    public void testUpdateEvents() {
        JiraConnector jc = new JiraConnector();
        JiraConfiguration jconf = new JiraConfiguration("https://jira.atlassian.com/", null, null);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            Date parse = dateFormat.parse("2014/12/08 11:00");
            jc.initConnector(jconf);
            jc.updateEvents(parse);
            
            EventQueryManagerService queryService = jc.getQueryService();
            long countEventsOfType = queryService.countEventsOfType(KomeaService.EVENT_NEW_BUG);
            System.out.println(countEventsOfType);
            assertTrue(countEventsOfType > 0);
        } catch (ParseException ex) {
            Logger.getLogger(JiraConnectorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    
}
