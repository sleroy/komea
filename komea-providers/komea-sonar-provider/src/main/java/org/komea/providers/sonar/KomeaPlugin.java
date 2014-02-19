package org.komea.providers.sonar;

import java.util.Arrays;
import java.util.List;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EventCategory;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.komea.product.rest.client.RestClientFactory;
import org.komea.product.rest.client.api.IEventsAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Metric;

@Properties({
    @Property(
            key = KomeaPlugin.SERVER_URL_KEY,
            name = "Komea Server Url",
            description = "Komea Server Url",
            project = false,
            global = true),
    @Property(
            key = KomeaPlugin.METRICS_KEY,
            name = "Metric keys",
            description = "Metric keys",
            project = false,
            global = true),
    @Property(
            key = KomeaPlugin.PROJECT_KEY,
            name = "Komea Project key",
            description = "Komea Project key",
            project = true,
            global = false)})
public class KomeaPlugin extends SonarPlugin {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaPlugin.class.getName());
    public static final String SERVER_URL_KEY = "komea.serverUrl";
    public static final String METRICS_KEY = "komea.metrics";
    public static final String PROJECT_KEY = "komea.project";
    public static final EventType ANALYSIS_STARTED = createEventType(
            "analysis_started", "SonarQube analysis started", "", Severity.INFO);
    public static final EventType ANALYSIS_COMPLETE = createEventType(
            "analysis_complete", "SonarQube analysis complete", "", Severity.INFO);
    public static final List<EventType> EVENT_TYPES = Arrays.asList(
            ANALYSIS_STARTED, ANALYSIS_COMPLETE);

    public static EventType createEventType(final Metric metric) {
        return createEventType("analysis_measure_" + metric.getKey(),
                "SonarQube measure " + metric.getName(),
                metric.getDescription(), Severity.INFO);
    }

    public static EventType createEventType(final String key, final String name,
            final String description, final Severity severity) {
        final EventType eventType = new EventType();
        eventType.setCategory(EventCategory.QUALITY.name());
        eventType.setDescription(description);
        eventType.setEnabled(true);
        eventType.setEntityType(EntityType.PROJECT);
        eventType.setEventKey(key);
        eventType.setName(name);
        eventType.setSeverity(severity);
        return eventType;
    }

    public static List<String> getMetricKeys(final Settings settings) {
        return Arrays.asList(settings.getStringArrayBySeparator(METRICS_KEY, ","));
    }

    public static Provider getProvider(final String serverUrl) {
        final Provider provider = new Provider();
        provider.setProviderType(ProviderType.QUALITY);
        provider.setName("SonarQube");
        provider.setUrl(serverUrl);
        provider.setIcon(serverUrl + "/static/komea/sonarqube_logo.png");
        return provider;
    }

    public static String getUrl(final Settings settings, final String urlKey) {
        String url = settings.getString(urlKey);
        if (url != null) {
            url = url.trim();
            if (url.endsWith("/")) {
                url = url.substring(0, url.length() - 1);
            }
            if (url.isEmpty()) {
                url = null;
            }
        }
        return url;
    }

    public static String getProjectKey(final Settings settings) {
        return getUrl(settings, PROJECT_KEY);
    }

    public static String getKomeaUrl(final Settings settings) {
        return getUrl(settings, SERVER_URL_KEY);
    }

    public static String getSonarUrl(final Settings settings) {
        return getUrl(settings, "sonar.core.serverBaseURL");
    }

    public KomeaPlugin() {
        super();
    }

    public static String getProjectUrl(final String sonarUrl, final int projectId) {
        return sonarUrl + "/dashboard/index/" + projectId;
    }

    public static void pushEvents(final String serverUrl, final EventSimpleDto... events) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(KomeaPlugin.class.getClassLoader());
            final IEventsAPI eventsAPI = RestClientFactory.INSTANCE.createEventsAPI(serverUrl);
            for (final EventSimpleDto event : events) {
                eventsAPI.pushEvent(event);
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        } finally {
            Thread.currentThread().setContextClassLoader(contextClassLoader);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List getExtensions() {
        return Arrays.asList(KomeaProjectAnalysisHandler.class, KomeaDecorator.class,
                KomeaServerStartHandler.class);
    }
}
