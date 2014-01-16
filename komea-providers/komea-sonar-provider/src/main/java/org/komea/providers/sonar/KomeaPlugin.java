
package org.komea.providers.sonar;



import java.util.Arrays;
import java.util.List;

import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.EventCategory;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.Severity;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
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
                global = false) })
public class KomeaPlugin extends SonarPlugin
{
    
    
    private static final Logger         LOGGER                      =
                                                                            LoggerFactory
                                                                                    .getLogger(KomeaPlugin.class
                                                                                            .getName());
    public static final String          SERVER_URL_KEY              = "komea.serverUrl";
    public static final String          METRICS_KEY                 = "komea.metrics";
    public static final String          PROJECT_KEY                 = "komea.project";
    public static final String          EVENT_ANALYSIS_STARTED_KEY  = "SONARQUBE_ANALYSIS_STARTED";
    public static final EventType       EVENT_ANALYSIS_STARTED      =
                                                                            createEventType(
                                                                                    EVENT_ANALYSIS_STARTED_KEY,
                                                                                    "SonarQube analysis started",
                                                                                    "",
                                                                                    Severity.INFO);
    public static final String          EVENT_ANALYSIS_ENDED_KEY    = "SONARQUBE_ANALYSIS_ENDED";
    public static final EventType       EVENT_ANALYSIS_ENDED        =
                                                                            createEventType(
                                                                                    EVENT_ANALYSIS_ENDED_KEY,
                                                                                    "SonarQube analysis ended",
                                                                                    "",
                                                                                    Severity.INFO);
    public static final String          EVENT_ANALYSIS_DURATION_KEY = "SONARQUBE_ANALYSIS_DURATION";
    public static final EventType       EVENT_ANALYSIS_DURATION     =
                                                                            createEventType(
                                                                                    EVENT_ANALYSIS_DURATION_KEY,
                                                                                    "SonarQube analysis duration",
                                                                                    "",
                                                                                    Severity.INFO);
    public static final List<EventType> EVENT_TYPES                 =
                                                                            Arrays.asList(
                                                                                    EVENT_ANALYSIS_STARTED,
                                                                                    EVENT_ANALYSIS_ENDED,
                                                                                    EVENT_ANALYSIS_DURATION);
    
    
    
    public static EventType createEventType(final Metric metric) {
    
    
        return createEventType("SONARQUBE_MEASURE_" + metric.getKey(), "SonarQube measure '"
                + metric.getName() + "'", metric.getDescription(), Severity.INFO);
    }
    
    
    public static EventType createEventType(
            final String key,
            final String name,
            final String description,
            final Severity severity) {
    
    
        final EventType eventType = new EventType();
        eventType.setCategory(EventCategory.QUALITY.name());
        eventType.setDescription(description);
        eventType.setEnabled(true);
        eventType.setEntityTypeEnum(EntityType.PROJECT);
        eventType.setEventKey(key);
        eventType.setName(name);
        eventType.setSeverityEnum(severity);
        return eventType;
    }
    
    
    public static List<String> getMetricKeys(final Settings settings) {
    
    
        return Arrays.asList(settings.getStringArrayBySeparator(METRICS_KEY, ","));
    }
    
    
    public static String getProjectKey(final Settings settings) {
    
    
        String property = settings.getString(PROJECT_KEY);
        if (property != null && property.trim().isEmpty()) {
            property = null;
        }
        return property;
    }
    
    
    public static Provider getProvider(final String serverUrl) {
    
    
        final Provider provider = new Provider();
        provider.setProviderTypeEnum(ProviderType.SONARQUBE);
        provider.setName("SonarQube");
        provider.setUrl(serverUrl);
        provider.setIcon(serverUrl + "/static/komea/sonarqube_logo.png");
        return provider;
    }
    
    
    public static String getServerUrl(final Settings settings) {
    
    
        String property = settings.getString(SERVER_URL_KEY);
        if (property != null && property.trim().isEmpty()) {
            property = null;
        }
        return property;
    }
    
    
    public KomeaPlugin() {
    
    
        super();
    }
    
    
    @Override
    @SuppressWarnings("unchecked")
    public List getExtensions() {
    
    
        return Arrays.asList(KomeaProjectAnalysisHandler.class, KomeaDecorator.class,
                KomeaServerStartHandler.class);
    }
}
