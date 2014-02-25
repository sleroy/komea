package org.komea.providers.sonar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.events.ProjectAnalysisHandler;
import org.sonar.api.config.Settings;
import org.sonar.api.platform.Server;
import org.sonar.api.resources.Project;

public class KomeaProjectAnalysisHandler implements ProjectAnalysisHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaProjectAnalysisHandler.class.getName());
    private final Map<Integer, Long> map = new HashMap<Integer, Long>(0);
    private final Settings settings;
    private final Provider provider;
    private final String sonarUrl;
    private final Map<Integer, String> projectMap = new HashMap<Integer, String>(0);

    public KomeaProjectAnalysisHandler(final Server server, final Settings settings) {
        this.settings = settings;
        this.sonarUrl = KomeaPlugin.getSonarUrl(settings);
        this.provider = KomeaPlugin.getProvider(sonarUrl);
    }

    @Override
    public void onProjectAnalysis(final ProjectAnalysisEvent event) {
        final String komeaUrl = KomeaPlugin.getKomeaUrl(settings);
        if (komeaUrl == null) {
            return;
        }
        try {
            final int projectId = event.getProject().getId();
            final String projectKey = getCiFlowProjectKey(event.getProject());
            if (event.isStart()) {
                final long start = new Date().getTime();
                map.put(projectId, start);
                KomeaPlugin.pushEvents(sonarUrl, komeaUrl, createStartEvent(
                        projectKey, start, sonarUrl, projectId));
            } else if (event.isEnd()) {
                final long start = map.get(projectId);
                final long end = new Date().getTime();
                KomeaPlugin.pushEvents(sonarUrl, komeaUrl, createCompleteEvent(
                        projectKey, start, end, sonarUrl, projectId));
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
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

    private EventSimpleDto createStartEvent(final String projectKey, final long start,
            final String sonarUrl, final int projectId) {
        final String message = "SonarQube analysis started for poject " + projectKey;
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(start));
        properties.put("project", projectKey);
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaPlugin.ANALYSIS_STARTED.getEventKey());
        event.setMessage(message);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(provider.getUrl());
        event.setUrl(KomeaPlugin.getProjectUrl(sonarUrl, projectId));
        event.setValue(start);
        return event;
    }

    private EventSimpleDto createCompleteEvent(final String projectKey,
            final long start, final long end, final String sonarUrl,
            final int projectId) {
        final long duration = end - start;
        final String message = "SonarQube analysis succeeded for project "
                + projectKey + " in " + duration + "ms";
        final Map<String, String> properties = new HashMap<String, String>(0);
        properties.put("date", String.valueOf(end));
        properties.put("project", projectKey);
        properties.put("duration", String.valueOf(duration));
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(KomeaPlugin.ANALYSIS_SUCCESS.getEventKey());
        event.setMessage(message);
        event.setProject(projectKey);
        event.setProperties(properties);
        event.setProvider(provider.getUrl());
        event.setUrl(KomeaPlugin.getProjectUrl(sonarUrl, projectId));
        event.setValue(event.getDate().getTime());
        return event;
    }

}
