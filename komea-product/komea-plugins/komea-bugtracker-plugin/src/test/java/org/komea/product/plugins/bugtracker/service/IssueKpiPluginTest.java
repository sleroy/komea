/**
 *
 */

package org.komea.product.plugins.bugtracker.service;



import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.komea.product.backend.api.exceptions.GroovyScriptException.GroovyValidationStatus;
import org.komea.product.backend.groovy.GroovyEngineService;
import org.komea.product.backend.service.SpringService;
import org.komea.product.backend.service.dataplugin.IDynamicDataSourcePool;
import org.komea.product.backend.service.kpi.KpiDefinition;
import org.komea.product.backend.service.kpi.KpiDefinitionLoader;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.backend.utils.IFilter;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.GroupFormula;
import org.komea.product.database.enums.Severity;
import org.komea.product.plugins.bugtracker.kpis.IssueFilterKPI;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;
import org.komea.product.plugins.bugtracking.model.Issue;
import org.komea.product.plugins.bugtracking.model.IssueResolution;
import org.komea.product.plugins.bugtracking.model.IssueStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import uk.co.jemos.podam.api.PodamFactoryImpl;

import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



/**
 * @author sleroy
 */

public class IssueKpiPluginTest
{
    
    
    private final class isClosedFilter implements IFilter<IIssue>
    {
        
        
        @Override
        public boolean matches(final IIssue _task) {
        
        
            return _task.getStatus() == IssueStatus.CLOSED;
        }
    }
    
    
    
    private final class isOpenedFilter implements IFilter<IIssue>
    {
        
        
        @Override
        public boolean matches(final IIssue _task) {
        
        
            return _task.getStatus() == IssueStatus.OPENED;
        }
    }
    
    
    
    /**
     *
     */
    private static final int            NUMBER_OF_ISSUE_PLUGINS = 2;
    
    
    private final Logger                LOGGER                  = LoggerFactory
                                                                        .getLogger(getClass());
    private final SpringService         springService           = new SpringService();
    protected final List<IIssue>        fakeListOfIssues        = new PodamFactoryImpl()
                                                                        .manufacturePojo(
                                                                                ArrayList.class,
                                                                                Issue.class);
    protected final GroovyEngineService groovyEngineService     = new GroovyEngineService();
    
    
    
    @Before
    public void beforeInit() {
    
    
        final ApplicationContext mock = mock(ApplicationContext.class);
        when(mock.getAutowireCapableBeanFactory()).thenReturn(
                mock(AutowireCapableBeanFactory.class));
        springService.setApplicationContext(mock);
        groovyEngineService.setSpringService(springService);
        groovyEngineService.init();
        
        System.out.println(fakeListOfIssues);
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugtracker.service.IssueKpiPlugin#bzOpenNotFixedBugs()}.
     */
    @Test
    public void test() throws Exception {
    
    
        final KpiDefinition kpiDefinition =
                new KpiDefinitionLoader(groovyEngineService, "ProjectOpenNotFixedBugs.groovy")
                        .load();
        
        wireData(kpiDefinition);
        final Double expected =
                NUMBER_OF_ISSUE_PLUGINS
                        * Double.valueOf(CollectionUtil.filter(fakeListOfIssues,
                                new IFilter<IIssue>()
                                {
                                    
                                    
                                    @Override
                                    public boolean matches(final IIssue _task) {
                                    
                                    
                                        return _task.getStatus() == IssueStatus.OPENED
                                                && _task.getResolution() == IssueResolution.NOT_FIXED;
                                    }
                                    
                                }).size());
        final KpiResult result = kpiDefinition.getResult();
        assertEquals(expected, result.computeUniqueValue(GroupFormula.SUM_VALUE));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugtracker.service.IssueKpiPlugin#bzOpenNotFixedBugs()}.
     */
    @Test
    public void testBugsHandled() throws Exception {
    
    
        final KpiDefinition kpiDefinition =
                new KpiDefinitionLoader(groovyEngineService, "BugsHandled.groovy").load();
        
        wireData(kpiDefinition);
        final Double expected =
                NUMBER_OF_ISSUE_PLUGINS
                        * Double.valueOf(CollectionUtil.filter(fakeListOfIssues,
                                new IFilter<IIssue>()
                                {
                                    
                                    
                                    @Override
                                    public boolean matches(final IIssue _task) {
                                    
                                    
                                        return true;
                                    }
                                    
                                }).size());
        final KpiResult result = kpiDefinition.getResult();
        assertEquals(expected, result.computeUniqueValue(GroupFormula.SUM_VALUE));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugtracker.service.IssueKpiPlugin#bzOpenNotFixedBugs()}.
     */
    @Test
    public void testBugsHandledStillOpened() throws Exception {
    
    
        final KpiDefinition kpiDefinition =
                new KpiDefinitionLoader(groovyEngineService, "BugsHandledStillOpened.groovy")
                        .load();
        
        wireData(kpiDefinition);
        final Double expected =
                NUMBER_OF_ISSUE_PLUGINS
                        * Double.valueOf(CollectionUtil.filter(fakeListOfIssues,
                                new isOpenedFilter()).size());
        final KpiResult result = kpiDefinition.getResult();
        assertEquals(expected, result.computeUniqueValue(GroupFormula.SUM_VALUE));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugtracker.service.IssueKpiPlugin#bzOpenBugs()}.
     */
    @Test
    public void testBzClosedBugs() throws Exception {
    
    
        final KpiDefinition kpiDefinition =
                new KpiDefinitionLoader(groovyEngineService, "ProjectClosedBugScript.groovy")
                        .load();
        
        
        wireData(kpiDefinition);
        final Double expected =
                NUMBER_OF_ISSUE_PLUGINS
                        * Double.valueOf(CollectionUtil.filter(fakeListOfIssues,
                                new isClosedFilter()).size());
        final KpiResult result = kpiDefinition.getResult();
        assertEquals(expected, result.computeUniqueValue(GroupFormula.SUM_VALUE));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugtracker.service.IssueKpiPlugin#bzOpenBugs()}.
     */
    @Test
    public void testBzOpenBugs() throws Exception {
    
    
        final KpiDefinition kpiDefinition =
                new KpiDefinitionLoader(groovyEngineService, "ProjectOpenBugScript.groovy").load();
        
        
        wireData(kpiDefinition);
        final Double expected =
                NUMBER_OF_ISSUE_PLUGINS
                        * Double.valueOf(CollectionUtil.filter(fakeListOfIssues,
                                new isOpenedFilter()).size());
        final KpiResult result = kpiDefinition.getResult();
        assertFalse(result.hasFailed());
        assertEquals(expected, result.computeUniqueValue(GroupFormula.SUM_VALUE));
        
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugtracker.service.IssueKpiPlugin#bzOpenBySeverityBugs(java.lang.String)}.
     */
    @Test
    public void testBzOpenBySeverityBugs() throws Exception {
    
    
        final KpiDefinition kpiDefinition =
                new KpiDefinitionLoader(groovyEngineService,
                        "ProjectOpenByCriticalSeverityBugs.groovy").load();
        
        
        wireData(kpiDefinition);
        final Double expected =
                NUMBER_OF_ISSUE_PLUGINS
                        * Double.valueOf(CollectionUtil.filter(fakeListOfIssues,
                                new IFilter<IIssue>()
                                {
                                    
                                    
                                    @Override
                                    public boolean matches(final IIssue _task) {
                                    
                                    
                                        return _task.getStatus() == IssueStatus.OPENED
                                                && _task.getSeverity() == Severity.CRITICAL;
                                    }
                                    
                                }).size());
        final KpiResult result = kpiDefinition.getResult();
        assertEquals(expected, result.computeUniqueValue(GroupFormula.SUM_VALUE));
        
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugtracker.service.IssueKpiPlugin#bzOpenNotFixedBugs()}.
     */
    @Test
    public void testBzOpenNotFixedBugs() throws Exception {
    
    
        final KpiDefinition kpiDefinition =
                new KpiDefinitionLoader(groovyEngineService, "ProjectOpenNotFixedBugs.groovy")
                        .load();
        
        wireData(kpiDefinition);
        final Double expected =
                NUMBER_OF_ISSUE_PLUGINS
                        * Double.valueOf(CollectionUtil.filter(fakeListOfIssues,
                                new IFilter<IIssue>()
                                {
                                    
                                    
                                    @Override
                                    public boolean matches(final IIssue _task) {
                                    
                                    
                                        return _task.getStatus() == IssueStatus.OPENED
                                                && _task.getResolution() == IssueResolution.NOT_FIXED;
                                    }
                                    
                                }).size());
        final KpiResult result = kpiDefinition.getResult();
        assertEquals(expected, result.computeUniqueValue(GroupFormula.SUM_VALUE));
    }
    
    
    /**
     * Test method for {@link org.komea.product.plugins.bugtracker.service.IssueKpiPlugin#bzTotalBugs()}.
     */
    @Test
    public void testBzTotalBugs() throws Exception {
    
    
        final KpiDefinition kpiDefinition =
                new KpiDefinitionLoader(groovyEngineService, "ProjectTotalBugs.groovy").load();
        
        wireData(kpiDefinition);
        final Double expected = Double.valueOf(fakeListOfIssues.size() * 2);
        final KpiResult result = kpiDefinition.getResult();
        assertFalse(result.hasFailed());
        assertEquals(expected, result.computeUniqueValue(GroupFormula.SUM_VALUE));
    }
    
    
    /**
     * @param _validFormula
     */
    private void validFormula(final GroovyValidationStatus _validFormula) {
    
    
        assertEquals(GroovyValidationStatus.OK, _validFormula);
        
        
    }
    
    
    /**
     * @param _parseQuery
     */
    private void wireData(final KpiDefinition _parseQuery) {
    
    
        final IssueFilterKPI issueFilter = (IssueFilterKPI) _parseQuery.getQuery();
        final IDynamicDataSourcePool dynamicDataSource = mock(IDynamicDataSourcePool.class);
        issueFilter.setDataSourcePool(dynamicDataSource);
        final IIssuePlugin iIssuePlugin = mock(IIssuePlugin.class);
        when(dynamicDataSource.getDataSourceOfType(IIssuePlugin.class)).thenReturn(
                Lists.newArrayList(iIssuePlugin, iIssuePlugin));
        when(iIssuePlugin.getData()).thenReturn(fakeListOfIssues);
        
    }
}
