/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraException;

/**
 *
 * @author rgalerme
 */
public class JiraService {

    public final static SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    public final static Integer GetOccurence = 1000;

    public static void importNewIssue(JiraServerAPI jiraApi, KomeaServerAPI komeaApi, Date date) {
        try {
            String format = FORMATTER.format(date);
            Issue.SearchResult searchIssues = jiraApi.getClient().searchIssues("created > \"" + format + "\"", GetOccurence);
            KomeaService.sendNewIssue(komeaApi, searchIssues.issues);

            if (searchIssues.total > searchIssues.max) {
                for (int i = searchIssues.max; i < searchIssues.total; i = i + searchIssues.max) {

                    Issue.SearchResult parcourIssues = jiraApi.getClient().searchIssues("created > \"" + format + "\"", null, Integer.MAX_VALUE, i);
                    KomeaService.sendNewIssue(komeaApi, parcourIssues.issues);
                }
            }
        } catch (JiraException ex) {
            Logger.getLogger(JiraService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static void importUpdateIssue(JiraServerAPI jiraApi, KomeaServerAPI komeaApi, Date date) {
        try {

            String format = FORMATTER.format(date);
            Issue.SearchResult searchIssues = jiraApi.getClient().searchIssues("updated > \"" + format + "\"", GetOccurence);
            KomeaService.sendUpdateIssue(komeaApi, searchIssues.issues);

            if (searchIssues.total > searchIssues.max) {
                for (int i = searchIssues.max; i < searchIssues.total; i = i + searchIssues.max) {

                    Issue.SearchResult parcourIssues = jiraApi.getClient().searchIssues("updated > \"" + format + "\"", null, Integer.MAX_VALUE, i);
                    KomeaService.sendUpdateIssue(komeaApi, parcourIssues.issues);
                }
            }

        } catch (JiraException ex) {
            Logger.getLogger(JiraService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
