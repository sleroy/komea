package org.komea.connectors.sonar.launch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.joda.time.DateTime;
import org.komea.product.company.database.model.Project;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.services.Metric;
import org.sonar.wsclient.services.MetricQuery;
import org.sonar.wsclient.services.Profile;
import org.sonar.wsclient.services.ProfileQuery;
import org.sonar.wsclient.services.ProfilesProjectQuery;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceIndexQuery;
import org.sonar.wsclient.services.TimeMachine;
import org.sonar.wsclient.services.TimeMachineCell;
import org.sonar.wsclient.services.TimeMachineQuery;

public class SonarConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(SonarConnector.class.getName());
    private static final List<String> NUMERICAL_TYPES = Arrays.asList(
            "INT", "FLOAT", "PERCENT", "MILLISEC", "WORK_DUR");

    public static SonarConnector create(final String host) {
        final SonarConnector sonarConnector = new SonarConnector();
        sonarConnector.init(host);
        return sonarConnector;
    }
    public static final List<String> SEVERITY_KEYS = Arrays.asList("INFO", "MINOR", "MAJOR", "CRITICAL", "BLOCKER");

    private Sonar sonar;
    private final Map<String, Profile> profilesWithRules = new HashMap<>();

    public SonarConnector() {
    }

    public void init(final String host) {
        sonar = Sonar.create(host);
    }

    public List<Measure> getMeasuresOfProject(final Project project, final List<Kpi> kpis, final DateTime from, final DateTime to) {
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
        final Set<Date> dates = new HashSet<>();
        for (final TimeMachineCell cell : timeMachine.getCells()) {
            final Date date = cell.getDate();
            dates.add(date);
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
        for (final Date date : dates) {
            final Measure measure = new Measure(KomeaConnector.ANALYZES_COUNT, project.getId(), date, 1d);
            measures.add(measure);
        }
        measures.addAll(getActivatedRulesMeasures(project));
        return measures;
    }

    private String getProfileKey(final Profile profile) {
        return profile.getLanguage() + ":" + profile.getName();
    }

    private List<Measure> getActivatedRulesMeasures(final Project project) {
        final List<Measure> measures = new ArrayList<>(5);
        final ProfilesProjectQuery query = ProfilesProjectQuery.createWithProject(project.getKey());
        final List<Profile> profiles = sonar.findAll(query);
        final Map<String, Integer> severities = new HashMap<>(SEVERITY_KEYS.size());
        for (final String severity : SEVERITY_KEYS) {
            severities.put(severity, 0);
        }
        for (final Profile profile : profiles) {
            final String profileKey = getProfileKey(profile);
            if (!profilesWithRules.containsKey(profileKey)) {
                final ProfileQuery subQuery = ProfileQuery.create(profile.getLanguage(), profile.getName());
                profilesWithRules.put(profileKey, sonar.find(subQuery));
            }
            final Profile profileWithRules = profilesWithRules.get(profileKey);
            final List<Profile.Rule> rules = profileWithRules.getRules();
            for (final Profile.Rule rule : rules) {
                final String severity = rule.getSeverity();
                severities.put(severity, severities.get(severity) + 1);
            }
        }
        double cpt = 0;
        for (final String severity : severities.keySet()) {
            final double value = severities.get(severity);
            cpt += value;
            final String kpiKey = getSeverityKpiKey(severity);
            final Measure measure = new Measure(kpiKey, project.getId(), DateTime.now().toDate(), value);
            measures.add(measure);
        }
        final Measure measure = new Measure("rules", project.getId(), DateTime.now().toDate(), cpt);
        measures.add(measure);
        return measures;
    }

    public static String getSeverityKpiKey(final String severity) {
        return "rules_" + severity.toLowerCase();
    }

    public List<Metric> getMetrics(final List<String> metricKeys) {
        final MetricQuery query = MetricQuery.all();
        final List<Metric> allMetrics = sonar.findAll(query);
        final List<Metric> metrics = new ArrayList<>(metricKeys.size());
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
        final ResourceIndexQuery query = new ResourceIndexQuery().
                setQualifiers(Resource.QUALIFIER_PROJECT).setScopes(Resource.SCOPE_SET);
        final List<Resource> allProjects = sonar.findAll(query);
        final List<Resource> projects = new ArrayList<>(projectKeys.size());
        for (final Resource project : allProjects) {
            if (projectKeys.isEmpty() || projectKeys.contains(project.getKey())) {
                projects.add(project);
            }
        }
        return projects;
    }

}
