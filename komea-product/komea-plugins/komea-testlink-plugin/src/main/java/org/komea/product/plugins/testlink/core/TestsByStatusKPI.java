/**
 *
 */
package org.komea.product.plugins.testlink.core;

import br.eti.kinoshita.testlinkjavaapi.model.TestCase;
import java.util.List;
import org.komea.cep.dynamicdata.IDynamicDataQuery;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.api.formula.tuple.ITuple;
import org.komea.eventory.formula.tuple.TupleFactory;
import org.komea.eventory.formula.tuple.TupleResultMap;
import org.komea.eventory.query.CEPResult;
import org.komea.product.backend.service.entities.IProjectService;
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
    private ITestLinkServerDAO serverConfig;

    @Autowired
    private ITestLinkServerProxyFactory proxyFactory;

    private final String status;

    public TestsByStatusKPI(final String status) {
        this.status = status;
    }

    /*
     * (non-Javadoc)
     * @see org.komea.cep.dynamicdata.IDynamicDataQuery#getResult()
     */
    @Override
    public ICEPResult getResult() {
        final TupleResultMap<Integer> tupleResultMap = new TupleResultMap<Integer>();

        final List<TestLinkServer> servers = serverConfig.selectAll();
        for (final TestLinkServer testlinkServer : servers) {
            try {
                addServerResults(tupleResultMap, testlinkServer);
            } catch (final Exception e) {
                LOGGER.error("Testlink update failed for server {}", testlinkServer.getName(), e);
            }
        }
        final ICEPResult result = CEPResult.buildFromMap(tupleResultMap);
        return result;
    }

    private void addServerResults(final TupleResultMap<Integer> tupleResultMap, final TestLinkServer testlinkServer) {
        final ITestLinkServerProxy openProxy = proxyFactory.newConnector(testlinkServer);
        // traitement spécifique au plugin
        if (openProxy != null) {
            final List<TestLinkProject> listProject = openProxy.getListProject();
            for (final TestLinkProject projet : listProject) {
                LOGGER.debug("Testlink : checking update from project {} of server {}.",
                        projet.getName(), testlinkServer.getName());
                final Project project = projectService.selectByKey(projet.getName());
                if (project == null) {
                    continue;
                }
                final ITuple tuple = TupleFactory.newTuple(project.getEntityKey());
                final List<TestCase> testCases = openProxy.getTotalTests(projet);
                LOGGER.info(project.getDisplayName() + " test cases : " + testCases.size());
                int cpt = 0;
                for (final TestCase testCase : testCases) {
                    if (isTestMatches(testCase)) {
                        cpt++;
                    }
                }
                tupleResultMap.insertEntry(tuple, cpt);
            }
        }
    }

    private boolean isTestMatches(final TestCase testCase) {
        return status.isEmpty() || (testCase.getExecutionStatus() != null
                && testCase.getExecutionStatus().name().toLowerCase().equals(status.toLowerCase()));
    }

    public void setProjectService(IProjectService ProjectService) {
        this.projectService = ProjectService;
    }

    @Override
    public String toString() {
        return "TestsByStatusKPI{" + "status=" + status + '}';
    }

    @Override
    public String getFormula() {
        return "new " + this.getClass().getName() + "(\"" + status + "\")";
    }

}
