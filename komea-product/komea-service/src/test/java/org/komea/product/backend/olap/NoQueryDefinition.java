/**
 * 
 */

package org.komea.product.backend.olap;



import java.util.List;

import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;

import com.google.common.collect.Lists;



/**
 * @author sleroy
 */
public class NoQueryDefinition implements ICEPQueryImplementation
{
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.ICEPQueryImplementation#getBackupDelay()
     */
    @Override
    public BackupDelay getBackupDelay() {
    
    
        return BackupDelay.DAY;
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.ICEPQueryImplementation#getFilterDefinitions()
     */
    @Override
    public List<IFilterDefinition> getFilterDefinitions() {
    
    
        return Lists.newArrayList();
    }
    
    
    /*
     * (non-Javadoc)
     * @see org.komea.eventory.api.engine.ICEPQueryImplementation#getFormula()
     */
    @Override
    public ICEPFormula<?, ?> getFormula() {
    
    
        return new KpiResultFormula();
    }
    
}
