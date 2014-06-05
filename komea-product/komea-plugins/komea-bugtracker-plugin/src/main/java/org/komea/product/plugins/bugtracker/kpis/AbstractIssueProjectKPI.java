/**
 * 
 */

package org.komea.product.plugins.bugtracker.kpis;



import groovy.lang.Binding;
import groovy.lang.Script;

import java.util.List;

import org.apache.wicket.util.collections.MultiMap;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.api.ISpringService;
import org.komea.product.backend.groovy.AbstractDynamicQuery;
import org.komea.product.backend.groovy.GroovyFilter;
import org.komea.product.backend.service.entities.IProjectService;
import org.komea.product.backend.utils.CollectionUtil;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;
import org.komea.product.plugins.model.IDynamicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * @author sleroy
 */
public abstract class AbstractIssueProjectKPI extends

AbstractDynamicQuery
{
    
    
    private static final Logger                      LOGGER =
                                                                    LoggerFactory
                                                                            .getLogger(IssueHandlerKPI.class);
    private String                                   filter;
    @Autowired
    private IGroovyEngineService                     groovyEngineService;
    @Autowired
    private IProjectService                          projectService;
    protected final IDynamicDataSource<IIssuePlugin> dynamicDataSource;
    @Autowired
    protected ISpringService                         springService;
    
    
    
    public AbstractIssueProjectKPI(final BackupDelay _delay, final String _dynamicSource) {
    
    
        super(EntityType.PERSON, _delay);
        dynamicDataSource = springService.getBean(_dynamicSource);
    }
    
    
    @Override
    public KpiResult evaluateResult() {
    
    
        final KpiResult kpiResult = new KpiResult();
        final Script initializationToEvaluateResults = initializationToEvaluateResults();
        final MultiMap<Project, IIssue> map = new MultiMap<Project, IIssue>();
        LOGGER.info("Obtaining results from the dynamic data source");
        final List<IIssue> listOfIssues = dynamicDataSource.fetchData().getData();
        LOGGER.info("Results obtained : {} elements", listOfIssues.size());
        LOGGER.info("Sorting results per handler");
        for (final IIssue issue : listOfIssues) {
            final Project handler = obtainProject(issue);
            if (handler == null) {
                continue;
            }
            map.addValue(handler, issue);
        }
        LOGGER.info("Applying the filter per handler and obtaining results");
        for (final Project person : projectService.selectAll()) {
            final List<IIssue> filteredIssues =
                    CollectionUtil.filter(map.get(person), new GroovyFilter(
                            initializationToEvaluateResults, "issue"));
            kpiResult.put(person, filteredIssues.size());
        }
        
        
        return kpiResult;
    }
    
    
    public String getFilter() {
    
    
        return filter;
    }
    
    
    public void setFilter(final String _filter) {
    
    
        filter = _filter;
    }
    
    
    @Override
    public String toString() {
    
    
        return "IssueHandlerKPI [\\n\\tdynamicDataSource="
                + dynamicDataSource + ", \\n\\tfilter=" + filter + "]";
    }
    
    
    protected Script initializationToEvaluateResults() {
    
    
        final CompilerConfiguration config = new CompilerConfiguration();
        final Binding binding = new Binding();
        return groovyEngineService.parseScript(filter, config, binding);
        
        
    }
    
    
    protected abstract Project obtainProject(final IIssue issue);
    
}
