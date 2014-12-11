/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rcarz.jiraclient.Component;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.IssueType;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.Priority;
import net.rcarz.jiraclient.Project;
import net.rcarz.jiraclient.WorkLog;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;
import net.rcarz.jiraclient.greenhopper.RapidView;
import net.rcarz.jiraclient.greenhopper.Sprint;

/**
 *
 * @author rgalerme
 */
public class JiraServerAPITest {

    public void test() throws ParseException {
        try {
            //    Issue is = new Issue();
//                BasicCredentials creds = new BasicCredentials("batman", "pow! pow!");
            JiraClient jira = new JiraClient("https://jira.atlassian.com/");
            GreenHopperClient gh = new GreenHopperClient(jira);
           
            Issue issue = jira.getIssue("SRCTREE-725");
            List<Component> componentds = issue.getComponents();
            
            try {
                java.lang.reflect.Field declaredField = Issue.class.getDeclaredField("fields");
                declaredField.setAccessible(true);
                Object get = declaredField.get(issue);
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(JiraServerAPITest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException ex) {
                Logger.getLogger(JiraServerAPITest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(JiraServerAPITest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(JiraServerAPITest.class.getName()).log(Level.SEVERE, null, ex);
            }
            
             List<RapidView> rapidViews = gh.getRapidViews();
                java.lang.reflect.Field[] fields = issue.getClass().getFields();
         Issue.class.getDeclaredMethods();
            Map<String, Object> map = Field.getMap(String.class, Object.class, issue);
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            System.out.println("-------------- begin --------------");
            for (Map.Entry<String, Object> entrySet1 : entrySet) {
                System.out.println("entrySet1.getKey()"); 
            }
            issue.getField("created");
            List<WorkLog> allWorkLogs = issue.getAllWorkLogs();
            for (WorkLog allWorkLog : allWorkLogs) {
                String comment = allWorkLog.getComment();
                Date createdDate = allWorkLog.getCreatedDate();
                Date updatedDate = allWorkLog.getUpdatedDate();
                System.out.println("ok");
            }

//            Object field = issue.getField(Field.PRIORITY);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            Date madate = formatter.parse("2014/12/4 15:10");
            
            String format = formatter.format(madate);
            
            List<Project> projects = jira.getProjects();
            for (Project project : projects) {
                
                List<Component> components = project.getComponents();
                for (Component component : components) {
                    System.out.println(component.getName());
                    component.getDescription();
                   
                }
            }
            
            List<Priority> priorities = jira.getPriorities();
            for (Priority priority : priorities) {
                String name = priority.getName();
            }
            
            List<IssueType> issueTypes = jira.getIssueTypes();
            for (IssueType sueType : issueTypes) {
                String name = sueType.getName();
            }
//            Issue.SearchResult searchIssuess = jira.searchIssues("created > \"2014/12/04\"");
//            Issue.SearchResult searchIssues = jira.searchIssues("created > \""+format+"\"", Integer.MAX_VALUE);
            

//            Priority priority = issue.getPriority();
//            issue.getAssignee();
            RapidView next = rapidViews.iterator().next();
            next.getSprints();
                
                
           
//
//                Backlog backlogData = rapidView.getBacklogData();
//                List<SprintIssue> issues = backlogData.getIssues();
//                for (SprintIssue sue : issues) {
//                    sue.getPriorityName();
//                    System.out.println("sprint issue : " + sue);
//                }
//            }

      

        } catch (JiraException ex) {
            Logger.getLogger(JiraServerAPITest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
