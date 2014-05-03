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
import org.komea.product.database.alert.EventBuilder;
import org.komea.product.database.alert.IEvent;
import org.komea.product.database.enums.EntityType;
import org.komea.product.database.model.Project;
import org.komea.product.plugins.kpi.filters.WithProjectFilter;
import org.komea.product.plugins.kpi.formula.ProjectFormula;
import org.komea.product.service.dto.EntityKey;



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
        CEPQueryTester.newTest().withQuery(queryDefinition).send(fakeEvent()).expectRows(1)
                .expectStorageSize(1).hasResults(new Object[][]
                    {
                        {
                                EntityKey.of(EntityType.PROJECT, 1),
                                1 } });
        ;
        
        
    }
    
    
    /**
     * @return
     */
    private IEvent fakeEvent() {
    
    
        return EventBuilder.newAlert().now()
                .project(new Project(1, "KEY", "NAME", "DESCRIPTION", 1, "icon")).build();
    }
    
}
