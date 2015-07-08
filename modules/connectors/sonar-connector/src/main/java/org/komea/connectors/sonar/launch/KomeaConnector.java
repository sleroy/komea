package org.komea.connectors.sonar.launch;

import java.util.ArrayList;
import java.util.List;
import org.komea.product.company.database.enums.EntityType;
import org.komea.product.company.database.model.Project;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.rest.client.RestClientFactory;
import org.komea.product.rest.client.api.IKpisAPI;
import org.komea.product.rest.client.api.IMeasuresAPI;
import org.komea.product.rest.client.api.IProjectsAPI;
import org.sonar.wsclient.services.Metric;
import org.sonar.wsclient.services.Resource;

public class KomeaConnector {

    public static final String ANALYZES_COUNT = "analyzes_count";

    public static KomeaConnector create(final String url) throws Exception {
        final KomeaConnector komeaConnector = new KomeaConnector();
        komeaConnector.init(url);
        return komeaConnector;
    }

    private IKpisAPI kpisService;
    private IProjectsAPI projectsService;
    private IMeasuresAPI measuresService;

    private KomeaConnector() {
    }

    public List<Kpi> additionalKpis() {
        final List<Kpi> kpis = new ArrayList<>(6);
        kpis.add(getOrCreate(getAnalyzesCountKpi()));
        for (final String severity : SonarConnector.SEVERITY_KEYS) {
            kpis.add(getOrCreate(getRulesBySeverity(severity)));
        }
        return kpis;
    }

    public List<Kpi> toKpis(final List<Metric> metrics) {
        final List<Kpi> kpis = new ArrayList<>(metrics.size());
        for (final Metric metric : metrics) {
            Kpi kpi = metricToKpi(metric);
            kpi = getOrCreate(kpi);
            kpis.add(kpi);
        }
        return kpis;
    }

    private Kpi getAnalyzesCountKpi() {
        final Kpi kpi = new Kpi();
        kpi.setDescription("Number of SonarQube analyzes per project.");
        kpi.setEntityType(EntityType.PROJECT);
        kpi.setEsperRequest(EmptyKpi.getFormula());
        kpi.setGroupFormula(GroupFormula.SUM_VALUE);
        kpi.setKpiKey(ANALYZES_COUNT);
        kpi.setName("SonarQube Analyzes");
        kpi.setProviderType(ProviderType.QUALITY);
        kpi.setValueDirection(ValueDirection.BETTER);
        kpi.setValueType(ValueType.INT);
        return kpi;
    }

    private Kpi getRulesBySeverity(final String severity) {
        final Kpi kpi = new Kpi();
        kpi.setDescription("Number of SonarQube rules activated with severity " + severity + " per project.");
        kpi.setEntityType(EntityType.PROJECT);
        kpi.setEsperRequest(EmptyKpi.getFormula());
        kpi.setGroupFormula(GroupFormula.LAST_VALUE);
        kpi.setKpiKey(SonarConnector.getSeverityKpiKey(severity));
        kpi.setName("Rules " + severity);
        kpi.setProviderType(ProviderType.QUALITY);
        kpi.setValueDirection(ValueDirection.NONE);
        kpi.setValueType(ValueType.INT);
        return kpi;
    }

    private Kpi metricToKpi(final Metric metric) {
        final Kpi kpi = new Kpi();
        kpi.setDescription(metric.getDescription());
        kpi.setEntityType(EntityType.PROJECT);
        kpi.setEsperRequest(EmptyKpi.getFormula());
        kpi.setGroupFormula(GroupFormula.LAST_VALUE);
        kpi.setKpiKey(metric.getKey());
        kpi.setName(metric.getName());
        kpi.setProviderType(ProviderType.QUALITY);
        final Integer direction = metric.getDirection();
        if (direction < 0) {
            kpi.setValueDirection(ValueDirection.WORST);
        } else if (direction < 0) {
            kpi.setValueDirection(ValueDirection.BETTER);
        } else {
            kpi.setValueDirection(ValueDirection.NONE);
        }
        final String type = metric.getType();
        if ("FLOAT".equals(type)) {
            kpi.setValueType(ValueType.FLOAT);
        } else if ("PERCENT".equals(type)) {
            kpi.setValueType(ValueType.PERCENT);
        } else {
            kpi.setValueType(ValueType.INT);
        }
        return kpi;
    }

    public List<Project> getProjects(final List<Resource> resources) {
        final List<Project> projects = new ArrayList<>(resources.size());
        for (final Resource resource : resources) {
            Project project = resourceToProject(resource);
            project = getOrCreate(project);
            projects.add(project);
        }
        return projects;
    }

    private Project resourceToProject(final Resource resource) {
        final Project project = new Project();
        project.setDescription(resource.getDescription());
        project.setName(resource.getName());
        project.setProjectKey(resource.getKey());
        project.setIcon("project");
        return project;
    }

    private void init(final String url) throws Exception {
        kpisService = RestClientFactory.INSTANCE.createKpisAPI(url);
        projectsService = RestClientFactory.INSTANCE.createProjectsAPI(url);
        measuresService = RestClientFactory.INSTANCE.createMeasuresAPI(url);
    }

    private Kpi getOrCreate(final Kpi kpi) {
        try {
            return kpisService.getOrCreate(kpi);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Project getOrCreate(final Project project) {
        try {
            return projectsService.getOrCreate(project);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void pushMeasures(final List<Measure> measures) {
        try {
            measuresService.pushMeasures(measures);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void clearMeasuresOfKpi(final String kpiKey) {
        try {
            measuresService.clearMeasuresOfKpi(kpiKey);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
