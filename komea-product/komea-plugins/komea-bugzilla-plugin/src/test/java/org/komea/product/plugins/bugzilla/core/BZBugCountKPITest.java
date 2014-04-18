package org.komea.product.plugins.bugzilla.core;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.eventory.api.formula.ICEPResult;
import org.komea.eventory.api.formula.ITupleResultMap;
import org.komea.eventory.api.formula.tuple.ITuple;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBZServerProxy;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.plugins.bugzilla.model.BzBug;
import org.komea.product.plugins.bugzilla.model.BzFilter;
import org.komea.product.plugins.bugzilla.model.BzSearch;
import org.komea.product.service.dto.EntityKey;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BZBugCountKPITest {

    @Mock
    private IBZConfigurationDAO bugZillaConfigurationMock;

    @Mock
    private IBZServerProxyFactory proxyFactoryMock;

    @Mock
    private IProjectService projectService;

    @Mock
    private IBZServerProxy server;

    public BZBugCountKPITest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        final List<BZServerConfiguration> configurations = new ArrayList<BZServerConfiguration>(1);
        final BZServerConfiguration configuration = new BZServerConfiguration();
        configurations.add(configuration);
        final Project project1 = new Project(1, "scertify", null, null, null, null);
        final List<BzBug> bugs = Lists.newArrayList();
        bugs.add(BzBug.create("open", "", "", ""));
        bugs.add(BzBug.create("closed", "fixed", "", ""));
        bugs.add(BzBug.create("open", "fixed", "", ""));
        bugs.add(BzBug.create("closed", "assigned", "", ""));
        bugs.add(BzBug.create("", "assigned", "", ""));
        bugs.add(BzBug.create("", "", "", ""));

        Mockito.when(bugZillaConfigurationMock.selectAll()).thenReturn(configurations);
        Mockito.when(proxyFactoryMock.newConnector(configuration)).thenReturn(server);
        Mockito.when(server.getProductNames()).thenReturn(Arrays.asList("scertify"));
        Mockito.when(server.getBugs("scertify")).thenReturn(bugs);
        Mockito.when(projectService.selectByKey("scertify")).thenReturn(project1);
    }

    @After
    public void tearDown() {
    }

    private BZBugCountKPI createKpi(final BzSearch... searchs) {
        final BZBugCountKPI kpi = BZBugCountKPI.create(searchs);
        kpi.setBugZillaConfiguration(bugZillaConfigurationMock);
        kpi.setProxyFactory(proxyFactoryMock);
        kpi.setProjectService(projectService);
        return kpi;
    }

    @Test
    public void testGetResultBySearchAll() {
        final BZBugCountKPI kpi = createKpi();
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(6), results.get(1));
    }

    @Test
    public void testGetResultByOneSearchOneFilterOneValue() {
        final BZBugCountKPI kpi = createKpi(
                BzSearch.create(BzFilter.create("status", "open"))
        );
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(2), results.get(1));
    }

    @Test
    public void testGetResultByOneSearchOneFilterMultipleValues() {
        final BZBugCountKPI kpi = createKpi(
                BzSearch.create(BzFilter.create("status", "open", "closed"))
        );
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(4), results.get(1));
    }

    @Test
    public void testGetResultByMultipleSearchsOneFilterOneValue() {
        final BZBugCountKPI kpi = createKpi(
                BzSearch.create(BzFilter.create("status", "open")),
                BzSearch.create(BzFilter.create("resolution", "fixed"))
        );
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(3), results.get(1));
    }

    @Test
    public void testGetResultByMultipleSearchsOneFilterMultipleValues() {
        final BZBugCountKPI kpi = createKpi(
                BzSearch.create(BzFilter.create("status", "open", "closed")),
                BzSearch.create(BzFilter.create("resolution", "fixed", "assigned"))
        );
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(5), results.get(1));
    }

    @Test
    public void testGetResult() {
        final BZBugCountKPI kpi = createKpi(
                BzSearch.create(BzFilter.create("status", "open", "closed"),
                        BzFilter.create("resolution", "fixed")),
                BzSearch.create(BzFilter.create("status", "closed"),
                        BzFilter.create("resolution", "assigned"))
        );
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(3), results.get(1));
    }

    @Test
    public void testGetResultFromStringFormula() {
        final BZBugCountKPI kpi = new BZBugCountKPI(
                "status=open,closed;resolution=fixed#status=closed;resolution=assigned");
        kpi.setBugZillaConfiguration(bugZillaConfigurationMock);
        kpi.setProxyFactory(proxyFactoryMock);
        kpi.setProjectService(projectService);
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(3), results.get(1));
    }

    private Map<Integer, Integer> getResults(final BZBugCountKPI kpi) {
        final ICEPResult result = kpi.getResult();
        final ITupleResultMap<Integer> map = result.asMap();
        final Map<ITuple, Integer> table = map.getTable();
        final Map<Integer, Integer> results = new HashMap<Integer, Integer>(table.size());
        for (final ITuple tuple : table.keySet()) {
            final EntityKey entityKey = tuple.getFirst();
            results.put(entityKey.getId(), table.get(tuple));
        }
        return results;
    }
}
