/**
 *
 */
package org.komea.product.plugins.testlink.core;

import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import java.util.List;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.IDynamicDataQuery;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.testlink.api.ITestLinkServerDAO;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxy;
import org.komea.product.plugins.testlink.api.ITestLinkServerProxyFactory;
import org.komea.product.plugins.testlink.model.TestLinkProject;
import org.komea.product.plugins.testlink.model.TestLinkServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public final class TestsByStatusKPI implements IDynamicDataQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestsByStatusKPI.class);

    @Autowired
    private IProjectService projectService;

    @Autowired
    private ITestLinkServerProxyFactory proxyFactory;

    @Autowired
    private ITestLinkServerDAO serverConfig;

    private final String status;

    public TestsByStatusKPI(final String status) {

        this.status = status;
    }

    @Override
    public BackupDelay getBackupDelay() {

        return BackupDelay.DAY;
    }

    public String getFormula() {

        return "new " + this.getClass().getName() + "(\"" + status + "\")";
    }

    /*
     * (non-Javadoc)
     *
     * @see org.komea.product.cep.api.dynamicdata.IDynamicDataQuery#getResult()
     */
    @Override
    public KpiResult getResult() {

        final KpiResult kpiResult = new KpiResult();

        final List<TestLinkServer> servers = serverConfig.selectAll();
        for (final TestLinkServer testlinkServer : servers) {
            try {
                addServerResults(kpiResult, testlinkServer);
            } catch (final Exception e) {
                LOGGER.error("Testlink update failed for server {}", testlinkServer.getName(), e);
            }
        }
        return kpiResult;
    }

    public void setProjectService(final IProjectService ProjectService) {

        projectService = ProjectService;
    }

    @Override
    public String toString() {

        return "TestsByStatusKPI{" + "status=" + status + '}';
    }

    private void addServerResults(final KpiResult _kpiResult, final TestLinkServer testlinkServer) {

        final ITestLinkServerProxy openProxy = proxyFactory.newConnector(testlinkServer);
        // traitement spécifique au plugin
        if (isProxyOpened(openProxy)) {
            analysisTestlinkProjects(_kpiResult, testlinkServer, openProxy);
        }
    }

    private void analysisTestlinkProjects(final KpiResult _kpiResult, final TestLinkServer testlinkServer,
            final ITestLinkServerProxy openProxy) {

        final List<TestLinkProject> listProject = openProxy.getListProject();
        for (final TestLinkProject testlinkProject : listProject) {
            LOGGER.debug("Testlink : checking update from project {} of server {}.", testlinkProject.getName(),
                    testlinkServer.getName());
            final String alias = testlinkProject.getName();
            Project project = projectService.selectByAlias(alias);
            if (project == null /*&& conf.isAutocreateProjects()*/) {
                project = projectService.getOrCreate(alias);
            }
            if (project == null) {
                continue;
            }

            final List<TestCase> testCases = openProxy.getTotalTests(testlinkProject);
            final int cpt = countNumberOfTestCases(testCases);
            _kpiResult.put(project.getEntityKey(), cpt);
        }
    }

    private int countNumberOfTestCases(final List<TestCase> testCases) {

        int cpt = 0;
        for (final TestCase testCase : testCases) {
            if (isTestMatches(testCase)) {
                cpt++;
            }
        }
        return cpt;
    }

    private boolean isProxyOpened(final ITestLinkServerProxy openProxy) {

        return openProxy != null;
    }

    private boolean isTestMatches(final TestCase testCase) {
        return status.isEmpty() || testCase.getExecutionStatus() != null
                && testCase.getExecutionStatus().name().toLowerCase().equals(status.toLowerCase());
    }

}
