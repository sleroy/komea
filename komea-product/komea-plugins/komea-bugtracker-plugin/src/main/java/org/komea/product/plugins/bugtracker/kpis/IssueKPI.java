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
import org.komea.product.database.dto.KpiResult;
import org.komea.product.plugins.bugtracking.model.IIssue;
import org.komea.product.plugins.bugtracking.model.IIssuePlugin;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ArrayListMultimap;



/**
 * @author sleroy
 */
public final class IssueKPI extends

AbstractDynamicQuery
{
    
    
    private Closure<KpiResult>   closure;
    @Autowired
    private IIssuePlugin[]       connectors;
    
    
    @Autowired
    private IGroovyEngineService groovyEngineService;
    
    
    
    public IssueKPI(final BackupDelay _delay) {
    
    
        super(_delay);
    }
    
    
    @Override
    public KpiResult evaluateResult() {
    
    
        ArrayListMultimap.create();
        final List<IIssue> list = new ArrayList(10000);
        LOGGER.info("Analyzing through {} connectors", getConnectors().length);
        for (final IIssuePlugin plugin : getConnectors()) {
            
            list.addAll(plugin.getData());
        }
        
        LOGGER.info("Obtained {} issues", list);
        
        return closure.call(list);
    }
    
    
    public Closure<KpiResult> getClosure() {
    
    
        return closure;
    }
    
    
    public IIssuePlugin[] getConnectors() {
    
    
        return connectors;
    }
    
    
    public IssueKPI setClosure(final Closure<KpiResult> _closure) {
    
    
        closure = _closure;
        return this;
    }
    
    
    public void setConnectors(final IIssuePlugin[] _connectors) {
    
    
        connectors = _connectors;
    }
    
    
}
