package org.komea.connectors.jenkins.launch;

import com.offbytwo.jenkins.model.JobWithDetails;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.komea.product.company.database.model.Project;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.komea.product.rest.client.RestClientFactory;
import org.komea.product.rest.client.api.IKpisAPI;
import org.komea.product.rest.client.api.IMeasuresAPI;
import org.komea.product.rest.client.api.IProjectsAPI;

public class KomeaConnector {

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

    public Map<JobWithDetails, Project> getProjects(final List<JobWithDetails> jobs) {
        final Map<JobWithDetails, Project> projects = new HashMap<>(jobs.size());
        for (final JobWithDetails job : jobs) {
            Project project = jobToProject(job);
            project = getOrCreate(project);
            projects.put(job, project);
        }
        return projects;
    }

    private Project jobToProject(final JobWithDetails job) {
        final Project project = new Project();
        project.setDescription(job.getUrl());
        project.setName(job.getDisplayName());
        project.setProjectKey(job.getName());
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

    public List<Kpi> getKpis(final List<Kpi> kpis) {
        final List<Kpi> komeaKpis = new ArrayList<>(kpis.size());
        for (final Kpi kpi : kpis) {
            final Kpi komeaKpi = getOrCreate(kpi);
            komeaKpis.add(komeaKpi);
        }
        return komeaKpis;
    }

}
