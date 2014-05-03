/**
 * 
 */

package org.komea.product.cep.tester;



import java.util.Collections;

import org.junit.Test;
import org.komea.eventory.cache.CacheConfigurationBuilder;
import org.komea.eventory.filter.EventFilterBuilder;
import org.komea.eventory.query.CEPQueryImplementation;
import org.komea.product.cep.filter.OnlyEventFilter;
import org.komea.product.cep.formula.EventCountFormula;
import org.komea.product.plugins.kpi.filters.WithProjectFilter;
import org.komea.product.plugins.kpi.formula.ProjectFormula;



/**
 * @author sleroy
 */
public class CEPQueryTesterTest
{
    
    
    /**
     * Test method for {@link org.komea.product.cep.tester.CEPQueryTester#newTest()}.
     */
    @Test
    public final void testNewTest() throws Exception {
    
    
        final CEPQueryImplementation queryDefinition = new CEPQueryImplementation();
        queryDefinition.setFormula(new ProjectFormula(new EventCountFormula()));
        queryDefinition.setParameters(Collections.EMPTY_MAP);
        queryDefinition.addFilterDefinition(EventFilterBuilder.create()
                .chain(new OnlyEventFilter()).chain(new WithProjectFilter())
                .buildFilterDefinition("filter", CacheConfigurationBuilder.noConfiguration()));
        final queryDefinition.CEPQueryTester newTest =
                CEPQueryTester.newTest().withQuery(queryDefinition);
        
    }
    
}
