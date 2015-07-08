package org.komea.connectors.sonar.launch;

import java.util.Arrays;
import java.util.List;
import org.joda.time.DateTime;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;
import org.komea.connectors.sdk.main.IConnectorCommand;
import org.komea.connectors.sdk.std.impl.DateOptionHandler;
import org.komea.product.company.database.model.Project;
import org.komea.product.database.model.Kpi;
import org.komea.product.database.model.Measure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.wsclient.services.Metric;
import org.sonar.wsclient.services.Resource;

public class SonarPushCommand implements IConnectorCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(SonarPushCommand.class.getName());

    @Option(name = "-url", usage = "Url of Komea", required = true)
    private String komeaUrl;

    @Option(name = "-sonar", usage = "Url of Sonar", required = true)
    private String sonarUrl;

    @Option(name = "-clear", usage = "Clear measures of kpis", required = false)
    private Boolean clear = false;

    @Option(name = "-from", usage = "Date of beginning in format dd/MM/yyyy", required = false, handler = DateOptionHandler.class)
    private DateTime from;

    @Option(name = "-to", usage = "Date of ending in format dd/MM/yyyy", required = false, handler = DateOptionHandler.class)
    private DateTime to;

    @Option(name = "-projects", usage = "List of Sonar project keys to include separated by whitespace", required = false, handler = StringArrayOptionHandler.class)
    private String[] resourceKeys = new String[0];

    @Option(name = "-metrics", usage = "List of Sonar metric keys to include separated by whitespace", required = false, handler = StringArrayOptionHandler.class)
    private String[] metricKeys = new String[0];

    private SonarConnector sonarConnector;
    private KomeaConnector komeaConnector;

    @Override
    public String action() {
        return "push";
    }

    @Override
    public String description() {
        return "Push Sonar measures to Komea";
    }

    @Override
    public void init() throws Exception {
        LOGGER.info("Komea URL : " + komeaUrl);
        LOGGER.info("Sonar URL : " + sonarUrl);
        sonarConnector = SonarConnector.create(sonarUrl);
        komeaConnector = KomeaConnector.create(komeaUrl);
    }

    @Override
    public void run() throws Exception {
        LOGGER.info("Select projects from Sonar");
        final List<Resource> resources = sonarConnector.getProjects(Arrays.asList(resourceKeys));
        LOGGER.info("Select associated projects from Komea (" + resources.size() + ")");
        final List<Project> projects = komeaConnector.getProjects(resources);
        LOGGER.info("Select metrics from Sonar");
        final List<Metric> metrics = sonarConnector.getMetrics(Arrays.asList(metricKeys));
        LOGGER.info("Select associated kpis from Komea (" + metrics.size() + ")");
        final List<Kpi> metricKpis = komeaConnector.toKpis(metrics);
        final List<Kpi> allKpis = komeaConnector.additionalKpis();
        allKpis.addAll(metricKpis);

        if (clear) {
            LOGGER.info("Clear measures of " + allKpis.size() + " kpis in Komea...");
            int cpt = 0;
            for (final Kpi kpi : allKpis) {
                LOGGER.info("Clear measures of kpi " + kpi.getKey() + " in Komea ("
                        + (int) (100d * cpt / allKpis.size()) + "%)");
                komeaConnector.clearMeasuresOfKpi(kpi.getKey());
                cpt++;
            }
            LOGGER.info("Clear measures of " + allKpis.size() + " kpis in Komea done.");
        }

        LOGGER.info("Select measures from Sonar and send them to Komea...");
        int cpt = 0;
        for (final Project project : projects) {
            LOGGER.info("Select measures for project " + project.getKey() + " from Sonar");
            final List<Measure> measures = sonarConnector.getMeasuresOfProject(
                    project, metricKpis, from, to);
            LOGGER.info("Send " + measures.size() + " measures for project "
                    + project.getKey() + " to Komea ("
                    + (int) (100d * cpt / projects.size()) + "%)");
            komeaConnector.pushMeasures(measures);
            cpt++;
        }
        LOGGER.info("Select measures from Sonar and send them to Komea done.");
    }

}
