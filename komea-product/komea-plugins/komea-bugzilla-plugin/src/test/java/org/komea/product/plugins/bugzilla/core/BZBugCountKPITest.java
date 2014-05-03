
package org.komea.product.plugins.bugzilla.core;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.komea.product.backend.kpi.search.Filter;
import org.komea.product.backend.kpi.search.Search;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugzilla.api.IBZConfigurationDAO;
import org.komea.product.plugins.bugzilla.api.IBZServerProxy;
import org.komea.product.plugins.bugzilla.api.IBZServerProxyFactory;
import org.komea.product.plugins.bugzilla.model.BZServerConfiguration;
import org.komea.product.plugins.bugzilla.model.BzBug;
import org.komea.product.service.dto.EntityKey;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;



@RunWith(MockitoJUnitRunner.class)
public class BZBugCountKPITest
{
    
    
    @Mock
    private IBZConfigurationDAO   bugZillaConfigurationMock;
    
    @Mock
    private IProjectService       projectService;
    
    @Mock
    private IBZServerProxyFactory proxyFactoryMock;
    
    @Mock
    private IBZServerProxy        server;
    
    
    
    public BZBugCountKPITest() {
    
    
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
    
    
    @Test
    public void testGetExceptionsResult() {
    
    
        final BZBugCountKPI kpi =
                createKpi(Search.create(Filter.create("status", false, "open", "closed")));
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(2), results.get(1));
    }
    
    
    @Test
    public void testGetIgnoreCaseResult() {
    
    
        final BZBugCountKPI kpi =
                createKpi(Search.create(Filter.create("StAtUs", true, "open", "CLOSED")));
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(4), results.get(1));
    }
    
    
    @Test
    public void testGetResult() {
    
    
        final BZBugCountKPI kpi =
                createKpi(
                        Search.create(Filter.create("status", true, "open", "closed"),
                                Filter.create("resolution", false, "fixed")),
                        Search.create(Filter.create("status", false, "closed"),
                                Filter.create("resolution", true, "assigned")));
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(3), results.get(1));
    }
    
    
    @Test
    public void testGetResultByMultipleSearchsOneFilterMultipleValues() {
    
    
        final BZBugCountKPI kpi =
                createKpi(Search.create(Filter.create("status", true, "open", "closed")),
                        Search.create(Filter.create("resolution", true, "fixed", "assigned")));
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(5), results.get(1));
    }
    
    
    @Test
    public void testGetResultByMultipleSearchsOneFilterOneValue() {
    
    
        final BZBugCountKPI kpi =
                createKpi(Search.create(Filter.create("status", true, "open")),
                        Search.create(Filter.create("resolution", true, "fixed")));
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(3), results.get(1));
    }
    
    
    @Test
    public void testGetResultByOneSearchOneFilterMultipleValues() {
    
    
        final BZBugCountKPI kpi =
                createKpi(Search.create(Filter.create("status", true, "open", "closed")));
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(4), results.get(1));
    }
    
    
    @Test
    public void testGetResultByOneSearchOneFilterOneValue() {
    
    
        final BZBugCountKPI kpi = createKpi(Search.create(Filter.create("status", true, "open")));
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(2), results.get(1));
    }
    
    
    @Test
    public void testGetResultBySearchAll() {
    
    
        final BZBugCountKPI kpi = createKpi();
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(6), results.get(1));
    }
    
    
    @Test
    public void testGetResultFromDefaultConstructor() {
    
    
        final BZBugCountKPI kpi = new BZBugCountKPI();
        kpi.setBugZillaConfiguration(bugZillaConfigurationMock);
        kpi.setProxyFactory(proxyFactoryMock);
        kpi.setProjectService(projectService);
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(6), results.get(1));
    }
    
    
    @Test
    public void testGetResultFromEmptyStringFormula() {
    
    
        final BZBugCountKPI kpi = new BZBugCountKPI("");
        kpi.setBugZillaConfiguration(bugZillaConfigurationMock);
        kpi.setProxyFactory(proxyFactoryMock);
        kpi.setProjectService(projectService);
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(6), results.get(1));
    }
    
    
    @Test
    public void testGetResultFromStringFormula() {
    
    
        final BZBugCountKPI kpi =
                new BZBugCountKPI(
                        "status=open,closed;resolution!=fixed#status!=closed;resolution=assigned");
        kpi.setBugZillaConfiguration(bugZillaConfigurationMock);
        kpi.setProxyFactory(proxyFactoryMock);
        kpi.setProjectService(projectService);
        final Map<Integer, Integer> results = getResults(kpi);
        Assert.assertEquals(1, results.size());
        Assert.assertEquals(Integer.valueOf(3), results.get(1));
    }
    
    
    private BZBugCountKPI createKpi(final Search... searchs) {
    
    
        final BZBugCountKPI kpi = BZBugCountKPI.create(searchs);
        kpi.setBugZillaConfiguration(bugZillaConfigurationMock);
        kpi.setProxyFactory(proxyFactoryMock);
        kpi.setProjectService(projectService);
        return kpi;
    }
    
    
    private Map<Integer, Integer> getResults(final BZBugCountKPI kpi) {
    
    
        final KpiResult result = kpi.getResult();
        final Map<Integer, Integer> results = new HashMap<Integer, Integer>();
        for (final EntityKey entityKey : result.getMap().keySet()) {
            results.put(entityKey.getId(), result.getValue(entityKey).intValue());
        }
        return results;
    }
}
