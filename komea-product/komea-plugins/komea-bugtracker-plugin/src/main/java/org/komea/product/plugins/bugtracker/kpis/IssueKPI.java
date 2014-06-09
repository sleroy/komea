/**
 * 
 */

package org.komea.product.plugins.bugtracker.kpis;



import groovy.lang.Closure;

import java.util.ArrayList;
import java.util.List;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.product.backend.api.IGroovyEngineService;
import org.komea.product.backend.groovy.AbstractDynamicQuery;
import org.komea.product.backend.service.dataplugin.IDynamicDataSourcePool;
import org.komea.product.database.api.IEntity;
import org.komea.product.database.dto.KpiResult;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;



/**
 * @author sleroy
 */
public final class IssueKPI extends

AbstractDynamicQuery
{
    
    
    private Closure<KpiResult>     closure;
    @Autowired
    private IDynamicDataSourcePool dataSourcePool;
    private final String           dynamicSource;
    
    @Autowired
    private IGroovyEngineService   groovyEngineService;
    
    
    
    public IssueKPI(final BackupDelay _delay, final String _dynamicSource) {
    
    
        super(_delay);
        dynamicSource = _dynamicSource;
    }
    
    
    @Override
    public KpiResult evaluateResult() {
    
    
        final ListMultimap<IEntity, IIssue> map = ArrayListMultimap.create();
        final List<IIssue> list = new ArrayList(10000);
        for (final IIssuePlugin plugin : dataSourcePool.getDataSourceOfType(IIssuePlugin.class)) {
            list.addAll(plugin.getData());
        }
        
        LOGGER.info("Obtained {} issues", list);
        
        return closure.call(list);
    }
    
    
    public Closure<KpiResult> getClosure() {
    
    
        return closure;
    }
    
    
    public IssueKPI setClosure(final Closure<KpiResult> _closure) {
    
    
        closure = _closure;
        return this;
    }
    
    
}
