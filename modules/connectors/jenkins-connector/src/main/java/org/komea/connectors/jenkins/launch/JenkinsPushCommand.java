package org.komea.connectors.jenkins.launch;

import com.offbytwo.jenkins.model.JobWithDetails;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;
import org.komea.connectors.sdk.main.IConnectorCommand;
import org.komea.product.company.database.model.Project;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsPushCommand implements IConnectorCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsPushCommand.class.getName());

    @Option(name = "-url", usage = "Url of Komea", required = true)
    private String komeaUrl;

    @Option(name = "-jenkins", usage = "Url of Jenkins", required = true)
    private String jenkinsUrl;

    @Option(name = "-clear", usage = "Clear measures of kpis", required = false)
    private Boolean clear = false;

    @Option(name = "-projects", usage = "List of Jenkins job keys to include separated by whitespace", required = false, handler = StringArrayOptionHandler.class)
    private String[] jobKeys = new String[0];

    private JenkinsConnector jenkinsConnector;
    private KomeaConnector komeaConnector;

    @Override
    public String action() {
        return "push";
    }

    @Override
    public String description() {
        return "Push Jenkins measures to Komea";
    }

    @Override
    public void init() throws Exception {
        LOGGER.info("Komea URL : " + komeaUrl);
        LOGGER.info("Jenkins URL : " + jenkinsUrl);
        jenkinsConnector = JenkinsConnector.create(jenkinsUrl);
        komeaConnector = KomeaConnector.create(komeaUrl);
    }

    @Override
    public void run() throws Exception {
        LOGGER.info("Select jobs from Jenkins");
        final List<JobWithDetails> jobs = jenkinsConnector.getJobs(Arrays.asList(jobKeys));
        LOGGER.info("Select associated projects from Komea (" + jobs.size() + ")");
        final Map<JobWithDetails, Project> projects = komeaConnector.getProjects(jobs);
        LOGGER.info("Get Jenkins kpis");
        List<Kpi> kpis = JenkinsKpis.getKpis();
        LOGGER.info("Select associated kpis from Komea (" + kpis.size() + ")");
        kpis = komeaConnector.getKpis(kpis);

        if (clear) {
            LOGGER.info("Clear measures of " + kpis.size() + " kpi in Komea...");
            int cpt = 0;
            for (final Kpi kpi : kpis) {
                LOGGER.info("Clear measures of kpi " + kpi.getKey() + " in Komea ("
                        + (int) (100d * cpt / kpis.size()) + "%)");
                komeaConnector.clearMeasuresOfKpi(kpi.getKey());
                cpt++;
            }
            LOGGER.info("Clear measures of " + kpis.size() + " kpi in Komea done.");
        }

        LOGGER.info("Calculate measures from Jenkins and send them to Komea...");
        int cpt = 0;
        for (final JobWithDetails job : projects.keySet()) {
            final Project project = projects.get(job);
            LOGGER.info("Calculate measures for project " + project.getKey() + " from Sonar");
            final List<Measure> measures = jenkinsConnector.evaluateJob(job, project.getId());
            LOGGER.info("Send " + measures.size() + " measures for project "
                    + project.getKey() + " to Komea ("
                    + (int) (100d * cpt / projects.size()) + "%)");
            komeaConnector.pushMeasures(measures);
            cpt++;
        }
        LOGGER.info("Select measures from Sonar and send them to Komea done...");
    }

}
