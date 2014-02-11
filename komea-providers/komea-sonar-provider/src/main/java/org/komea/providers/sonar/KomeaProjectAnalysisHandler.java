
package org.komea.providers.sonar;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.Provider;
import org.komea.product.rest.client.RestClientFactory;
import org.komea.product.rest.client.api.IEventsAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.events.ProjectAnalysisHandler;
import org.sonar.api.config.Settings;
import org.sonar.api.platform.Server;
import org.sonar.api.resources.Project;

/**
 * KomeaProjectAnalysisHandler.java (UTF-8)
 * 28 oct. 2013
 * 
 * @author scarreau
 */
public class KomeaProjectAnalysisHandler implements ProjectAnalysisHandler {
    
    private static final Logger        LOGGER     = LoggerFactory.getLogger(KomeaProjectAnalysisHandler.class.getName());
    private final Map<Integer, Long>   map        = new HashMap<Integer, Long>(0);
    private final Settings             settings;
    private final Provider             provider;
    private final Map<Integer, String> projectMap = new HashMap<Integer, String>(0);
    
    public KomeaProjectAnalysisHandler(final Server server, final Settings settings) {
    
        this.settings = settings;
        provider = KomeaPlugin.getProvider(server.getURL());
    }
    
    @Override
    public void onProjectAnalysis(final ProjectAnalysisEvent event) {
    
        try {
            final String komeaUrl = KomeaPlugin.getServerUrl(settings);
            if (komeaUrl == null) {
                return;
            }
            final IEventsAPI eventsAPI = RestClientFactory.INSTANCE.createEventsAPI(komeaUrl);
            final int projectId = event.getProject().getId();
            final String projectKey = getCiFlowProjectKey(event.getProject());
            if (event.isStart()) {
                final long start = new Date().getTime();
                map.put(projectId, start);
                eventsAPI.pushEvent(createStartEvent(projectKey, start));
            } else if (event.isEnd()) {
                final long start = map.get(projectId);
                final long end = new Date().getTime();
                eventsAPI.pushEvent(createEndEvent(projectKey, end));
                eventsAPI.pushEvent(createDurationEvent(projectKey, start, end));
            }
        } catch (Exception ex) {
            LOGGER.warn(ex.getMessage(), ex);
        }
    }
    
    private String getCiFlowProjectKey(final Project project) {
    
        final int projectId = project.getId();
        String ciFlowProjectKey;
        if (projectMap.containsKey(projectId)) {
            ciFlowProjectKey = projectMap.get(projectId);
        } else {
            ciFlowProjectKey = KomeaPlugin.getProjectKey(settings);
            if (ciFlowProjectKey == null || ciFlowProjectKey.isEmpty()) {
                ciFlowProjectKey = project.getKey();
            }
            projectMap.put(projectId, ciFlowProjectKey);
        }
        return ciFlowProjectKey;
    }
    
    private EventSimpleDto createStartEvent(final String projectKey, final long start) {
    
        final String message = "Analysis of " + projectKey + " started.";
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(start));
        properties.put("project", projectKey);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaPlugin.EVENT_ANALYSIS_STARTED.getEventKey());
        event.setMessage(message);
        event.setPersonGroup(null);
        event.setPersons(null);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(provider.getUrl());
        event.setUrl(null);
        event.setValue(event.getDate().getTime());
        return event;
    }
    
    private EventSimpleDto createDurationEvent(final String projectKey, final long start, final long end) {
    
        final long duration = end - start;
        final String message = "Analysis of " + projectKey + " done in : " + duration + "ms";
        final Map<String, String> properties = new HashMap<String, String>(3);
        properties.put("start", String.valueOf(start));
        properties.put("end", String.valueOf(end));
        properties.put("duration", String.valueOf(duration));
        properties.put("project", projectKey);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaPlugin.EVENT_ANALYSIS_DURATION.getEventKey());
        event.setMessage(message);
        event.setPersonGroup(null);
        event.setPersons(null);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(provider.getUrl());
        event.setUrl(null);
        event.setValue(duration);
        return event;
    }
    
    private EventSimpleDto createEndEvent(final String projectKey, final long end) {
    
        final String message = "Analysis of " + projectKey + " ended.";
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(end));
        properties.put("project", projectKey);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaPlugin.EVENT_ANALYSIS_ENDED.getEventKey());
        event.setMessage(message);
        event.setPersonGroup(null);
        event.setPersons(null);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(provider.getUrl());
        event.setUrl(null);
        event.setValue(event.getDate().getTime());
        return event;
    }
    
}
