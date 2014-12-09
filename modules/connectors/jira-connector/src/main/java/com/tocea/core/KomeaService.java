/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import com.google.common.collect.Maps;
import java.io.Serializable;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.JiraException;
import static net.rcarz.jiraclient.Resource.getBaseUri;
import net.rcarz.jiraclient.RestClient;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.komea.event.model.api.IComplexEvent;
import org.komea.event.model.beans.ComplexEvent;

/**
 *
 * @author rgalerme
 */
public class KomeaService {

    public static final String EVENT_NEW_BUG = "new_bug";
    public static final String EVENT_UPDATE_BUG = "update_bug";
    public static final String PROVIDER_BUG = "jira";

    public static void sendNewIssue(KomeaServerAPI komeaApi, List<Issue> issues) {

        sendEvent(komeaApi, issues, EVENT_NEW_BUG);
    }

    public static void sendUpdateIssue(KomeaServerAPI komeaApi, List<Issue> issues) {
        sendEvent(komeaApi, issues, EVENT_UPDATE_BUG);
    }

    private static void sendEvent(KomeaServerAPI komeaApi, List<Issue> issues, String eventName) {
        IComplexEvent complexEventDto;
        for (Issue sue : issues) {

            complexEventDto = createBugEvent(sue, eventName);
            komeaApi.getEventStorage().storeComplexEvent(complexEventDto);
        }
    }

    private static IComplexEvent createBugEvent(final Issue bug, final String eventName) {
        final ComplexEvent complexEventDto = new ComplexEvent();
        complexEventDto.setProvider(PROVIDER_BUG);
        complexEventDto.setEventKey(eventName);
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
