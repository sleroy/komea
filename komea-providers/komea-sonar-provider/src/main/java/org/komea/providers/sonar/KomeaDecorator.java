package org.komea.providers.sonar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.komea.product.database.dto.EventSimpleDto;
import org.komea.product.database.model.EventType;
import org.komea.product.database.model.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Decorator;
import org.sonar.api.batch.DecoratorBarriers;
import org.sonar.api.batch.DecoratorContext;
import org.sonar.api.batch.DependsUpon;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.MetricFinder;
import org.sonar.api.platform.Server;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.resources.ResourceUtils;

@DependsUpon(DecoratorBarriers.END_OF_VIOLATION_TRACKING)
public class KomeaDecorator implements Decorator {

    private static final Logger LOGGER = LoggerFactory.getLogger(KomeaDecorator.class.getName());
    private String komeaProjectKey;
    private final MetricFinder metricFinder;
    private final Provider provider;
    private final String komeaUrl;
    private final String sonarUrl;
    private final List<String> projectMetricKeys;

    public KomeaDecorator(final Server server, final Settings settings,
            final MetricFinder metricFinder) {
        this.metricFinder = metricFinder;
        this.provider = KomeaPlugin.getProvider(server.getURL());
        this.komeaProjectKey = KomeaPlugin.getProjectKey(settings);
        this.komeaUrl = KomeaPlugin.getKomeaUrl(settings);
        this.sonarUrl = KomeaPlugin.getSonarUrl(settings);
        this.projectMetricKeys = new ArrayList<String>(KomeaPlugin.getMetricKeys(settings));
    }

    @Override
    public void decorate(final Resource resource, final DecoratorContext context) {
        if (!ResourceUtils.isProject(resource) || komeaUrl == null) {
            return;
        }
        Project project = (Project) resource;
        if (komeaProjectKey == null) {
            komeaProjectKey = project.getKey();
        }
        final List<EventSimpleDto> events = new ArrayList<EventSimpleDto>();
        final Collection<Metric> metrics = metricFinder.findAll(projectMetricKeys);
        for (final Metric metric : metrics) {
            final Double result = getValue(metric, context);
            if (result == null) {
                continue;
            }
            final EventSimpleDto event = createMeasureEvent(metric, result,
                    sonarUrl, project.getId());
            events.add(event);
        }
        KomeaPlugin.pushEvents(komeaUrl, events.toArray(new EventSimpleDto[events.size()]));
    }

    private EventSimpleDto createMeasureEvent(final Metric metric,
            final Double value, final String sonarUrl, final int projectId) {
        final EventType eventType = KomeaPlugin.createEventType(metric);
        final String message = "SonarQube measure of metric " + metric.getName()
                + " for project " + komeaProjectKey + " is : " + value;
        final HashMap<String, String> properties = new HashMap<String, String>(0);
        properties.put("metricName", metric.getKey());
        properties.put("value", value.toString());
        properties.put("project", komeaProjectKey);
        properties.put("date", String.valueOf(new Date().getTime()));
        final EventSimpleDto event = new EventSimpleDto();
        event.setDate(new Date());
        event.setEventType(eventType.getEventKey());
        event.setMessage(message);
        event.setProject(komeaProjectKey);
        event.setProperties(properties);
        event.setProvider(provider.getUrl());
        event.setUrl(KomeaPlugin.getProjectUrl(sonarUrl, projectId));
        event.setValue(value);
        return event;
    }

    private Double getValue(final Metric metric, final DecoratorContext context) {
        if (metric == null || !metric.isNumericType()) {
            return null;
        }
        final Measure measure = context.getMeasure(metric);
        if (measure == null) {
            return null;
        }
        final Double result = measure.getValue();
        if (result == null || result.isInfinite() || result.isNaN()) {
            return null;
        }
        return result;
    }

    @Override
    public boolean shouldExecuteOnProject(final Project project) {
        return true;
    }

    @DependsUpon
    public List<Metric> dependsUpon() {
        return Arrays.asList(CoreMetrics.VIOLATIONS_DENSITY);
    }

}
