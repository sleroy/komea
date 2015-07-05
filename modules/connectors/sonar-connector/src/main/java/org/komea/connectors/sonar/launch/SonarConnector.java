package org.komea.connectors.sonar.launch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.komea.product.company.database.model.Project;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Metric;
import org.sonar.wsclient.services.MetricQuery;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceIndexQuery;
import org.sonar.wsclient.services.TimeMachine;
import org.sonar.wsclient.services.TimeMachineCell;
import org.sonar.wsclient.services.TimeMachineQuery;

public class SonarConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(SonarConnector.class.getName());
    private static final List<String> NUMERICAL_TYPES = Arrays.asList(
            "INT", "FLOAT", "PERCENT", "MILLISEC", "WORK_DUR");

    private final Sonar sonar;

    public SonarConnector(final String host) {
        sonar = Sonar.create(host);
    }

    public List<Measure> getMeasuresOfProject(final Project project, final List<Kpi> kpis,
            final DateTime from, final DateTime to) {
        final List<String> kpiKeys = new ArrayList<>(kpis.size());
        for (final Kpi kpi : kpis) {
            kpiKeys.add(kpi.getKey());
        }
        final TimeMachineQuery timeMachineQuery = TimeMachineQuery.createForMetrics(
                project.getKey(), kpiKeys.toArray(new String[kpiKeys.size()]));
        if (from != null) {
            timeMachineQuery.setFrom(from.toDate());
        }
        if (to != null) {
            timeMachineQuery.setTo(to.toDate());
        }
        final TimeMachine timeMachine = sonar.find(timeMachineQuery);
        final List<Measure> measures = new ArrayList<>();
        for (final TimeMachineCell cell : timeMachine.getCells()) {
            final Date date = cell.getDate();
            final Object[] values = cell.getValues();
            for (int i = 0; i < values.length; i++) {
                final Object object = values[i];
                if (object != null && object instanceof Number) {
                    final Double value = ((Number) object).doubleValue();
                    final String kpiKey = kpiKeys.get(i);
                    final Measure measure = new Measure(kpiKey, project.getId(), date, value);
                    measures.add(measure);
                }
            }
        }
        return measures;
    }

    public List<Metric> getMetrics(final List<String> metricKeys) {
        final MetricQuery query = MetricQuery.all();
        final List<Metric> allMetrics = sonar.findAll(query);
        final List<Metric> metrics = new ArrayList<>();
        for (final Metric metric : allMetrics) {
            if (isNumericalMetric(metric) && (metricKeys.isEmpty()
                    || metricKeys.contains(metric.getKey()))) {
                metrics.add(metric);
            }
        }
        return metrics;
    }

    private boolean isNumericalMetric(final Metric metric) {
        return NUMERICAL_TYPES.contains(metric.getType());
    }

    public List<Resource> getProjects(final List<String> projectKeys) {
        final ResourceIndexQuery query = new ResourceIndexQuery().setQualifiers(Resource.QUALIFIER_PROJECT);
        final List<Resource> allProjects = sonar.findAll(query);
        final List<Resource> projects = new ArrayList<>();
        for (final Resource project : allProjects) {
            if (projectKeys.isEmpty() || projectKeys.contains(project.getKey())) {
                projects.add(project);
            }
        }
        return projects;
    }

}
