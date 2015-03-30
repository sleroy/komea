/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.connectors.jira.utils;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.ICredentials;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.RestClient;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.komea.connectors.jira.IJiraConfiguration;
import org.komea.connectors.jira.exceptions.BadConfigurationException;
import org.komea.connectors.jira.impl.JiraConfiguration;

/**
 *
 * @author rgalerme
 */
public class JiraServerFactoryTest {

    private IJiraServerFactory factory;

    public JiraServerFactoryTest() {

    }

    @Before
    public void setUp() {
        factory = JiraServerFactory.getInstance();
    }

    @Test
    public void testGetNewJiraServerContext() {
        String url = "https://jira.mongodb.org/";
        String login = "pierredutonerre";
        String pass = "pierre";

        IJiraConfiguration jconf0 = new JiraConfiguration(null, null, null);
        IJiraConfiguration jconf1 = new JiraConfiguration("", null, null);
        IJiraConfiguration jconf2 = new JiraConfiguration(url, null, null);
        IJiraConfiguration jconf3 = new JiraConfiguration(url, "", "");
        IJiraConfiguration jconf4 = new JiraConfiguration(url, null, "");
        IJiraConfiguration jconf5 = new JiraConfiguration(url, "", null);
        IJiraConfiguration jconf6 = new JiraConfiguration(url, login, pass);
        IJiraConfiguration jconf7 = new JiraConfiguration(url, login, null);
        IJiraConfiguration jconf8 = new JiraConfiguration(url, null, pass);
        boolean tempvalid = false;
        try {
            factory.getNewJiraServerContext(jconf0);
        } catch (BadConfigurationException ex) {
            tempvalid = true;
        }
        assertTrue(tempvalid);
        tempvalid = false;
        try {
            factory.getNewJiraServerContext(jconf1);
        } catch (BadConfigurationException ex) {
            tempvalid = true;
        }
        assertTrue(tempvalid);

        try {
            JiraServerContext newJiraServerContext = factory.getNewJiraServerContext(jconf2);
            ICredentials creds = getCreds(newJiraServerContext);
            assertNull(creds);

            newJiraServerContext = factory.getNewJiraServerContext(jconf3);
            creds = getCreds(newJiraServerContext);
            assertNull(creds);
            
            newJiraServerContext = factory.getNewJiraServerContext(jconf4);
            creds = getCreds(newJiraServerContext);
            assertNull(creds);
            
            newJiraServerContext = factory.getNewJiraServerContext(jconf5);
            creds = getCreds(newJiraServerContext);
            assertNull(creds);
            
            newJiraServerContext = factory.getNewJiraServerContext(jconf6);
            creds = getCreds(newJiraServerContext);
            assertNotNull(creds);
            assertEquals(creds.getLogonName(), login);
            
            newJiraServerContext = factory.getNewJiraServerContext(jconf7);
            creds = getCreds(newJiraServerContext);
            assertNull(creds);
            
            newJiraServerContext = factory.getNewJiraServerContext(jconf8);
            creds = getCreds(newJiraServerContext);
            assertNull(creds);
            
        } catch (BadConfigurationException ex) {
            Logger.getLogger(JiraServerFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
            fail();
        }

    }

    private ICredentials getCreds(JiraServerContext jiraContext) {
        ICredentials result = null;
        try {
            Field field = RestClient.class.getDeclaredField("creds");
            field.setAccessible(true);
            result = (ICredentials) field.get(jiraContext.getClient().getRestClient());
//            field.
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(JiraServerFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(JiraServerFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(JiraServerFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}
