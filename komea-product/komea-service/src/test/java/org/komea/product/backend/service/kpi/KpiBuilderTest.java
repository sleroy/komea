/**
 * 
 */

package org.komea.product.backend.service.kpi;



import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.komea.eventory.api.cache.BackupDelay;
import org.komea.eventory.api.engine.ICEPQueryImplementation;
import org.komea.eventory.api.filters.IFilterDefinition;
import org.komea.eventory.api.formula.ICEPFormula;
import org.komea.eventory.formula.NoCEPFormula;
import org.komea.product.backend.groovy.GroovyEngineService;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.enums.ProviderType;
import org.komea.product.database.enums.ValueDirection;
import org.komea.product.database.enums.ValueType;
import org.komea.product.database.model.Kpi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;



/**
 * @author sleroy
 */
public class KpiBuilderTest
{
    
    
    public static class CEPQuery0 implements ICEPQueryImplementation
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
        
        
            return Collections.EMPTY_LIST;
        }
        
        
        /*
         * (non-Javadoc)
         * @see org.komea.eventory.api.engine.ICEPQueryImplementation#getFormula()
         */
        @Override
        public ICEPFormula<?, ?> getFormula() {
        
        
            return new NoCEPFormula();
        }
        
    }
    
    
    
    @Test
    public void queryTest() {
    
    
        final GroovyEngineService groovyEngineService = new GroovyEngineService();
        
        final Kpi build =
                KpiBuilder.create().nameAndKeyDescription("BLA").query(CEPQuery0.class)
                        .providerType(ProviderType.CI_BUILD).cronOneDay()
                        .interval(0d, 1d, ValueDirection.BETTER, ValueType.BOOL)
                        .entityType(EntityType.DEPARTMENT).build();
        
        assertTrue(groovyEngineService.isValidFormula(build.getEsperRequest()));
        assertNotNull(groovyEngineService.parseQuery(build));
        
    }
}
