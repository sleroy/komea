/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tocea.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.rcarz.jiraclient.Issue;

import org.komea.event.model.beans.ComplexEvent;

import com.google.common.collect.Maps;

/**
 *
 * @author rgalerme
 */
public class KomeaService {

	public static void sendNewIssue(final KomeaServerAPI komeaApi, final List<Issue> issues) {

		sendEvent(komeaApi, issues, EVENT_NEW_BUG);
	}

	public static void sendUpdateIssue(final KomeaServerAPI komeaApi, final List<Issue> issues) {
		sendEvent(komeaApi, issues, EVENT_UPDATE_BUG);
	}

	private static ComplexEvent createBugEvent(final Issue bug, final String eventName) {
		final ComplexEvent complexEventDto = new ComplexEvent();
		complexEventDto.setProvider(PROVIDER_BUG);
		complexEventDto.setEventType(eventName);
		Map<String, Serializable> properties = Maps.newHashMap();

		try {
			final java.lang.reflect.Field declaredField = Issue.class.getDeclaredField("fields");
			declaredField.setAccessible(true);
			properties = (Map<String, Serializable>) declaredField.get(bug);
		} catch (final NoSuchFieldException ex) {
			Logger.getLogger(KomeaService.class.getName()).log(Level.SEVERE, null, ex);
		} catch (final SecurityException ex) {
			Logger.getLogger(KomeaService.class.getName()).log(Level.SEVERE, null, ex);
		} catch (final IllegalArgumentException ex) {
			Logger.getLogger(KomeaService.class.getName()).log(Level.SEVERE, null, ex);
		} catch (final IllegalAccessException ex) {
			Logger.getLogger(KomeaService.class.getName()).log(Level.SEVERE, null, ex);
		}

		complexEventDto.setProperties(properties);
		return complexEventDto;
	}

	private static void sendEvent(final KomeaServerAPI komeaApi, final List<Issue> issues, final String eventName) {
		ComplexEvent complexEventDto;
		for (final Issue sue : issues) {

			complexEventDto = createBugEvent(sue, eventName);
			komeaApi.getEventStorage().storeComplexEvent(complexEventDto);
		}
	}

	public static final String	EVENT_NEW_BUG	 = "new_bug";

	public static final String	EVENT_UPDATE_BUG	= "update_bug";

	public static final String	PROVIDER_BUG	 = "jira";

}
